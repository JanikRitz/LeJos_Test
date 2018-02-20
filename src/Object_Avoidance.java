import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;

public class Object_Avoidance {
    public static void main(String args[]) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
        DifferentialPilot p = pilot();
        p.setTravelSpeed(2);

        while (!Button.ESCAPE.isDown()) {
            if (sonic.getDistance() < 40) {
                p.arcForward(10);
            } else {
                p.forward();
            }

        }

        p.stop();
        while (Button.ESCAPE.isDown()) ;
        Button.ENTER.waitForPressAndRelease();

        p.forward();
        while (!Button.ESCAPE.isDown()) {
            if (sonic.getDistance() < 40) {
                p.arcForward(10);
                msDelay(500);
                p.forward();
            }
        }

        p.stop();
    }

    public static DifferentialPilot pilot() {
        // Measured in cm
        return new DifferentialPilot(3.0, 13.5, Motor.B, Motor.C);
    }
}
