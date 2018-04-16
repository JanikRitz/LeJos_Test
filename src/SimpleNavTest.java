import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Isabell on 16.04.2018.
 */
public class SimpleNavTest implements ButtonListener {
    SimpleNavigationPilot pilot;

    public static void main(String[] args) {
        SimpleNavTest navigationTest = new SimpleNavTest();
        navigationTest.mainLoop();
    }

    public SimpleNavTest(){
        DifferentialPilot master = DifferentialPilotFactory.newMasterPilot();
        this.pilot = new SimpleNavigationPilot(master, -1.35);

        Button.ESCAPE.addButtonListener(this);
    }

    public void mainLoop(){
        while (true){
            for(NavigationInterface.Direction direction: NavigationInterface.Direction.values()) {
                this.pilot.driveForward();
                this.pilot.driveForward();
                this.pilot.driveDirection(direction);
            }
        }
    }

    @Override
    public void buttonPressed(Button button) {

    }

    @Override
    public void buttonReleased(Button button) {
        System.exit(0);
    }
}
