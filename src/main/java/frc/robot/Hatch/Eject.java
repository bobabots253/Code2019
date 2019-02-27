package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Misc.*;
import frc.robot.Autonomous.AutoDelay;

public class Eject extends CommandGroup {
    public Eject(){
        System.out.println("You work right?");
        if(HatchSubsystem.retainer.get() != Value.kReverse){ // If retainer is engaged
            System.out.println("Not else");
            addSequential(new RunCommand( () -> HatchSubsystem.release()));
            addSequential(new AutoDelay(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new AutoDelay(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.retract()));
        } else {
            System.out.println("Else");
            addSequential(new RunCommand( () -> HatchSubsystem.extend()));
            addSequential(new AutoDelay(0.25));
            addSequential(new RunCommand( () -> HatchSubsystem.retract()));
        }
    }
}
