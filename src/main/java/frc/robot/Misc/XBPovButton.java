package frc.robot.Misc;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XBPovButton extends JoystickButton{
    private boolean toggle = true;

    private GenericHID joystickNumber;
    private int xboxPOV;

    public XBPovButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);

        joystickNumber = joystick;
        xboxPOV = buttonNumber;
    }

    public boolean get(){
        return joystickNumber.getPOV() == xboxPOV;
    }

    public boolean isPressed(){
        if(get() && toggle){
            toggle = false;
            return true;
        }else if(!get()){
            toggle = true;
        }
        return false;
    }

    public static final int
            NONE = -1,
            UP = 0,
            UP_RIGHT = 45,
            RIGHT = 90,
            DOWN_RIGHT = 115,
            DOWN = 180,
            DOWN_LEFT = 225,
            LEFT = 270,
            UP_LEFT = 315;
}