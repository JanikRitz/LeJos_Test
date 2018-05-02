import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;

public class BTSlave implements BTGeneric {
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTSlave(String name_other, MapNavigationPilot pilot){
        this.pilot = pilot;
        this.other_nxt = name_other;
    }

    @Override
    public int commLoop(){
        // TODO connect

        NXTConnection connection = Bluetooth.waitForConnection();

        if(connection == null) return -3;

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while(true){
            // TODO receive and send Data
            BTMapComm.receiveData(inputStream, this.pilot);

            if (BTMapComm.sendData(outputStream, this.data)) return -4;

            // TODO Wait
        }
    }

    @Override
    public void addObject(int x, int y, MapNavigationPilot.MapObject object){
        this.data.add(x);
        this.data.add(y);
        this.data.add(object.ordinal()); // TODO Check for correctness
    }

    @Override
    public void run() {
        this.commLoop();
    }
}
