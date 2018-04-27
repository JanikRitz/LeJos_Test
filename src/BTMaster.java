import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import javax.bluetooth.RemoteDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;

public class BTMaster implements Runnable{
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTMaster(String name_other, MapNavigationPilot pilot){
        this.pilot = pilot;
        this.other_nxt = name_other;
    }

    public int commLoop(){
        // TODO connect

        RemoteDevice remoteDevice = Bluetooth.getKnownDevice(this.other_nxt);

        if(remoteDevice == null) return -2;

        BTConnection connection = Bluetooth.connect(remoteDevice);

        if(connection == null) return -3;

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while(true){
            // TODO receive and send Data
            try {
                if(this.data.size() >= 3) {
                    outputStream.writeInt(this.data.remove()); // X
                    outputStream.writeInt(this.data.remove()); // Y
                    outputStream.writeInt(this.data.remove()); // Type
                    outputStream.flush();
                }
            } catch (IOException ioe) {
               return -4;
            }

            try {
                int x = inputStream.readInt();
                int y = inputStream.readInt();
                int type = inputStream.readInt();
                this.pilot.modifyMap(x, y, type);
            } catch (IOException ioe) {
                // No Data, is Ok, try later
            }
            // TODO wait
        }
    }

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
