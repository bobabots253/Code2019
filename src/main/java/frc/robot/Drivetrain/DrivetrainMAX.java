package frc.robot.Drivetrain;
 
import java.util.Arrays;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * NOTICE: This class is the alternate version to the main Drivetrain 
 * Subsystem. Instead of 3 TalonSRX's(3 minCIMS) per side, this edition
 * uses 2 TalonSRX's(2 miniCIMS) and 1 SparkMAX(1 NEO Brushless).
 * Refactor this class to use this drivetrain subsystem instead
 */

public class DrivetrainMAX extends Subsystem {

    //Talon SRX uses CAN ports 1,3,4,6; SparkMAX's uses CAN Ports 2,5!
    private static TalonSRX leftMotorA = new TalonSRX(1), leftMotorC = new TalonSRX(3), 
        rightMotorA = new TalonSRX(4), rightMotorC = new TalonSRX(6);
    
    //NOTE: THESE ARRAY'S OF TALONSRX'S DOES NOT INCLUDE THE SPARKMAX MOTOR CONTROLLERS
    private static final TalonSRX[] motors = {leftMotorA, leftMotorC, rightMotorA, rightMotorC};
    private static final TalonSRX[] leftMotors = {leftMotorA, leftMotorC};
    private static final TalonSRX[] rightMotors = {rightMotorA, rightMotorC};

    private static CANSparkMax leftMotorB = new CANSparkMax(2, MotorType.kBrushless);
    private static CANSparkMax rightMotorB = new CANSparkMax(5, MotorType.kBrushless);

    private static DrivetrainMAX instance = null;
    public DrivetrainMAX getInstance(){
        if(instance == null){
            instance = new DrivetrainMAX();
        }
        return instance;
    }

    public void initDefaultCommand(){
       
    }

    private DrivetrainMAX(){

        //Spark MAX's follows lead TalonSRX(<side>MotorA)
        //SPARK FOLLOWING TALONS CURRENTLY UNAVAILABLE AS OF 1/23/2019, USE Drivetrain.java for now. 
        leftMotorB.follow(ExternalFollower.kFollowerPhoenix, 1);
        leftMotorC.follow(leftMotorA);

        rightMotorB.follow(ExternalFollower.kFollowerPhoenix, 4);
        rightMotorC.follow(rightMotorA);

        Arrays.stream(leftMotors).forEach(motor -> motor.setInverted(true));
        Arrays.stream(rightMotors).forEach(motor -> motor.setInverted(false));

        for (TalonSRX motor : motors) {
            
            // Current and voltage settings
            motor.configPeakCurrentLimit(30);
            motor.configPeakCurrentDuration(500);
            motor.configContinuousCurrentLimit(35);
            motor.configVoltageCompSaturation(12);
            motor.enableVoltageCompensation(true);
            motor.enableCurrentLimit(true);

        if(leftMotorB.getIdleMode() == IdleMode.kCoast) {
            SmartDashboard.putString("Idle Mode", "Coast");
        } else {
            SmartDashboard.putString("Idle Mode", "Brake");
        }
            
        SmartDashboard.putNumber("LVoltage", leftMotorB.getBusVoltage());
        SmartDashboard.putNumber("LTemperature",leftMotorB.getMotorTemperature());
        SmartDashboard.putNumber("LOutput", leftMotorB.getAppliedOutput());

        SmartDashboard.putNumber("RVoltage", rightMotorB.getBusVoltage());
        SmartDashboard.putNumber("RTemperature",rightMotorB.getMotorTemperature());
        SmartDashboard.putNumber("ROutput", rightMotorB.getAppliedOutput());


    }
    }
}
