package frc.robot.Misc;

import static frc.robot.Misc.XBPovButton.DOWN;
import static frc.robot.Misc.XBPovButton.DOWN_LEFT;
import static frc.robot.Misc.XBPovButton.DOWN_RIGHT;
import static frc.robot.Misc.XBPovButton.LEFT;
import static frc.robot.Misc.XBPovButton.NONE;
import static frc.robot.Misc.XBPovButton.RIGHT;
import static frc.robot.Misc.XBPovButton.UP;
import static frc.robot.Misc.XBPovButton.UP_LEFT;
import static frc.robot.Misc.XBPovButton.UP_RIGHT;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Drivetrain.GyroDrive;
import frc.robot.Drivetrain.VisionTrack;
import frc.robot.Shooter.ShooterSubsystem;
import frc.robot.Shooter.SpinVelocity;

public class OI {

    public Joystick joystick2 = new Joystick(2);
    public Joystick joystick3 = new Joystick(3);

    private XboxController xboxcontroller;

    private JoystickButton ButtonA;
    private JoystickButton ButtonB;
    private JoystickButton ButtonX;
    private JoystickButton ButtonY;
    private JoystickButton ButtonRB;
    private JoystickButton ButtonLB;
    private JoystickButton ButtonRT;
    private JoystickButton ButtonLT;

    private JoystickButton dpadUP;
    private JoystickButton dpadUP_RIGHT;
    private JoystickButton dpadRIGHT;
    private JoystickButton dpadDOWN_RIGHT;
    private JoystickButton dpadDOWN;
    private JoystickButton dpadDOWN_LEFT;
    private JoystickButton dpadLEFT;
    private JoystickButton dpadUP_LEFT;
    private JoystickButton dpadNONE;

    private JoystickButton triggerLeft;
    private JoystickButton triggerRight;

    private NetworkTable limelight;
    private double last_valid_x_offset = 0;
    private AHRS navX = new AHRS(SPI.Port.kMXP, (byte)200);

    private static OI instance = null;

    public static OI getInstance() {
        if (instance == null)
            instance = new OI();
        return instance;
    }

    public OI() {
        xboxcontroller = new XboxController(0);

        ButtonA = new JoystickButton(xboxcontroller, 1);
        ButtonB = new JoystickButton(xboxcontroller, 2);
        ButtonX = new JoystickButton(xboxcontroller, 3);
        ButtonY = new JoystickButton(xboxcontroller, 4);
        ButtonRB = new JoystickButton(xboxcontroller, 6);
        ButtonLB = new JoystickButton(xboxcontroller, 5);
        ButtonRT = new JoystickButton(xboxcontroller, 7);
        ButtonLT = new JoystickButton(xboxcontroller, 8);

        dpadUP = new XBPovButton(xboxcontroller, UP);
        dpadUP_RIGHT = new XBPovButton(xboxcontroller, UP_RIGHT);
        dpadRIGHT = new XBPovButton(xboxcontroller, RIGHT);
        dpadDOWN_RIGHT = new XBPovButton(xboxcontroller, DOWN_RIGHT);
        dpadDOWN = new XBPovButton(xboxcontroller, DOWN);
        dpadDOWN_LEFT = new XBPovButton(xboxcontroller, DOWN_LEFT);
        dpadLEFT = new XBPovButton(xboxcontroller, LEFT);
        dpadUP_LEFT = new XBPovButton(xboxcontroller, UP_LEFT);
        dpadNONE = new XBPovButton(xboxcontroller, NONE);

        triggerLeft = new TriggerButton(xboxcontroller, Hand.kLeft);
        triggerRight = new TriggerButton(xboxcontroller, Hand.kRight);

        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        if (Robot.shooter != null) {
            /*ButtonA.whenPressed(new SpinIndiv(1, Stage.LOWER));
            ButtonA.whenReleased(new SpinIndiv(0, Stage.LOWER));

            ButtonX.whenPressed(new SpinIndiv(1, Stage.HIGHER));
            ButtonX.whenReleased(new SpinIndiv(0, Stage.HIGHER));*/

            ButtonA.whenPressed(new SpinVelocity(500, 500));
            ButtonA.whenReleased(new RunCommand( () -> ShooterSubsystem.spin(0,0)));
        }

        if (Robot.hatch != null) {
            ButtonB.whenPressed(new RunCommand(() -> Robot.hatch.ejectHatch()));
            ButtonY.whenPressed(new RunCommand(() -> Robot.hatch.alternate_retainer()));
        }

        if (Robot.drivetrain != null) {
            ButtonRB.whileHeld(new GyroDrive());
            ButtonLB.whileHeld(new VisionTrack());
        }

    }

