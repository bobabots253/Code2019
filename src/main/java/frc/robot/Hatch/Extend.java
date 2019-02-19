package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Extend extends Command {

    protected void execute(){
        HatchSubsystem.extend();
    }

    @Override
    protected boolean isFinished() {
        return HatchSubsystem.eject.get() == Value.kForward;
    }
    
}