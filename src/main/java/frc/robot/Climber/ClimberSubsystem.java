package frc.robot.Climber;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Misc.Constants;

public class ClimberSubsystem extends Subsystem {

    public static DoubleSolenoid climberFront = new DoubleSolenoid(Constants.kPCM_ID, Constants.kClimbFront, Constants.kRetractFront);
    public static DoubleSolenoid climberBack = new DoubleSolenoid(Constants.kPCM_ID, Constants.kClimbBack, Constants.kRetractBack);

    private static ClimberSubsystem instance = null;

    public static ClimberSubsystem getInstance() {
        if (instance == null)
            instance = new ClimberSubsystem();
        return instance;
    }

    public void climbFront(){
        climberFront.set(Value.kForward);
    }

    public void retractFront(){
        climberFront.set(Value.kReverse);
    }

    public void alternateFront(){

        if (climberFront.get() == Value.kReverse){
            climbFront();
        } else {
            retractFront();
        }
    }

    public void climbBack(){
        climberBack.set(Value.kForward);
    }

    public void retractBack(){
        climberBack.set(Value.kReverse);
    }

    public void alternateBack(){
        if (climberBack.get() == Value.kReverse){
            climbBack();
        } else {
            retractBack();
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

}