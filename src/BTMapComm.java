import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;

public class BTMapComm {
    static boolean sendData(DataOutputStream outputStream, Queue<Integer> data) {
        try {
            if(data.size() >= 3) {
                outputStream.writeInt((int) data.pop()); // X
                outputStream.writeInt((int) data.pop()); // Y
                outputStream.writeInt((int) data.pop()); // Type
                outputStream.flush();
            } else{
                outputStream.writeInt(-1); // X
                outputStream.writeInt(-1); // Y
                outputStream.writeInt(-1); // Type
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
            if(x == -1 || y == -1 || type == -1) return;
            pilot.modifyMap(x, y, type);
        } catch (IOException ioe) {
            // No Data, is Ok, try later
        }
    }
}
