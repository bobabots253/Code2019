package frc.robot.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI;

public class SpinPaired extends Command {

    private InputType input;

    private double lspeed, hspeed;
    private Joystick leftStick, rightStick;
    private Joystick stick;

    public SpinPaired(double lspeed, double hspeed) {
        requires(Robot.shooter);
        this.input = InputType.SPEED;

        this.lspeed = lspeed;
        this.hspeed = hspeed;
    }

    public SpinPaired(Joystick leftStick, Joystick rightStick){
        requires(Robot.shooter);
        this.input = InputType.DOUBLE_JOYSTICK;

        this.leftStick = leftStick;
        this.rightStick = rightStick;
    }
    
    public SpinPaired(Joystick stick){
        requires(Robot.shooter);
        this.input = InputType.SINGLE_JOYSTICK;

        this.stick = stick;
    }
    

    protected void execute() {
        switch(input){
            case SPEED:
                ShooterSubsystem.spin(this.lspeed, this.hspeed);
                break;
            case SINGLE_JOYSTICK:
                ShooterSubsystem.spin(OI.deadbandX(this.stick.getY(), Constants.kJoystickDeadband), OI.deadbandX(-this.stick.getY(), Constants.kJoystickDeadband));
                break;
            case DOUBLE_JOYSTICK:
                ShooterSubsystem.spin(this.leftStick.getY(), this.rightStick.getY());
            default:
                ShooterSubsystem.spin(0, 0);
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ShooterSubsystem.spin(0, 0);
    }

    private enum InputType {
        SINGLE_JOYSTICK, DOUBLE_JOYSTICK, SPEED;
    }

}