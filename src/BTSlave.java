import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;

public class BTSlave implements BTGeneric {
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTSlave(String name_other) {
        this.other_nxt = name_other;
        this.data = new Queue<Integer>();
    }

    public void init(MapNavigationPilot pilot) {
        this.pilot = pilot;
    }

    @Override
    public int commLoop() {
        NXTConnection connection = Bluetooth.waitForConnection();

        if (connection == null) return -3;

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while (true) {
            BTMapComm.receiveData(inputStream, this.pilot);

            if (BTMapComm.sendData(outputStream, this.data)) return -4;

            // TODO Wait
        }
    }

    @Override
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
