import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

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

        public double degrees(Direction other){
            if(this.equals(other)){
                return (double) 0;
            }

            double angle = other.degrees() - this.degrees();
            if(angle > 180){
                return 360.0 - angle;
            } else{
                return angle;
            }
        }

        public double degrees(){
            switch (this) {
                case NORTH:
                    return 0;
                case EAST:
                    return 90;
                case SOUTH:
                    return 180;
                case WEST:
                    return 270;
                default:
                    throw new ValueException("Direction not supported");
            }
        }
    }
}
