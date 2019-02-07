package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchSubsystem extends Subsystem {

    public static DoubleSolenoid pistons = new DoubleSolenoid(1, 2, 3);

    private static HatchSubsystem instance = null;
    public static HatchSubsystem getInstance() {
        if (instance == null)
            instance = new HatchSubsystem();
        return instance;
    }

    public static void extend() {
        pistons.set(Value.kForward);
    }

    public static void retract() {
        pistons.set(Value.kReverse);
    }

    public static void alternate(){
        if(pistons.get() == Value.kReverse){
            extend();
        } else {
            retract();
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

}