import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Isabell on 16.04.2018.
 */
public class SimpleNavigationPilot implements NavigationInterface {

    private DifferentialPilot pilot;
    private Direction facing;

    public SimpleNavigationPilot(DifferentialPilot pilot) {
        this.facing = Direction.NORTH;
        this.pilot = pilot;
    }

    @Override
    public void rotateDegrees(double degrees) {
        pilot.rotate(degrees);
    }

    @Override
    public void rotateDirection(Direction direction) {
        double angle = 0;
        try {
            angle = this.facing.degrees(direction);
        } catch (Exception e){
            // Use Default 0 degrees
        }
        pilot.rotate(angle);
    }

    @Override
    public int driveForward() {
        pilot.travel(20);
        return 0;
    }

    @Override
    public int driveDirection(Direction direction) {
        this.rotateDirection(direction);
        return this.driveForward();
    }
}
