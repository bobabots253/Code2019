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
        lspeed = leftStick.getY();
        hspeed = leftStick.getX();
    }
    
    public SpinPaired(Joystick stick){
        lspeed = stick.getY();
        hspeed = -stick.getY();
    }

    protected void execute() {
        ShooterSubsystem.spin(lspeed, hspeed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ShooterSubsystem.spin(0, 0);
    }

}