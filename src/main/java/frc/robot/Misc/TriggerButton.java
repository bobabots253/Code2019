package frc.robot.Misc;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TriggerButton extends JoystickButton{

    private XboxController joystick;
    private Hand hand;

    public TriggerButton(XboxController joystick, Hand hand) {
        super(joystick, -1);

        this.joystick = joystick;
        this.hand = hand;
        
    }

    public boolean get(){
        return joystick.getTriggerAxis(hand) > Constants.kTriggerDeadband;
    }

}