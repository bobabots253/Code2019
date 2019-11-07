package frc.robot.Drivetrain;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private static VictorSPX leftMotorA = new VictorSPX(1), leftMotorB = new VictorSPX(2),
            rightMotorA = new VictorSPX(3), rightMotorB = new VictorSPX(5);

    private static final VictorSPX[] motors = { leftMotorA, leftMotorB, rightMotorA, rightMotorB };
    private static final VictorSPX[] leftMotors = { leftMotorA, leftMotorB, };
    private static final VictorSPX[] rightMotors = { rightMotorA, rightMotorB, };

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

        rightMotorB.follow(rightMotorA);
        
        //Setting ramping in open loop control (only needs to be done on masters)
        leftMotorA.configOpenloopRamp(0, 0);
        rightMotorA.configOpenloopRamp(0, 0);
 
        // Drivetrain subsystem negation settings
        Arrays.stream(leftMotors).forEach(motor -> motor.setInverted(true));
        Arrays.stream(rightMotors).forEach(motor -> motor.setInverted(false));

        // Setting common settings for all mspeed controllers
        for (VictorSPX motor : motors) {

            // Current and voltage settings
            /*motor.configPeakCurrentLimit(20);
            motor.configPeakCurrentDuration(500);
            motor.configContinuousCurrentLimit(15);
            motor.configVoltageCompSaturation(12);
            motor.enableVoltageCompensation(true);
            motor.enableCurrentLimit(false);
            
            motor.configNeutralDeadband(0.08, 10);*/
            
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
    
    //Sets all motors to speeds, only requires masters because all other motors follow
    public static void drive(double left, double right){
        leftMotorA.set(ControlMode.PercentOutput, left);
        rightMotorA.set(ControlMode.PercentOutput, right);
    }
    
    //Resets encoders
    public static void resetEncoders(){
        rightMotorA.setSelectedSensorPosition(0);
        leftMotorA.setSelectedSensorPosition(0);
    }
}
