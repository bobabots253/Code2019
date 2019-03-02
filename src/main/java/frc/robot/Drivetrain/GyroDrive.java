package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.Constants;

public class GyroDrive extends Command {

    private double kP = Constants.kP_Gyro;
    private double kI = Constants.kI_Gyro;
    private double kD = Constants.kD_Gyro;

    private double kF = Constants.kF; 

    // 0.02 is robot loop speed (50Hz), consider placing into Notifier running
    // something faster for better accuracy
    private GyroController gyro = new GyroController(kP, kI, kD, 0.02);

    public GyroDrive() {
        requires(Robot.drivetrain);
    }

    protected void execute() {
        // Getting desired speeds
        double throttle = Robot.oi.getThrottleValue();
        double turn = Robot.oi.getTurnValue() * Constants.kTurnScaleGyro;

        // Calculating output
        double omega = Robot.oi.getAngularVelocity();
        double ang_adjust = gyro.calculate(turn - omega); // Calculating required correction values based on error

        double left = throttle - ang_adjust;
        double right = throttle + ang_adjust;

        // Makes sure kF is applied in the right direction
        if (left > 0) {
            left += kF;
        } else if (left < 0) {
            left -= kF;
        }
        if (right > 0) {
            right += kF;
        } else if (right < 0) {
            right -= kF;
        }

        // Drives robot at calculated speeds
        Drivetrain.drive(left, right);

    }

    // Does not need to finish, should be run until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Safety when command ends
    @Override
    protected void end() {
        Drivetrain.drive(0, 0);
    }
}
