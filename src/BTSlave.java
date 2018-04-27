import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import javax.bluetooth.RemoteDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;

public class BTSlave implements Runnable{
    private final String other_nxt;
    private MapNavigationPilot pilot;
    private Queue<Integer> data;


    public BTSlave(String name_other, MapNavigationPilot pilot){
        this.pilot = pilot;
        this.other_nxt = name_other;
    }

    public int commLoop(){
        // TODO connect

        NXTConnection connection = Bluetooth.waitForConnection();

        if(connection == null) return -3;

        DataInputStream inputStream = connection.openDataInputStream();
        DataOutputStream outputStream = connection.openDataOutputStream();

        while(true){
            // TODO receive and send Data
            receiveData(inputStream, this.pilot);

            if (sendData(outputStream, this.data)) return -4;

            // TODO Wait
        }
    }

    // TODO Move to other class for inheritance
    static boolean sendData(DataOutputStream outputStream, Queue<Integer> data) {
        try {
            if(data.size() >= 3) {
                outputStream.writeInt(data.remove()); // X
                outputStream.writeInt(data.remove()); // Y
                outputStream.writeInt(data.remove()); // Type
                outputStream.flush();
            }
        } catch (IOException ioe) {
            return true;
        }
        return false;
    }

    static void receiveData(DataInputStream inputStream, MapNavigationPilot pilot) {
        try {
            int x = inputStream.readInt();
            int y = inputStream.readInt();
            int type = inputStream.readInt();
            pilot.modifyMap(x, y, type);
        } catch (IOException ioe) {
            // No Data, is Ok, try later
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
