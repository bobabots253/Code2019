package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.OI;

public class RunShooter extends Command {

    double lStage, hStage;
    
    public RunShooter(double lButtonInput, double hButtonInput){
        this.lStage = lButtonInput;
        this.hStage = hButtonInput;
        requires(Robot.shootersubsystem);
    }

    public RunShooter(){
        requires(Robot.shootersubsystem);
    }
    
    protected void execute() {
        ShooterSubsystem.runShooter(lStage, hStage);
    }

    protected boolean isFinished(){
        return true;
    }
}