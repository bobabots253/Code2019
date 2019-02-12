package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI;

public class Drive extends Command {

    private double left, right;

    public Drive() {
        requires(Robot.drivetrain);
    }

    protected void execute() {
        double throttle = Robot.oi.getThrottleValue();
        double turn = Robot.oi.getTurnValue();

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

        Drivetrain.drive(left, right);

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Drivetrain.drive(0, 0);
    }

}