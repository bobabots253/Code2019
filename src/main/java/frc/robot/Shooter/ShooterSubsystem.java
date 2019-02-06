package frc.robot.Shooter;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ShooterSubsystem extends Subsystem {

    private static VictorSPX motorLow = new VictorSPX(7);
    private static VictorSPX motorHigh = new VictorSPX(8);

    // Safety net ensuring only one shooter subsystem can be created
    private static ShooterSubsystem instance = null;
    public static ShooterSubsystem getInstance() {
        if (instance == null)
            instance = new ShooterSubsystem();
        return instance;
    }

    private ShooterSubsystem() {

        motorLow.configVoltageCompSaturation(12);
        motorLow.enableVoltageCompensation(true);

        motorHigh.configVoltageCompSaturation(12);
        motorHigh.enableVoltageCompensation(true);

        // Lower shooter axle encoder settings
        motorLow.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        motorLow.setSensorPhase(true);

    }

    public static void spin(double lvolt, double hvolt) {
        motorLow.set(ControlMode.PercentOutput, lvolt);
        motorHigh.set(ControlMode.PercentOutput, hvolt);
    }

    public static void spinVelocity(double lvelo, double hvelo) {
        motorLow.set(ControlMode.Velocity, lvelo);
        motorHigh.set(ControlMode.Velocity, hvelo);
    }

    @Override
    protected void initDefaultCommand() {

    }

}