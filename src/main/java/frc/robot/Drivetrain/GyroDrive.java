package frc.robot.Drivetrain;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GyroDrive extends Command {

    // Creates a new Notifier to run this Command at nyoom speeds
    private double dt = 0.02;
    private Notifier notifier = new Notifier(new GyroPID(dt));
    
    
    public GyroDrive(){
        requires(Robot.drivetrain);
        notifier.startPeriodic(dt); //Starting Notifier in constructor
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void end(){
        Drivetrain.drive(0, 0);
        notifier.stop();
    }

}
