/**
 * Created by Isabell on 10.04.2018.
 */

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.LCP;
import lejos.nxt.comm.LCPMessageListener;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class MasterNavTest implements ButtonListener {
    public static void main(String[] args) {
        new MasterNavTest();
    }

    public MasterNavTest() {
        Button.ESCAPE.addButtonListener(this);
        DifferentialPilot pilot = DifferentialPilotFactory.newMasterPilot();

        BTGeneric communicator = new BTMaster("GD2017-2");

        MapNavigationPilot mapNavigationPilot = new MapNavigationPilot(pilot, 1.35, SensorPort.S1, SensorPort.S2, SensorPort.S3, 5, 5, 0, 0, NavigationInterface.Direction.NORTH, communicator);

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
