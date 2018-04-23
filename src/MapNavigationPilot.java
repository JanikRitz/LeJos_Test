import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class MapNavigationPilot implements NavigationInterface, SensorPortListener {

    public static final int mapDistance = 200;
    private DifferentialPilot pilot;
    private Direction facing;
    private double angleCorrection;
    private int x_pos;
    private int y_pos;
    private UltrasonicSensor distanceSensor;
    private ColorSensor colorSensor;
    private TouchSensor touchSensor;
    private MapObject[][] map;

    public MapNavigationPilot(DifferentialPilot pilot, double angleCorrection,
                              SensorPort distancePort, SensorPort colorPort, SensorPort touchPort,
                              int xSize, int ySize, int x_start, int y_start) {
        this.facing = Direction.NORTH;
        this.pilot = pilot;
        this.angleCorrection = angleCorrection;

        this.distanceSensor = new UltrasonicSensor(distancePort);
        this.colorSensor = new ColorSensor(colorPort);
        this.touchSensor = new TouchSensor(touchPort);

        touchPort.addSensorPortListener(this);
        distancePort.addSensorPortListener(this);

        this.x_pos = x_start;
        this.y_pos = y_start;

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.map[x][y] = MapObject.FREE;
            }
        }
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
        } catch (Exception e) {
            // Use Default 0 degrees
        }

        angle = angle * this.angleCorrection;
        pilot.rotate(angle);
        this.facing = direction;
    }

    @Override
    public int driveForward() {
        // TODO implement correct stuff

        // Check in map
        int newXPos = this.x_pos + Direction.xOffset(this.facing);
        int newYPos = this.y_pos + Direction.yOffset(this.facing);

        if (this.map[newXPos][newYPos] != MapObject.FREE) {
            return -1;
            // Or other Error Code like this.map[newXPos][newYPos]
        }

        // Drive and wait for sensors
        this.pilot.travel(mapDistance);

        // React to Sensor input

        // Change Map and Pilot
        this.x_pos = newXPos;
        this.y_pos = newYPos;

        this.map[this.x_pos][this.y_pos] = MapObject.FREE;
        return 0;
    }

    @Override
    public int driveDirection(Direction direction) {
        this.rotateDirection(direction);
        return this.driveForward();
    }

    @Override
    public void stateChanged(SensorPort sensorPort, int oldValue, int newValue) {
        // TODO Implement Actions for
        // TODO Touch Sensor
        // TODO Ultrasonic Sensor
    }

    public enum MapObject {
        FREE, OBSTACLE, RESOURCE
    }
}