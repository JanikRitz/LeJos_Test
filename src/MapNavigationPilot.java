import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import java.util.Random;

import static lejos.util.Delay.msDelay;


public class MapNavigationPilot implements NavigationInterface, SensorPortListener {

    public static final int mapDistance = 200;
    private static final double NORMAL_SPEED = 25; // TODO correct value
    private static final double SLOW_SPEED = 10; // TODO correct value
    public static final int MEASURE_ITERS = 5;
    private DifferentialPilot pilot;
    private Direction facing;
    private double angleCorrection;
    private int x_pos;
    private int y_pos;
    private UltrasonicSensor distanceSensor;
    private ColorSensor colorSensor;
    private TouchSensor touchSensor;
    private MapObject[][] map;
    private BTGeneric communicator;

    public MapNavigationPilot(DifferentialPilot pilot, double angleCorrection,
                              SensorPort distancePort, SensorPort colorPort, SensorPort touchPort,
                              int xSize, int ySize, int x_start, int y_start,
                              BTGeneric communicator) {
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

        this.communicator = communicator;

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.map[x][y] = MapObject.FREE;
            }
        }

        this.communicator.init(this);
        new Thread(this.communicator).start();
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

        if(newXPos > this.map.length || newYPos > this.map[0].length) return -4;

        if (this.map[newXPos][newYPos] != MapObject.FREE) {
            return -1;
            // Or other Error Code like this.map[newXPos][newYPos]
        }

        // Drive and wait for sensors
        this.pilot.travel(mapDistance, true);
        while (!touchSensor.isPressed() && this.pilot.isMoving()){
            // Regulate Speed
            if (this.distanceSensor.getDistance() > 15) {
                this.pilot.setTravelSpeed(NORMAL_SPEED);
            } else {
                this.pilot.setTravelSpeed(SLOW_SPEED);
            }
        }

        this.pilot.stop();
        this.pilot.setTravelSpeed(NORMAL_SPEED);

        if (touchSensor.isPressed()) {
            int reds = 0;
            for (int i = 0; i < MEASURE_ITERS; i++) {
                if (this.colorSensor.getColorID() == ColorSensor.Color.RED) reds++;
                msDelay(50);
            }
            if (reds >= MEASURE_ITERS/2) {
                // Found Resource
                this.modifyMap(newXPos, newYPos, MapObject.RESOURCE.ordinal());
            } else{
                // Found Obstacle
                this.modifyMap(newXPos, newYPos, MapObject.OBSTACLE.ordinal());
            }
            this.pilot.travel(-this.pilot.getMovementIncrement());
        } else {
            // Change Map and Pilot
            this.x_pos = newXPos;
            this.y_pos = newYPos;

            this.modifyMap(this.x_pos, this.y_pos, MapObject.FREE.ordinal());
        }
        return 0;
    }

    @Override
    public int driveDirection(Direction direction) {
        this.rotateDirection(direction);
        return this.driveForward();
    }

    public int driveRandomDirection(){
        Direction direction;
        direction = Direction.random();
        if(this.map[this.x_pos + Direction.xOffset(direction)][this.x_pos + Direction.yOffset(direction)] == MapObject.OBSTACLE)
            direction = Direction.random();

        return this.driveDirection(direction);
    }

    public synchronized void modifyMap(int x, int y, int type) {
        this.map[x][y] = MapObject.values()[type];
        this.communicator.addObject(x, y, MapObject.values()[type]);
    }

    @Override
    public void stateChanged(SensorPort sensorPort, int oldValue, int newValue) {
        // TODO Implement Actions for
        // TODO Touch Sensor
        // TODO Ultrasonic Sensor
        // Is this necessary?
    }

    public enum MapObject {
        FREE, OBSTACLE, RESOURCE
    }
}