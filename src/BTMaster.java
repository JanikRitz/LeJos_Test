import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import javax.bluetooth.RemoteDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BTMaster implements BTGeneric {
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTMaster(String name_other) {
        this.other_nxt = name_other;
        this.data = new ArrayBlockingQueue<Integer>(20);
    }

    public void init(MapNavigationPilot pilot) {
        this.pilot = pilot;
    }

    public int commLoop() {
        // TODO connect

        RemoteDevice remoteDevice = Bluetooth.getKnownDevice(this.other_nxt);

        if (remoteDevice == null) return -2;

        BTConnection connection = Bluetooth.connect(remoteDevice);

        if (connection == null) return -3;

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while (true) {
            // TODO receive and send Data
            if (BTMapComm.sendData(outputStream, this.data)) return -4;

            BTMapComm.receiveData(inputStream, this.pilot);
            // TODO wait
        }
    }

    public void addObject(int x, int y, MapNavigationPilot.MapObject object) {
        this.data.add(x);
        this.data.add(y);
        this.data.add(object.ordinal()); // TODO Check for correctness
    }

    @Override
    public void run() {
        this.commLoop();
    }
}
