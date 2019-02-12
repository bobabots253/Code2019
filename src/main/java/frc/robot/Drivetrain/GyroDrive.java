package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.GyroController;

public class GyroDrive extends Command {

    private double last_heading = 0;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private double kF = 0;
    private double target = 0;

    private GyroController gyro = new GyroController(kP, kI, kD, 0.02);
    
    public GyroDrive(){
        requires(Robot.drivetrain);
    }

    protected void execute() {
        double cur_heading = Robot.oi.getLHPHeading();

        double ang_vel = (cur_heading - last_heading)/0.02;
        double ang_adjust = gyro.calculate(ang_vel-target);
        last_heading = cur_heading;

        double left = -ang_adjust;
        double right = ang_adjust;

        left += left > 0 ? kF : -kF;
        right += right > 0 ? kF : -kF;

        Drivetrain.drive(Robot.oi.getThrottleValue() + left + Robot.oi.getTurnValue(),
                Robot.oi.getThrottleValue() + right - Robot.oi.getTurnValue());

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
