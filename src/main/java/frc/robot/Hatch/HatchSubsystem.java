package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchSubsystem extends Subsystem {

    public DoubleSolenoid eject = new DoubleSolenoid(1, 2, 3);

    private static HatchSubsystem instance = null;

    public static HatchSubsystem getInstance() {
        if (instance == null)
            instance = new HatchSubsystem();
        return instance;
    }

    private HatchSubsystem() {
        
    }

    @Override
    protected void initDefaultCommand() {

    }

}