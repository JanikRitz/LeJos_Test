import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class RotateTest implements ButtonListener {
    DifferentialPilot pilot;

    // 1.5 too much
    // 1.35 ok on carpet but not on hardwood floor
    // 1.0 not enough
    static int ANGLE = (int) (90 * (1.4));

    public static void main(String[] a) {
        RotateTest c_test = new RotateTest();
        c_test.main_loop();
    }

    public RotateTest() {
        this.pilot = DifferentialPilotFactory.newMasterPilot();
        //this.pilot = DifferentialPilotFactory.newSlavePilot();
        this.pilot.setTravelSpeed(25);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop() {
        while (true) {
            //
            LCD.drawString("Starting in 3 seconds",1,1);
            msDelay(3000);
            LCD.clear();
            LCD.drawString("Starting",1,1);
            // pilot.travel(100);
            pilot.rotate(ANGLE);
            msDelay(3000);
            pilot.rotate(-ANGLE);
            // pilot.travel(-100);
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
