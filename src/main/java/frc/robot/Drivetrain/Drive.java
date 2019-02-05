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

        boolean quickturn = Robot.oi.isQuickturn();
        double qLeft = Robot.oi.getLeftTrigger();
        double qRight = Robot.oi.getRightTrigger();

        left = throttle + throttle * turn - qLeft + qRight;
        right = throttle - throttle * turn - qRight + qLeft;

        left = OI.exponentiate(left, 2);
        right = OI.exponentiate(right, 2);
        
        SmartDashboard.putNumber("left", left);
        SmartDashboard.putNumber("right", right);
        Drivetrain.drive(left, right);

    }

    protected boolean isFinished(){
        return true;
    }

    protected void end(){
        Drivetrain.drive(0, 0);
    }

}