package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoDelay extends Command{
    private double timeout;

    public AutoDelay(double timeout){
        requires(Robot.drivetrain);
        this.timeout = timeout;
    }

    protected void initialize(){
        System.out.println("Delaying for " + timeout + " seconds!");
        setTimeout(timeout);

    }

    protected void execute(){

    }

    protected void end(){

    }

    protected boolean isFinished(){
        return isTimedOut();
    }
}