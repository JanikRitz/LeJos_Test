import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static lejos.util.Delay.msDelay;

public class SimpleFind implements ButtonListener {
    private static final double NORMAL_SPEED = 100;
    private static final double SLOW_SPEED = 20;

    Boolean exit;
    Random random;
    ColorSensor color_sensor;
    UltrasonicSensor sonic_sensor;
    TouchSensor touchSensor;
    DifferentialPilot pilot;

    public static void main(String[] a) {
        SimpleFind find = new SimpleFind();
        find.mainLoop();
    }

    public SimpleFind() {
        this.exit = false;

        this.sonic_sensor = new UltrasonicSensor(SensorPort.S1);
        this.color_sensor = new ColorSensor(SensorPort.S2);
        this.touchSensor = new TouchSensor(SensorPort.S3);

        //this.pilot = new DifferentialPilot(3.0, 14.5, Motor.B, Motor.C, false);
        this.pilot = DifferentialPilotFactory.newMasterPilot();
        //this.pilot = DifferentialPilotFactory.newSlavePilot();
        this.pilot.setTravelSpeed(NORMAL_SPEED);
        this.random = new Random();
        Button.ESCAPE.addButtonListener(this);
    }

    private void mainLoop() {
        while (!this.exit) {
            showColorDistance(this.color_sensor, this.sonic_sensor);

            if (this.sonic_sensor.getDistance() > 15) {

                if (this.pilot.getTravelSpeed() != NORMAL_SPEED || !this.pilot.isMoving()) {
                    this.pilot.setTravelSpeed(NORMAL_SPEED);
                    pilot.forward();
                }
            } else {

                if (this.pilot.getTravelSpeed() != SLOW_SPEED) {
                    this.pilot.setTravelSpeed(SLOW_SPEED);
                    pilot.forward();
                }

            }
            if (touchSensor.isPressed()) {

                this.pilot.stop();

                int reds = 0;
                for (int i = 0; i < 5; i++) {
                    if (this.color_sensor.getColorID() == ColorSensor.Color.RED) reds++;
                    msDelay(50);
                }
                if (reds > 0) {
                    LCD.clear();
                    LCD.drawString("Ready", 2, 2);
                    LCD.drawInt(reds, 2, 4);
                    Button.waitForAnyPress();
                } else {
                    reverse(100);
                    pilot.rotate(90);
                }
            }

            msDelay(100);
        }
    }

    private void reverse(int i) {
        pilot.stop();
        pilot.setTravelSpeed(NORMAL_SPEED);
        pilot.travel(-i);
    }

    private void showColorDistance(ColorSensor colorSensor, UltrasonicSensor sonic) {
        LCD.clear();
        ColorSensor.Color c = colorSensor.getColor();

        int left = 0;
        int right = 10;

        int line = 1;
        LCD.drawString("Color:", left, line);
        this.showColor(colorSensor, right, line);
        line += 2;

        LCD.drawString("Distance:", left, line);

        int distance = 0;
        distance = sonic.getDistance();

        if (distance == 255) {
            LCD.drawString("Infinite", right, line);
        } else {
            LCD.drawInt(distance, right, line);
            LCD.drawString(" units", right + 5, line);
        }
    }

    private void showColor(ColorSensor sensor, int row, int column) {
        String text = "";
        switch (sensor.getColorID()) {
            case ColorSensor.Color.NONE:
                text = "None";
                break;
            case ColorSensor.Color.RED:
                text = "Red";
                break;
            case ColorSensor.Color.GREEN:
                text = "Green";
                break;
            case ColorSensor.Color.BLUE:
                text = "Blue";
                break;
            case ColorSensor.Color.YELLOW:
                text = "Yellow";
                break;
            case ColorSensor.Color.MAGENTA:
                text = "Magenta";
                break;
            case ColorSensor.Color.ORANGE:
                text = "Orange";
                break;
            case ColorSensor.Color.WHITE:
                text = "White";
                break;
            case ColorSensor.Color.BLACK:
                text = "Black";
                break;
            case ColorSensor.Color.PINK:
                text = "Pink";
                break;
            case ColorSensor.Color.GRAY:
                text = "Gray";
                break;
            case ColorSensor.Color.LIGHT_GRAY:
                text = "Light Gray";
                break;
            case ColorSensor.Color.DARK_GRAY:
                text = "Dark Gray";
                break;
            case ColorSensor.Color.CYAN:
                text = "Cyan";
                break;
            default:
                text = "NA (" + sensor.getColorID() + ")";
        }
        LCD.drawString(text, row, column);
    }


    @Override
    public void buttonPressed(Button button) {
        this.exit = true;
        System.exit(0);
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
