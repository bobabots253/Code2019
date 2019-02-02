package frc.robot;
import edu.wpi.first.wpilibj.Servo;

public class useServos implements Runnable {
    private Servo servo;

    public useServos() {
        servo = new Servo(1);
    }
    
    public useServos(int channel) {
        servo = new Servo(channel);
    }

    public void run() {
        servo.setAngle(180.0);
        servo.setAngle(0);
    }
}
