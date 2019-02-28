package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchSubsystem extends Subsystem {

    public static DoubleSolenoid eject = new DoubleSolenoid(0, 7, 4);
    public static DoubleSolenoid retainer = new DoubleSolenoid(0, 1, 3);

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