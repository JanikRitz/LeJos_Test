import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Isabell on 02.03.2018.
 */
public class DifferentialPilotFactory {

    public static DifferentialPilot newMasterPilot(){
        return new DifferentialPilot(35.0, 136.0, Motor.B, Motor.C);
    }
    public static DifferentialPilot newSlavePilot(){
        return new DifferentialPilot(56.0, 115.0, Motor.B, Motor.C);
    }
}
