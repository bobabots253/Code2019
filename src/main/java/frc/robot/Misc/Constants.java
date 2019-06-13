package frc.robot.Misc;

public class Constants {

    public static final int kPCM_ID = 1; // PCM ID

    /* Driver Settings */
    public static final double kJoystickDeadband = 0.03; // Number between 0 and 1 representing how much of joystick is "dead" zone
    public static final double kTriggerDeadband = 0.05; // Number between 0 and 1 representing how much of trigger is "dead" zone

    public static final double kDriveExpScale = 1; // What power to put overall control to, (makes small inputs smaller for better control)
    public static final double kTurnSens = 0.86; // Maximum normal turning rate (in percent of max) to allow robot to turn to, b/t 0 and 1

    public static final double kTurnInPlaceExpScale = 2; // What power to put turn-in-place turning to (makes small inputs smaller for better control)
    public static final double kTurnInPlaceSens = 0.8; // Maximum turn-in-place rate (in percent of max) to allow robot to turn to, b/t 0 and 1

    public static final double kDriveSens = 1; // Modifier for overall driving speed (turn down to limit for demoing, for example)

    /* PID Loops */
    public static final double kF = 0.88/12; // Minimum value (in percent max speed) to move the robot, b/t 0 and 1

    // Angular velocity PID gains
    public static final double kP_Gyro = 0;
    public static final double kI_Gyro = 0;
    public static final double kD_Gyro = 0;
    public static final double kTurnScaleGyro = 20; // Sets maximum turn rate (in degrees) when using angular velocity control

    // Vision PID gains
    public static final double kP_AimVision = 0.044; 
    public static final double kI_AimVision = 0;
    public static final double kD_AimVision = 0.003;

    public static final double kP_DistVision = 0;
    public static final double kI_DistVision = 0;
    public static final double kD_DistVision = 0;

    /* Drivetrain Motor IDs */
    public static final int leftMotorA = 1;//TalonSRX
    public static final int leftMotorB = 2;//TalonSRX
    public static final int leftMotorC = 1;//VictorSPX

    public static final int rightMotorA = 3;//TalonSRX
    public static final int rightMotorB = 4;//TalonSRX
    public static final int rightMotorC = 2;//VictorSPX

    /* Shooter Motor IDs */
    public static final int kUpperStage = 8; // ID of upper stage motor
    public static final int kLowerStage = 5; // ID of lower stage motor

    /* Hatch Solenoid IDs */
    public static final int kEjectForward = 3; // Number between 0 and 7
    public static final int kEjectReverse = 1; // Number between 0 and 7

    public static final int kRetainerForward = 4; // Number between 0 and 7
    public static final int kRetainerReverse = 7; // Number between 0 and 7


}