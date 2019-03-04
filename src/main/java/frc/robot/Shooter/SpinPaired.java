package frc.robot.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SpinPaired extends Command {

    private double lspeed, hspeed;

    public SpinPaired(double lspeed, double hspeed) {
        requires(Robot.shooter);

        this.lspeed = lspeed;
        this.hspeed = hspeed;
    }

    public SpinPaired(Joystick leftStick, Joystick rightStick){
        requires(Robot.shooter);
        lspeed = leftStick.getY();
        hspeed = leftStick.getX();
    }
    
    public SpinPaired(Joystick stick){
        requires(Robot.shooter);
        lspeed = stick.getY();
        hspeed = -stick.getY();
    }

    protected void execute() {
        if(Robot.oi.getRightTrigger() > 0.1 && Robot.oi.getLeftTrigger() < 0.1){
            ShooterSubsystem.spin((Robot.oi.getRightTrigger())/1.5);
        } else if(Robot.oi.getLeftTrigger() > 0.1 && Robot.oi.getRightTrigger() < 0.1){
        ShooterSubsystem.spin(-Robot.oi.getLeftTrigger()/3);
        }else if(Robot.oi.getLeftTrigger() < 0.1 && Robot.oi.getRightTrigger() < 0.1){
            ShooterSubsystem.spin(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ShooterSubsystem.spin(0, 0);
    }

}
