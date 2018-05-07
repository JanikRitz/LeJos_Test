/**
 * Created by Isabell on 10.04.2018.
 */

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.LCP;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class SlaveNavTest implements ButtonListener {
    public static void main(String[] args) {
        new SlaveNavTest();
    }

    public SlaveNavTest() {
        Button.ESCAPE.addButtonListener(this);
        DifferentialPilot pilot = DifferentialPilotFactory.newSlavePilot();

        BTGeneric communicator = new BTSlave("NXT");

        MapNavigationPilot mapNavigationPilot = new MapNavigationPilot(pilot, 1.0, SensorPort.S1, SensorPort.S2, SensorPort.S3, 5, 5, 4, 4, NavigationInterface.Direction.SOUTH, false, communicator);

        while (true) {
            mapNavigationPilot.driveIntelligent();
        }
    }


    @Override
    public void buttonPressed(Button button) {
        System.exit(0);
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
