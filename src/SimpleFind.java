import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import java.util.Random;

import static lejos.util.Delay.msDelay;

public class SimpleFind implements ButtonListener {
    private static final double NORMAL_SPEED = 20;
    private static final double SLOW_SPEED = 3;
    private static final int MIN_RADIUS = 60;
    private static final int MIN_ANGLE = 90;
    public static final int ANGLE = 270;

    Boolean exit;
    Random random;
    ColorSensor color_sensor;
    UltrasonicSensor sonic_sensor;
    TouchSensor touchSensor;
    DifferentialPilot pilot;

    public static void main(String[] a) {
        SimpleFind colorTest = new SimpleFind();
        colorTest.mainLoop();
    }

    public SimpleFind() {
        this.exit = false;

        this.sonic_sensor = new UltrasonicSensor(SensorPort.S1);
        this.color_sensor = new ColorSensor(SensorPort.S2);
        this.touchSensor = new TouchSensor(SensorPort.S3);

        this.pilot = new DifferentialPilot(3.0, 14.5, Motor.B, Motor.C, false);
        this.pilot.setTravelSpeed(NORMAL_SPEED);
        this.random = new Random();
        Button.ESCAPE.addButtonListener(this);
    }

    private void mainLoop() {
        int loops = 0;

        while (!this.exit) {
            showColorDistance(this.color_sensor, this.sonic_sensor);

            if (this.sonic_sensor.getDistance() > 15) {

                if (this.pilot.getTravelSpeed() != NORMAL_SPEED || loops % 20 == 0 || !this.pilot.isMoving()) {
                    //arcForwardRandom();
                    this.pilot.setTravelSpeed(NORMAL_SPEED);
                    pilot.forward();
                }
            } else if (this.sonic_sensor.getDistance() > 5) {

                if (this.pilot.getTravelSpeed() != SLOW_SPEED) {
                    this.pilot.setTravelSpeed(SLOW_SPEED);
                    pilot.forward();
                }

                switch (this.color_sensor.getColorID()) {
                    case ColorSensor.Color.RED:
                        pilot.stop();
                        LCD.clear();
                        LCD.drawString("Ready", 2, 2);
                        Button.waitForAnyPress();
                        break;
                    case ColorSensor.Color.NONE:
                    case ColorSensor.Color.BLACK:
                        break;
                    default:
                        reverse(50);
                        pilot.rotate(180);
                }
            } else {
                reverse(25);
                rotateRandom();
            }
            if(touchSensor.isPressed()){
                reverse(25);
                rotateRandom();
            }

            msDelay(100);
            loops++;
        }
    }

    private void arcForwardRandom() {
        this.pilot.setTravelSpeed(NORMAL_SPEED);

        int decision = random.nextInt(101);
        decision = Math.max(decision - 50, 0);
        if (decision == 0) {
            pilot.forward();
        } else {
            decision = decision - 25;
            if (decision < 0) {
                pilot.arcForward(decision - MIN_RADIUS);
            } else {
                pilot.arcForward(decision + MIN_RADIUS);
            }
        }
    }

    private void rotateRandom() {
        int decision = this.random.nextInt(2* ANGLE + 1) - ANGLE;
        if (decision < 0) {
            pilot.rotate(decision - MIN_ANGLE);
        } else {
            pilot.rotate(decision + MIN_ANGLE);
        }
    }

    private void reverse(int i) {
        pilot.stop();
        pilot.setTravelSpeed(NORMAL_SPEED);
        pilot.travel(-i);
    }

    private void showColorDistance(ColorSensor color_sensor, UltrasonicSensor sonic) {
        LCD.clear();
        ColorSensor.Color c = color_sensor.getColor();

        int left = 0;
        int right = MIN_RADIUS;

        int line = 1;
        LCD.drawString("Color:", left, line);
        this.showColor(color_sensor, right, line);
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
