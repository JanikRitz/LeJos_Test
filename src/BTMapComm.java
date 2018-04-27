import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;

public class BTMapComm {
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
}
