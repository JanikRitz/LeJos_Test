/**
 * Created by Isabell on 10.04.2018.
 */

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.comm.LCP;
import lejos.nxt.comm.LCPMessageListener;

import static lejos.util.Delay.msDelay;

public class CommTest implements LCPMessageListener , ButtonListener {
    public static void main(String[] args) {
        CommTest commTest = new CommTest();
        commTest.mainLoop();
    }

    public CommTest(){
        Button.ESCAPE.addButtonListener(this);
        LCP.addMessageListener(this);
    }

    public void mainLoop() {

        LCP.messageWrite(3, "Test Message");
        while (true) {
            //do nothing
            msDelay(1000);
        }
    }

    @Override
    public void messageReceived(byte inBox, String message) {
        LCD.drawInt((int) inBox, 1, 1);
        LCD.drawString(message,1,2);
    }

    @Override
    public void buttonPressed(Button button) {
       // this.exit = true;
        System.exit(0);
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
