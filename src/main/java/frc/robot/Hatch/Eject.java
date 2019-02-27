package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Misc.RunCommand;

public class Eject extends CommandGroup {
    public Eject(){
        setInterruptible(false);
        if(HatchSubsystem.retainer.get() == Value.kReverse){ // If retainer is engaged
            System.out.println("Ejecting hatch with retainer drop!");
            addSequential(new RunCommand( () -> HatchSubsystem.release()));
            addSequential(new WaitCommand(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new WaitCommand(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.retract()));
        } else {
            System.out.println("Ejecting hatch without dropping retainer!");
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new WaitCommand(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.retract()));
        }
    }
    
}
