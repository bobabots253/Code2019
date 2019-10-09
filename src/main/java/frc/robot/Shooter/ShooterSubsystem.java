package frc.robot.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ShooterSubsystem extends Subsystem {

    private static TalonSRX motorLow = new TalonSRX(5);
    private static TalonSRX motorHigh = new TalonSRX(8);

    // Safety net ensuring only one shooter subsystem can be created
    private static ShooterSubsystem instance = null;
    public static ShooterSubsystem getInstance() {
        if (instance == null)
            instance = new ShooterSubsystem();
        return instance;
    }

    private ShooterSubsystem() {

        motorLow.configPeakCurrentLimit(60);
        motorLow.configPeakCurrentDuration(500);
        motorLow.configContinuousCurrentLimit(60);
        motorLow.enableCurrentLimit(true);

        motorHigh.configPeakCurrentLimit(60);
        motorHigh.configPeakCurrentDuration(500);
        motorHigh.configContinuousCurrentLimit(60);
        motorHigh.enableCurrentLimit(true);        

        motorLow.configVoltageCompSaturation(12);
        motorLow.enableVoltageCompensation(true);

        motorHigh.configVoltageCompSaturation(12);
        motorHigh.enableVoltageCompensation(true);

        // Lower shooter axle encoder settings
        motorLow.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        motorLow.setSensorPhase(true);

        motorHigh.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        motorHigh.setSensorPhase(false);

        

    }

    public static void spin(double lvolt, double hvolt) {
        motorLow.set(ControlMode.PercentOutput, lvolt);
        motorHigh.set(ControlMode.PercentOutput, hvolt);
 
    }

    public static void spin(double input){
        motorLow.set(ControlMode.PercentOutput, input);
        motorHigh.set(ControlMode.PercentOutput, -input);
    }

    public static void spin(double speed, Stage stage){
        if(stage == Stage.LOWER){
            motorLow.set(ControlMode.PercentOutput, speed);
        } else if(stage == Stage.HIGHER){
            motorHigh.set(ControlMode.PercentOutput, speed);
        }
    }

    public static void spinVelocity(double lvelo, double hvelo) {
        motorLow.set(ControlMode.Velocity, lvelo);
        motorHigh.set(ControlMode.Velocity, hvelo);
        SmartDashboard.putNumber("Encoder Lower Stage", motorLow.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Encoder Upper Stage", motorHigh.getSelectedSensorPosition(0));
    }

    public void resetEncoders(){
        motorLow.setSelectedSensorPosition(0,0,10);
        motorHigh.setSelectedSensorPosition(0,0,10);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SmartDashControl());
    }

    public enum Stage {
        LOWER(0), HIGHER(1);

        public final int value;

        Stage(int value){
            this.value = value;
        }
    }

}