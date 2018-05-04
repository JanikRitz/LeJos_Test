import java.util.Random;

/**
 * Created by Isabell on 16.04.2018.
 */
public interface NavigationInterface {

    public void rotateDegrees(double degrees);
    public void rotateDirection(Direction direction);
    public int driveForward();
    public int driveDirection(Direction direction);

    public enum Direction{
        NORTH , EAST , SOUTH , WEST ;

        public static Direction random(){
            Random random = new Random();
            return Direction.values()[random.nextInt(Direction.values().length)];
        }

        public static double degrees(Direction origin, Direction other) throws Exception {
            if(origin == other){
                return (double) 0;
            }

            double angle = degrees(other) - degrees(origin);

            if(angle > 180){
                return 360.0 - angle;
            } else if(angle < -180){
                return 360.0 + angle;
            }else{
                return angle;
            }
        }

        public static double degrees(Direction direction) throws Exception {
            switch (direction) {
                case NORTH:
                    return 0;
                case EAST:
                    return 90;
                case SOUTH:
                    return 180;
                case WEST:
                    return 270;
                default:
                    return 0;
                    // throw new Exception("Direction not supported");
            }
        }

        public static int xOffset(Direction direction){
            switch (direction) {
                case NORTH:
                    return 0;
                case EAST:
                    return 1;
                case SOUTH:
                    return 0;
                case WEST:
                    return -1;
                default:
                    return 0;
            }
        }

        public static int yOffset(Direction direction){
            switch (direction) {
                case NORTH:
                    return 1;
                case EAST:
                    return 0;
                case SOUTH:
                    return -1;
                case WEST:
                    return 0;
                default:
                    return 0;
            }
        }
    }
}
