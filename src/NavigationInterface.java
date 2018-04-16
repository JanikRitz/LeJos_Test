/**
 * Created by Isabell on 16.04.2018.
 */
public interface NavigationInterface {
    public void rotateDegrees(int degrees);
    public void rotateDirection(Direction direction);
    public int driveForward();
    public int driveDirection(Direction direction);

    public enum Direction{
        NORTH, EAST, SOUTH, WEST
    }
}
