package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Command {

    private double left, right;
    private boolean isVelocityTarget = false;

    public Drive() {
        requires(Robot.drivetrain);
    }

    public Drive(double leftFTPS, double rightFTPS){
        this.isVelocityTarget = true;
        this.left = leftFTPS;
        this.right = rightFTPS;
    }

    protected void execute() {
        if (!isVelocityTarget) {
            double throttle = Robot.oi.getThrottleValue();
            double turn = Robot.oi.getTurnValue();

            throttle = throttle * Constants.kDriveSens;

            SmartDashboard.putNumber("throttle", throttle);
            SmartDashboard.putNumber("turn", turn);

            if (throttle != 0) {
                left = throttle + throttle * turn * Constants.kTurnSens;
                right = throttle - throttle * turn * Constants.kTurnSens;

                left = OI.exponentiate(left, Constants.kDriveExpScale);
                right = OI.exponentiate(right, Constants.kDriveExpScale);

            } else {

                left = turn;
                right = -turn;

                left = OI.exponentiate(left, Constants.kTurnInPlaceExpScale);
                right = OI.exponentiate(right, Constants.kTurnInPlaceExpScale);

            }

            SmartDashboard.putNumber("left", left);
            SmartDashboard.putNumber("right", right);

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