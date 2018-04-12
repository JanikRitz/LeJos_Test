/**
 * Created by Isabell on 12.04.2018.
 */
import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class BTReceiveTest implements ButtonListener {
    public static void main(String [] args) throws Exception {
        BTReceiveTest btReceiveTest = new BTReceiveTest();
        btReceiveTest.mainLoop();
    }

    public BTReceiveTest(){
        Button.ESCAPE.addButtonListener(this);
    }

    private void mainLoop() throws IOException {
        String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";

        while (true) {
            LCD.drawString(waiting,0,0);
            NXTConnection connection = Bluetooth.waitForConnection();
            LCD.clear();
            LCD.drawString(connected,0,0);

            DataInputStream dis = connection.openDataInputStream();
            DataOutputStream dos = connection.openDataOutputStream();

            for(int i=0;i<100;i++) {
                int n = dis.readInt();
                LCD.drawInt(n,7,0,1);
                dos.writeInt(-n);
                dos.flush();
            }
            dis.close();
            dos.close();

            LCD.clear();
            LCD.drawString(closing,0,0);

            connection.close();
            LCD.clear();
        }
    }

    public void buttonPressed(Button var1) {
        System.exit(0);
    }

    public void buttonReleased(Button var1) {
    }
}