import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static lejos.util.Delay.msDelay;


public class Main {
    // Everything measured in cm
    public static void main(String[] args) {
//        System.out.println("Hello World");
//        System.out.println("Hello Isabell");
//        LCD.drawInt(10, 5,5); // Int, X-Pos, Y-Pos
//        Button.waitForAnyPress();
//        set_speed_revolutions_per_second(2);
//        drive_milliseconds(5*1000);


        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);

        while (!Button.ESCAPE.isDown()) {
            show_distance(sonic);
            msDelay(250);
        }
//        DifferentialPilot p = pilot();
//
//        p.arcForward(20); // arc clockwise
//        msDelay(10*1000);
//        p.stop();
//
//        Button.waitForAnyPress(5*1000);
//
//        p.arcForward(-20); // arc anti-clockwise
//        msDelay(10*1000);
//        p.stop();
//
//        Button.waitForAnyPress(5*1000);
//
//        p.travel(50.0);
//
//        System.out.println("Drove");
//
//        Button.ENTER.waitForPressAndRelease();

    }

    public static DifferentialPilot pilot() {
        // Measured in cm
        return new DifferentialPilot(3.0, 14.5, Motor.B, Motor.C, false);
    }

    public static void show_distance(UltrasonicSensor sensor) {
        LCD.clear();

        LCD.drawString("Distance is", 1, 1);

        int distance = 0;
        distance = sensor.getDistance();

        if (distance == 255){
            LCD.drawString("Infinite", 1, 3);
        } else {
            LCD.drawInt(distance, 1, 3);
        }

        LCD.drawString(" cm", 1, 5);
    }

    public static void set_speed_degrees_per_second(int speed) {
        Motor.B.setSpeed(speed);
        Motor.C.setSpeed(speed);
    }

    public static void set_speed_revolutions_per_second(double revolutions) {
        int speed_degrees = (int) Math.round(revolutions * 360);
        Motor.B.setSpeed(speed_degrees);
        Motor.C.setSpeed(speed_degrees);
    }

    public static void drive_milliseconds(int milliseconds) {
        Motor.B.backward();
        Motor.C.backward();

        msDelay(milliseconds);

        Motor.B.stop();
        Motor.C.stop();
    }
}
