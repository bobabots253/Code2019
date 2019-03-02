package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Misc.Constants;

public class HatchSubsystem extends Subsystem {

    public static DoubleSolenoid eject = new DoubleSolenoid(Constants.kPCM_ID, Constants.kEjectForward, Constants.kEjectReverse);
    public static DoubleSolenoid retainer = new DoubleSolenoid(Constants.kPCM_ID, Constants.kRetainerForward, Constants.kRetainerReverse);

    private static HatchSubsystem instance = null;

    public static HatchSubsystem getInstance() {
        if (instance == null)
            instance = new HatchSubsystem();
        return instance;
    }

    public void ejectHatch() {
        new Eject().start();

    }

    /* Hatch ejector methods */
    public void extend() {
        eject.set(Value.kForward);
    }

    public void retract() {
        eject.set(Value.kReverse);
    }

    public void alternate_ejector() {
        if (eject.get() == Value.kReverse) {
            extend();
        } else {
            retract();
        }
    }

    /* Retaining clamp methods */
    public void retain() {
        retainer.set(Value.kReverse);
    }

    public void release() {
        retainer.set(Value.kForward);
    }

    public void alternate_retainer() {
        if (retainer.get() == Value.kReverse) {
            release();
        } else {
            retain();
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

}