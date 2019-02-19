package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class GyroDrive extends Command {

    private double kP = 0.003;
    private double kI = 0.00;
    private double kD = 0.0003;

    private double kF = 0.06; //Minimum motor output required to spin or move
    private double last_error = 0;
    private double sum_error = 0;

    private double last_theta = 0;

    private double dt = 0.02;
    
    
    public GyroDrive(){
        requires(Robot.drivetrain);
    }

    protected void execute() {
        /* Obtaining throttle and turn values */
        double throttle = Robot.oi.getThrottleValue();
        double turn = Robot.oi.getTurnValue();

        double target = 0;
        if(throttle >= 0){
            target = turn*-400; //Rescaling turn stick to ang vel target
        }else {
            target = turn*400;
        }
        SmartDashboard.putNumber("Target", target);

        /* Deriving angular velocity */
        double theta = Robot.oi.getLHPHeading();
        double w = (theta-last_theta)/dt;
        SmartDashboard.putNumber("angvel", w);
        last_theta = theta;
        
        /* Performing PID math */
        double error = target-w;
        SmartDashboard.putNumber("Error", error);
        sum_error += error;
        double w_adjust = kP * error + kI * sum_error + kD * (error-last_error)/dt;
        SmartDashboard.putNumber("gyro response", w_adjust);
        last_error = error;
        System.out.println(w);

        /* Adding minimum movement power */
        double left = -w_adjust;
        double right = w_adjust;

        //Makes sure kF is applied in the right direction
        left += left >= 0 ? kF : -kF; 
        right += right >= 0 ? kF : -kF;

        //Adding speed values
        left += throttle;
        right += throttle;

        //Driving at calculated values
        Drivetrain.drive(left, right);

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void end(){
        Drivetrain.drive(0, 0);
    }

}
