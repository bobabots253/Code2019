package frc.robot.Drivetrain;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

    // Declarations are using IMotorController, the superclass to TalonSRX and
    // VictorSPX
    //public static TalonSRX leftMotorA = new TalonSRX(1), leftMotorB = new TalonSRX(2), rightMotorA = new TalonSRX(3),
      //      rightMotorB = new TalonSRX(4);

    private static VictorSPX leftMotorA = new VictorSPX(2), leftMotorB = new VictorSPX(3), rightMotorA = new VictorSPX(1), rightMotorB = new VictorSPX(5);

    private static IMotorController[] motors = { leftMotorA, leftMotorB, rightMotorA, rightMotorB
             };
    private static final IMotorController[] leftMotors = { leftMotorA, leftMotorB  };
    private static final IMotorController[] rightMotors = { rightMotorA, rightMotorB};

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
      //  leftMotorC.follow(leftMotorA);

        rightMotorB.follow(rightMotorA);
        //rightMotorC.follow(rightMotorA);

        // Drivetrain subsystem negation settings
        Arrays.stream(leftMotors).forEach(motor -> motor.setInverted(false));
        Arrays.stream(rightMotors).forEach(motor -> motor.setInverted(true));

        // Setting common settings for all speed controllers
        for (IMotorController motor : motors) {
            if (motor instanceof TalonSRX) {
                TalonSRX talon = (TalonSRX) motor;

                talon.configPeakCurrentLimit(30);
                talon.configPeakCurrentDuration(500);
                talon.configContinuousCurrentLimit(35);
                talon.enableCurrentLimit(false);
            }

            motor.configVoltageCompSaturation(12, 10);
            motor.enableVoltageCompensation(true);

        }
        /*
        // Left drivetrain encoder
        // leftMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1,
        // 10);
        leftMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        leftMotorA.setSensorPhase(false);

        // Right drivetrain encoder
        // rightMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1,
        // 10);
        rightMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rightMotorA.setSensorPhase(false);*/


    }

    public static void drive(double left, double right) {
        leftMotorA.set(ControlMode.PercentOutput, left);
        rightMotorA.set(ControlMode.PercentOutput, right);

        SmartDashboard.putNumber("left out", left);
        SmartDashboard.putNumber("right out", right);

    }

    public static void driveFTPS(double leftFTPS, double rightFTPS){
        leftMotorA.set(ControlMode.Velocity, 400);
        rightMotorA.set(ControlMode.Velocity, 400);
    }

    public static void resetEncoders() {
        rightMotorA.setSelectedSensorPosition(0);
        leftMotorA.setSelectedSensorPosition(0);
    }

    public static void setBrakeMode() {
        
        leftMotorA.setNeutralMode(NeutralMode.Brake);
        rightMotorA.setNeutralMode(NeutralMode.Brake);
    
    }

    public static void setCoastMode() {
        
        leftMotorA.setNeutralMode(NeutralMode.Coast);
        rightMotorA.setNeutralMode(NeutralMode.Coast);
        
    }
}