import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class Rotate_Test implements ButtonListener {
    DifferentialPilot pilot;

    public static void main(String[] a) {
        Rotate_Test c_test = new Rotate_Test();
        c_test.main_loop();
    }

    public Rotate_Test() {
        //this.pilot = DifferentialPilotFactory.newMasterPilot();
        this.pilot = DifferentialPilotFactory.newSlavePilot();
        this.pilot.setTravelSpeed(25);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop() {
        while (true) {
            //pilot.rotate(90); // -> ~45 degrees
            LCD.drawString("Starting in 3 seconds",1,1);
            msDelay(3000);
            LCD.clear();
            LCD.drawString("Starting",1,1);
            pilot.travel(100);
            msDelay(3000);
            pilot.travel(-100);
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
