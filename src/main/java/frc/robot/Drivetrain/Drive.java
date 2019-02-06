package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Misc.OI;

public class Drive extends Command {

    double left, right;

    public Drive() {
        requires(Robot.drivetrain);
    }

    protected void execute() {
        
        double throttle = Robot.oi.getThrottleValue();
        double turn = Robot.oi.getTurnValue();
        
        if(throttle != 0){
            left = throttle + throttle * turn;
            right = throttle - throttle * turn;

            //left = OI.exponentiate(left, 2);
            //right = OI.exponentiate(right, 2);
        } else {
            left = turn;
            right = -turn;
        }
        
        SmartDashboard.putNumber("leftCommanded", left);
        SmartDashboard.putNumber("rightCommanded", right);
        
        Drivetrain.drive(left, right);

    }

    protected boolean isFinished(){
        return false;
    }

    protected void end(){
        Drivetrain.drive(0, 0);
    }

}
