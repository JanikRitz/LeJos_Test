import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Touch_Test implements ButtonListener {
    DifferentialPilot pilot;
    TouchSensor touchSensor;

    public static void main(String[] a) {
        Touch_Test c_test = new Touch_Test();
        c_test.main_loop();
    }

    public Touch_Test() {
        this.pilot = new DifferentialPilot(5.5, 11.5, Motor.B, Motor.C, false);
        this.pilot.setTravelSpeed(5);

        this.touchSensor = new TouchSensor(SensorPort.S4);

        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop() {
        while(true) {
            this.pilot.forward();
            while (!this.touchSensor.isPressed()) {
            }
            this.pilot.stop();
            this.pilot.travel(-5);
            this.pilot.arc(0, (Math.random() * 90) + 45);
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
