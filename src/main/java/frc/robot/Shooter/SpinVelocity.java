package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Misc.OI;

public class SpinVelocity extends Command {

    private int lrpm, hrpm;

    public SpinVelocity(int lrpm, int hrpm) {
        requires(Robot.shooter);

        this.lrpm = lrpm;
        this.hrpm = hrpm;
    }

    protected void execute() {
        ShooterSubsystem.spinVelocity(OI.RPMtoTicksPerDecisecond((lrpm)), OI.RPMtoTicksPerDecisecond(hrpm));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ShooterSubsystem.spin(0, 0);
    }

}