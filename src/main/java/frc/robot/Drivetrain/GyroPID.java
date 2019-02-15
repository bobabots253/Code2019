package frc.robot.Drivetrain;

import frc.robot.Robot;

public class GyroPID implements Runnable {

    private double kP = 0.00;
    private double kI = 0.00;
    private double kD = 0.00;

    private double kF = 0.062; //Minimum motor output required to spin or move
    private double last_error = 0;
    private double sum_error = 0;

    private double last_theta = 0;
    private double dt = 0.02;

    public GyroPID(double dt){
        this.dt = dt;
    }

    public GyroPID(){}

    @Override
    public void run() {
        /* Obtaining throttle and turn values */
        double throttle = Robot.oi.getThrottleValue();
        double turn = Robot.oi.getTurnValue();

        double target = turn*20; //Rescaling turn stick to ang vel target

        /* Deriving angular velocity */
        double theta = Robot.oi.getLHPHeading();
        double w = (theta-last_theta)/dt;
        last_theta = theta;
        
        /* Performing PID math */
        double error = target-w;
        sum_error += error;
        double w_adjust = kP * error + kI * sum_error + kD * (error-last_error)/dt;
        last_error = error;

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
    
}