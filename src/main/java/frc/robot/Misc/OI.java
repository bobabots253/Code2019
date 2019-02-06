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

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    private double kTriggerDeadband = 0.05;
    private double kStickDeadband = 0.05;

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

    }

    /*
     * Methods for getting joystick values
     */
    public double getThrottleValue() {
        // Controllers y-axes are natively up-negative, down-positive. returns negative
        return -deadbandX(xboxcontroller.getY(Hand.kLeft), kStickDeadband);
    }

    public double getTurnValue() {
        return deadbandX(xboxcontroller.getX(Hand.kRight), kStickDeadband);
    }

    public double getLeftTrigger() {
        return deadbandX(xboxcontroller.getTriggerAxis(Hand.kLeft), kTriggerDeadband);
    }

    public double getRightTrigger() {
        return deadbandX(xboxcontroller.getTriggerAxis(Hand.kRight), kTriggerDeadband);
    }

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
        return Math.copySign(Math.pow(input, power), input);
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

    public static double RPMtoTicksPerDecisecond(int RPM){
        return RPM * (1024/60)/10;
    }
}