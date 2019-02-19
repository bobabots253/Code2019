package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Retract extends Command {

    protected void execute(){
        HatchSubsystem.retract();
    }

    @Override
    protected boolean isFinished() {
        return HatchSubsystem.eject.get() == Value.kReverse;
    }
    
}