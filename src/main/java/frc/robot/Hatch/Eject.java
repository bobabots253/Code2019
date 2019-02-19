package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Autonomous.AutoDelay;

public class Eject extends CommandGroup {
    public Eject(){
        if(HatchSubsystem.retainer.get() == Value.kReverse){ // If retainer is engaged
            addSequential(new Release());
            addSequential(new AutoDelay(0.5));
            addSequential(new Extend());
            addSequential(new AutoDelay(0.5));
        }
    }
}