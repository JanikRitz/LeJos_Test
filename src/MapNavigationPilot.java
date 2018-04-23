import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;


public class SimpleNavigationPilot implements NavigationInterface {

    private DifferentialPilot pilot;
    private Direction facing;
    private double angleCorrection;
    private int x_size;
    private int x_pos;
    private int y_size;
    private int y_pos;
    private int[][] map;

    public SimpleNavigationPilot(DifferentialPilot pilot, double angleCorrection, int x_size, int y_size, int x_start, int y_start) {
        this.facing = Direction.NORTH;
        this.pilot = pilot;
        this.angleCorrection = angleCorrection;

        this.x_size = x_size;
        this.y_size = y_size;

        this.x_pos = x_start;
        this.y_pos = y_start;

        this.map = [this.x_size][this.y_size]; // TODO Init MAP
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

        angle = angle*this.angleCorrection;
        pilot.rotate(angle);
        this.facing = direction;
    }

    @Override
    public int driveForward() {
        // TODO implement correct stuff

        // Check in map

        // Drive and wait for sensors

        // React to Sensor input

        // Change Map

        this.x_pos += 0;
        this.y_pos += 0;

        this.pilot.travel(200);
        return 0;
    }

    @Override
    public int driveDirection(Direction direction) {
        this.rotateDirection(direction);
        return this.driveForward();
    }
}

public enum MapObject(){
    FREE, OBSTACLE, RESOURCE
        }