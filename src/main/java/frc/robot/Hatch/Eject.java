package frc.robot.Hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.Misc.RunCommand;

public class Eject extends CommandGroup {
    public Eject() {
        setInterruptible(false);
        if (HatchSubsystem.retainer.get() == Value.kReverse) { // If retainer is engaged
            System.out.println("Ejecting hatch with retainer drop!");
            addSequential(new RunCommand(() -> Robot.hatch.release()));
            addSequential(new WaitCommand(0.125));
            addSequential(new RunCommand(() -> Robot.hatch.extend()));
            addSequential(new WaitCommand(0.125));
            addSequential(new RunCommand(() -> Robot.hatch.retract()));
        } else {
            System.out.println("Ejecting hatch without retainer drop!");
            addSequential(new RunCommand(() -> Robot.hatch.extend()));
            addSequential(new WaitCommand(0.125));
            addSequential(new RunCommand(() -> Robot.hatch.retract()));
        }
    }

}
