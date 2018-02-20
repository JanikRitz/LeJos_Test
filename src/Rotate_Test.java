import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class Rotate_Test implements ButtonListener {
    DifferentialPilot pilot;
    boolean exit;

    public static void main(String[] a) {
        Rotate_Test c_test = new Rotate_Test();
        c_test.main_loop();
    }

    public Rotate_Test() {
        this.pilot = new DifferentialPilot(3.0, 14.5, Motor.B, Motor.C, false);
        this.pilot.setTravelSpeed(10);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop() {
        while (!this.exit) {
            pilot.rotate(90); // -> ~45 degrees
            msDelay(5000);
            pilot.travel(50);
            pilot.travel(-50);
        }
    }

    @Override
    public void buttonPressed(Button button) {
        this.exit = true;
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
