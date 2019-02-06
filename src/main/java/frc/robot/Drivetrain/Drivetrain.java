package frc.robot.Drivetrain;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private static TalonSRX leftMotorA = new TalonSRX(1), leftMotorB = new TalonSRX(2), leftMotorC = new TalonSRX(3),
            rightMotorA = new TalonSRX(4), rightMotorB = new TalonSRX(5), rightMotorC = new TalonSRX(6);

    private static final TalonSRX[] motors = { leftMotorA, leftMotorB, leftMotorC, rightMotorA, rightMotorB,
            rightMotorC };
    private static final TalonSRX[] leftMotors = { leftMotorA, leftMotorB, leftMotorC };
    private static final TalonSRX[] rightMotors = { rightMotorA, rightMotorB, rightMotorC };

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        if (instance == null)
            instance = new Drivetrain();
        return instance;
    }

    // Sets up the Drivetrain to automatically run the teleoperated driving command
    // when no other commands are being run
    public void initDefaultCommand() {
    }

    // Talon configuration should only run once so it goes in the constructor
    private Drivetrain() {

        // Setting masters and followers
        leftMotorB.follow(leftMotorA);
        leftMotorC.follow(leftMotorA);

        rightMotorB.follow(rightMotorA);
        rightMotorC.follow(rightMotorA);

        // Drivetrain subsystem negation settings
        Arrays.stream(leftMotors).forEach(motor -> motor.setInverted(true));
        Arrays.stream(rightMotors).forEach(motor -> motor.setInverted(false));

        // Setting common settings for all mspeed controllers
        for (TalonSRX motor : motors) {

            // Current and voltage settings
            motor.configPeakCurrentLimit(30);
            motor.configPeakCurrentDuration(500);
            motor.configContinuousCurrentLimit(35);
            motor.configVoltageCompSaturation(12);
            motor.enableVoltageCompensation(true);
            motor.enableCurrentLimit(true);

        }

        // Left drivetrain encoder
        //leftMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        leftMotorA.setSensorPhase(true);

        // Right drivetrain encoder
        //rightMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        rightMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rightMotorA.setSensorPhase(false);

    }

    public static void drive(double left, double right){
        leftMotorA.set(ControlMode.PercentOutput, left);
        rightMotorA.set(ControlMode.PercentOutput, right);
    }

    public static void resetEncoders(){
        rightMotorA.setSelectedSensorPosition(0);
        leftMotorA.setSelectedSensorPosition(0);
    }
}