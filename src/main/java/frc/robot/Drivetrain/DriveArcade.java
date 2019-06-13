package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveArcade extends Command {

    private double left, right;
    private boolean isVelocityTarget = false;

    public DriveArcade() {
        requires(Robot.drivetrain);
    }

    public DriveArcade(double leftFTPS, double rightFTPS){
        this.isVelocityTarget = true;
        this.left = leftFTPS;
        this.right = rightFTPS;
    }

    protected void execute() {
        if (!isVelocityTarget) {
            double throttle = Robot.oi.getThrottleValue();
            double turn = Robot.oi.getTurnValue();

            throttle = throttle * SmartDashboard.getNumber("Speed Modifier", Constants.kDriveSens);

            left = throttle + turn;
            right = throttle - turn;

            Drivetrain.drive(left, right);
        } else {
            Drivetrain.driveFTPS(left, right);
        }

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Drivetrain.drive(0, 0);
    }

}