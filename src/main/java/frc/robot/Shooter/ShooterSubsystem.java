package frc.robot.Shooter;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ShooterSubsystem extends Subsystem {

    private static VictorSPX lowerStage = new VictorSPX(7);
    private static VictorSPX upperStage = new VictorSPX(8);
   
    private static ShooterSubsystem instance = null;
    public static ShooterSubsystem getInstance() {
        if (instance == null)
            instance = new ShooterSubsystem();
        return instance;
    }


    private ShooterSubsystem(){

        lowerStage.enableVoltageCompensation(true);
        lowerStage.configVoltageCompSaturation(12);
        
        upperStage.enableVoltageCompensation(true);
        upperStage.configVoltageCompSaturation(12);
    }

    public static void runShooter(double lStage, double hStage){
        lowerStage.set(ControlMode.PercentOutput, lStage);
        upperStage.set(ControlMode.PercentOutput, hStage);
    }
    
    @Override
    protected void initDefaultCommand() {
        
    }



    

}