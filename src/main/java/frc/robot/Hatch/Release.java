package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Release extends Command {

    protected void execute(){
        HatchSubsystem.release();
    }

    @Override
    protected boolean isFinished() {
        return HatchSubsystem.retainer.get() == Value.kForward;
    }
    
}