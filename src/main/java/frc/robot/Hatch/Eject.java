package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Autonomous.AutoDelay;

public class Eject extends CommandGroup {
    public Eject(){
        if(HatchSubsystem.retainer.get() == Value.kReverse){ // If retainer is engaged
            addSequential(new RunCommand( () -> HatchSubsystem.release()));
            addSequential(new AutoDelay(0.5));
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new AutoDelay(0.5));
            addSequential(new RunCommand( () -> HatchSubsystem.retract());
        } else {
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new AutoDelay(0.5));
            addSequential(new RunCommand( () -> HatchSubsystem.retract());
        }
    }
}
