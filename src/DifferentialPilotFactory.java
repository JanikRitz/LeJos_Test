import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Created by Isabell on 02.03.2018.
 */
public class DifferentialPilotFactory {

    public static DifferentialPilot newMasterPilot(){
        // 35mm Diameter + 3/5 Gear Ratio + 10% Correction
        return new DifferentialPilot((35.0*(3.0/5.0)/1.1), 136.0, Motor.B, Motor.C);
    }
    public static DifferentialPilot newSlavePilot(){
        // 56mm Diameter + 3% Correction
        return new DifferentialPilot(56.0/1.03, 115.0, Motor.B, Motor.C);
    }
}
