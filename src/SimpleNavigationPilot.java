import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Isabell on 16.04.2018.
 */
public class SimpleNavigationPilot implements NavigationInterface {

    private DifferentialPilot pilot;
    private Direction facing;
    private double angleCorrection;

    public SimpleNavigationPilot(DifferentialPilot pilot, double angleCorrection) {
        this.facing = Direction.NORTH;
        this.pilot = pilot;
        this.angleCorrection = angleCorrection;
    }

    @Override
    public void rotateDegrees(double degrees) {
        pilot.rotate(degrees);
    }

    @Override
    public void rotateDirection(Direction direction) {
        double angle = 0;
        try {
            angle = Direction.degrees(facing, direction);
        } catch (Exception e){
            // Use Default 0 degrees
        }

        LCD.clear();
        LCD.drawString("Rotating", 1, 1);
        LCD.drawInt((int) angle, 2, 2);
        LCD.drawString("Direction (old)", 1, 3);
        LCD.drawString(facing.name(), 2, 4);
        LCD.drawString("Direction (new)", 1, 5);
        LCD.drawString(direction.name(), 2, 6);
        //Button.waitForAnyPress();

        angle = angle*this.angleCorrection;
        pilot.rotate(angle);
        this.facing = direction;
    }

    @Override
    public int driveForward() {
        this.pilot.travel(200);
        return 0;
    }

    @Override
    public int driveDirection(Direction direction) {
        this.rotateDirection(direction);
        return this.driveForward();
    }
}
