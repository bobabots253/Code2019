package frc.robot.Drivetrain;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Misc.Constants;

public class Drivetrain extends Subsystem {

    // Declarations are using IMotorController, the superclass to TalonSRX and
    // VictorSPX
    public static TalonSRX leftMotorA = new TalonSRX(Constants.leftMotorA), leftMotorB = new TalonSRX(Constants.leftMotorB), 
    rightMotorA = new TalonSRX(Constants.rightMotorA), rightMotorB = new TalonSRX(Constants.rightMotorB);

    private static VictorSPX leftMotorC = new VictorSPX(Constants.leftMotorC), rightMotorC = new VictorSPX(Constants.rightMotorC);

    private static IMotorController[] motors = { leftMotorA, leftMotorB, leftMotorC, rightMotorA, rightMotorB,
            rightMotorC };
    private static final IMotorController[] leftMotors = { leftMotorA, leftMotorB, leftMotorC };
    private static final IMotorController[] rightMotors = { rightMotorA, rightMotorB, rightMotorC };

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        if (instance == null)
            instance = new Drivetrain();
        return instance;
    }

    // Sets up the Drivetrain to automatically run the teleoperated driving command
    // when no other commands are being run
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
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

        // Setting common settings for all speed controllers
        for (IMotorController motor : motors) {
            if (motor instanceof TalonSRX) {
                TalonSRX talon = (TalonSRX) motor;

                talon.configPeakCurrentLimit(15);
                talon.configPeakCurrentDuration(250);
                talon.configContinuousCurrentLimit(10);
                talon.enableCurrentLimit(false);
            }

            motor.configVoltageCompSaturation(12, 10);
            motor.enableVoltageCompensation(true);

        }

        leftMotorA.configClosedloopRamp(0.25);
        rightMotorA.configClosedloopRamp(0.25);
        // Left drivetrain encoder
        // leftMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1,
        // 10);
        leftMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        leftMotorA.setSensorPhase(false);

        // Right drivetrain encoder
        // rightMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1,
        // 10);
        rightMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rightMotorA.setSensorPhase(false);

    }

    public static void drive(double left, double right) {
        leftMotorA.set(ControlMode.PercentOutput, left);
        rightMotorA.set(ControlMode.PercentOutput, right);
    }

    public static void driveFTPS(double leftFTPS, double rightFTPS) {
        leftMotorA.set(ControlMode.Velocity, 400);
        rightMotorA.set(ControlMode.Velocity, 400);
    }

    public static void resetEncoders() {
        rightMotorA.setSelectedSensorPosition(0);
        leftMotorA.setSelectedSensorPosition(0);
    }

    public static void setBrakeMode() {

        leftMotorA.setNeutralMode(NeutralMode.Brake);
        leftMotorB.setNeutralMode(NeutralMode.Brake);
        leftMotorC.setNeutralMode(NeutralMode.Brake);
        rightMotorA.setNeutralMode(NeutralMode.Brake);
        rightMotorB.setNeutralMode(NeutralMode.Brake);
        rightMotorC.setNeutralMode(NeutralMode.Brake);

    }

    public static void setCoastMode() {

        leftMotorA.setNeutralMode(NeutralMode.Coast);
        leftMotorB.setNeutralMode(NeutralMode.Coast);
        leftMotorC.setNeutralMode(NeutralMode.Coast);
        rightMotorA.setNeutralMode(NeutralMode.Coast);
        rightMotorB.setNeutralMode(NeutralMode.Coast);
        rightMotorC.setNeutralMode(NeutralMode.Coast);

    }
}