    /*
     * Methods for navX gyro
     */
    public double getLHPHeading() {
        return -navX.getAngle();
    }

    public double getRHPHeading() {
        return navX.getAngle();
    }

    public void resetGyro() {
        navX.reset();
    }

    public double getAngularVelocity() {
        return navX.getRate(); // May have to make this negative
    }

    /*
     * Methods for getting joystick values
     */
    public double getThrottleValue() {
        // Controllers y-axes are natively up-negative, down-positive. returns negative
        return -deadbandX(xboxcontroller.getY(Hand.kLeft), Constants.kJoystickDeadband);
    }

    public double getTurnValue() {
        return deadbandX(xboxcontroller.getX(Hand.kRight), Constants.kJoystickDeadband);
    }

    public double getLeftTrigger() {
        return deadbandX(xboxcontroller.getTriggerAxis(Hand.kLeft), Constants.kTriggerDeadband);
    }

    public double getRightTrigger() {
        return deadbandX(xboxcontroller.getTriggerAxis(Hand.kRight), Constants.kTriggerDeadband);
    }

    /*
     * Methods for setting limelight values
     */
    public static enum LEDMode{
        PIPELINE(0), OFF(1), BLINK(2), ON(3);

        public int val;

        private LEDMode(int val){
            this.val = val;
        }
    }

    public static enum CamMode{
        VISION(0), DRIVER(1);

        public int val;

        private CamMode(int val){
            this.val = val;
        }
    }

    public static enum StreamMode{
        SIDE_BY_SIDE(0), PIP_MAIN(1), PIP_SECONDARY(2);

        public int val;
        
        private StreamMode(int val){
            this.val = val;
        }
    }

    public void setLastValidXOffset(double val) {
        last_valid_x_offset = val;
    }

    public void setLEDMode(LEDMode ledMode){
        limelight.getEntry("ledMode").setNumber(ledMode.val);
    }

    public void setCamMode(CamMode camMode){
        limelight.getEntry("camMode").setNumber(camMode.val);
    }

    public void setStreamMode(StreamMode stream){
        limelight.getEntry("stream").setNumber(stream.val);
    }

    /*
     * Methods for getting limelight values 
     */

    public double getxOffset() {
        SmartDashboard.putNumber("xoffset", -limelight.getEntry("tx").getDouble(0.0));
        return -limelight.getEntry("tx").getDouble(0);
    }

    public double getLastValidXOffset() {
        return last_valid_x_offset;
    }

    public double getyOffset() {
        return -limelight.getEntry("ty").getDouble(0.0);
    }


    public boolean getTargetValid() {
        return limelight.getEntry("tv").getDouble(0) == 1;
    }

    /*
     * Methods for driving
     */
    public boolean isQuickturn() {

        boolean leftActive = getLeftTrigger() != 0;
        boolean rightActive = getRightTrigger() != 0;

        return leftActive || rightActive;

    }

    public static double deadbandX(double input, double deadband) {
        if (Math.abs(input) <= deadband) {
            return 0;
        } else if (Math.abs(input) == 1) {
            return input;
        } else {
            return (1 / (1 - deadband) * (input + Math.signum(-input) * deadband));
        }
    }

    public static double exponentiate(double input, double power) {
        return Math.copySign(Math.abs(Math.pow(input, power)), input);
    }

    public static double deadbandY(double input, double deadband) {
        if (Math.abs(input) == 0.0) {
            return 0;
        } else if (Math.abs(input) == 1) {
            return input;
        } else {
            return input * (1.0 - deadband) + Math.signum(input) * deadband;
        }
    }

    public static double RPMtoTicksPerDecisecond(int RPM) {
        return RPM * (1024 / 60) / 10;
    }

    public static double ticksPerDSToFeetPerS(int ticks) {
        return (ticks * 40 * Math.PI) / (12 * 1024);
    }

    public static double feetPerSToTicksPerDS(double ftps) {
        return (ftps * 12 * 1024) / (40 * Math.PI);
    }
}