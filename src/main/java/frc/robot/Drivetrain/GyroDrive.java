package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.GyroController;

public class GyroDrive extends Command {

    private double last_heading = 0;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private double kF = 0; //Minimum motor output required to spin or move
    
    //TODO: Remove this and make the command work based on the turn stick 
    //AlSO TODO: Combine this and the other drive command, probably use an enum or something to manage which mode
    private double target = 0; 
    
    //0.02 is robot loop speed (50Hz), consider placing into Notifier running something faster for better accuracy 
    private GyroController gyro = new GyroController(kP, kI, kD, 0.02);
    
    public GyroDrive(){
        requires(Robot.drivetrain);
    }

    protected void execute() {
        double cur_heading = Robot.oi.getLHPHeading(); //Getting the heading (left hand positive)
        double omega = (cur_heading - last_heading)/0.02; //Deriving smol omega, angular velocity
        double ang_adjust = gyro.calculate(target-omega); //Calculating required correction values based on error
        last_heading = cur_heading;

        double left = -ang_adjust; 
        double right = ang_adjust; 
    
        //Makes sure kF is applied in the right direction
        left += left > 0 ? kF : -kF; 
        right += right > 0 ? kF : -kF;
        
        //Drives robot at calculated speeds (plus current throttle and turn values)
        Drivetrain.drive(Robot.oi.getThrottleValue() + left + Robot.oi.getTurnValue(),
                Robot.oi.getThrottleValue() + right - Robot.oi.getTurnValue());

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
