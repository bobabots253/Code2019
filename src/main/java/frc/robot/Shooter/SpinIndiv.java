package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Shooter.ShooterSubsystem.Stage;

public class SpinIndiv extends Command {

    private double speed;
    private Stage stage;

    public SpinIndiv(double speed, Stage stage) {
        this.speed = speed;
        this.stage = stage;
    }
    protected void execute() {
        ShooterSubsystem.spin(speed, stage);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ShooterSubsystem.spin(0, stage);
    }

}