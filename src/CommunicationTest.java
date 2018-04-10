/**
 * Created by Isabell on 10.04.2018.
 */

import lejos.nxt.LCD;
import lejos.nxt.comm.LCP;
import lejos.nxt.comm.LCPMessageListener;

public class CommunicationTest implements LCPMessageListener
{
    public static void main(String[] args) {
        CommunicationTest communicationTest = new CommunicationTest();
        communicationTest.mainLoop();
    }

    public void mainLoop(){
        LCP.addMessageListener(this);
        LCP.messageWrite(3,"Test Message");
        while (true){
            //do nothing
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageReceived(byte inBox, String message) {
        LCD.drawInt((int)inBox,1,1);
        LCD.drawString(message,1,2);
    }
}
