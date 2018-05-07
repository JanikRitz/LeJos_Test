import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import static lejos.util.Delay.msDelay;

import javax.bluetooth.RemoteDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;

public class BTMaster implements BTGeneric {
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTMaster(String name_other) {
        this.other_nxt = name_other;
        this.data = new Queue<Integer>();
    }

    public void init(MapNavigationPilot pilot) {
        this.pilot = pilot;
    }

    public int commLoop() {
        RemoteDevice remoteDevice = Bluetooth.getKnownDevice(this.other_nxt);

        if (remoteDevice == null) return -2;
        LCD.drawString("Found Device", 1, 1);

        BTConnection connection = Bluetooth.connect(remoteDevice);

        if (connection == null) return -3;
        LCD.drawString("Connected", 1, 3);

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while (true) {
            LCD.drawString("Sending", 1, 5);
            if (BTMapComm.sendData(outputStream, this.data)) return -4;

            BTMapComm.receiveData(inputStream, this.pilot);
            msDelay(3000);
        }
    }

    public void addObject(int x, int y, MapNavigationPilot.MapObject object) {
        this.data.push(x);
        this.data.push(y);
        this.data.push(object.ordinal()); // TODO Check for correctness
    }

    @Override
    public void run() {
        this.commLoop();
    }
}
