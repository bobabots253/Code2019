package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.OI;

public class RunShooter extends Command {

    private double lStage, hStage;
    
    public RunShooter(double lButtonInput, double hButtonInput){
        this.lStage = lButtonInput;
        this.hStage = hButtonInput;
        requires(Robot.shootersubsystem);
    }
    
    protected void execute() {
        ShooterSubsystem.runShooter(lStage, hStage);
    }

    @Override
    protected boolean isFinished(){
        return true;
    }

    protected void end(){
        ShooterSubsystem.runShooter(0, 0);

    }

}