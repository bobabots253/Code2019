package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI.CamMode;
import frc.robot.Misc.OI.LEDMode;

public class VisionTrack extends Command {

    private double left, right;

    private double aim_kP = Constants.kP_AimVision;
    private double aim_kI = Constants.kI_AimVision;
    private double aim_kD = Constants.kD_AimVision;

    private double dist_kP = Constants.kP_DistVision;
    private double dist_kI = Constants.kI_DistVision;
    private double dist_kD = Constants.kD_DistVision;

    private double kF = Constants.kF;

    private VisionController aim = new VisionController(aim_kP, aim_kI, aim_kD, 0.02);
    private VisionController distance = new VisionController(dist_kP, dist_kI, dist_kD, 0.02);

    public VisionTrack() {
        requires(Robot.drivetrain);

        SmartDashboard.putNumber("kPAim", aim_kP);
        SmartDashboard.putNumber("kIAim", aim_kI);
        SmartDashboard.putNumber("kDAim", aim_kD);

    }

    @Override
    protected void initialize(){
        Robot.oi.setLEDMode(LEDMode.ON);
        Robot.oi.setPipeline(0);
        Robot.oi.setCamMode(CamMode.VISION);
        
    }

    @Override
    protected void execute() {

        double turn = Robot.oi.getTurnValue();
        double throttle = Robot.oi.getThrottleValue();

        SmartDashboard.putNumber("throttle", throttle);

        double heading_error;
        double distance_error;

        if (!Robot.oi.getTargetValid()) {

            SmartDashboard.putBoolean("Has Target", false);

            heading_error = Robot.oi.getLastValidXOffset();
            distance_error = 0;

        } else {

            SmartDashboard.putBoolean("Has Target", true);

            heading_error = Robot.oi.getxOffset();
            distance_error = Robot.oi.getyOffset();

        }

        if(Math.abs(heading_error) < 1){
            heading_error = 0;
        }

        SmartDashboard.putNumber("distance_err", distance_error);
        SmartDashboard.putNumber("heading_error", heading_error);

        double kP = SmartDashboard.getNumber("kPAim", Constants.kP_AimVision);
        double kI = SmartDashboard.getNumber("kIAim", Constants.kI_AimVision);
        double kD = SmartDashboard.getNumber("kDAim", Constants.kD_AimVision);

        aim.updatePID(kP, kI, kD);

        double steering_adjust = aim.calculate(heading_error);
        double distance_adjust = distance.calculate(distance_error);

        if(Math.abs(throttle) > 0.01){
            left = distance_adjust - steering_adjust;
            right = distance_adjust + steering_adjust;

            left += left > 0 ? kF : -kF;
            right += right > 0 ? kF : -kF;
        } else {
            left = 0;
            right = 0;
        }

        Drivetrain.drive(throttle + left + turn, throttle + right - turn);

        SmartDashboard.putNumber("left", left);
        SmartDashboard.putNumber("right", right);

    }

    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted(){
        end();
    }

    protected void end() {
        Drivetrain.drive(0, 0);

        Robot.oi.setLEDMode(LEDMode.OFF);
        Robot.oi.setPipeline(1);
        //Robot.oi.setCamMode(CamMode.DRIVER);
        
    }

}