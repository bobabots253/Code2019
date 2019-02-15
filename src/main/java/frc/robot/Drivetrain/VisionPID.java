package frc.robot.Drivetrain;

public class VisionPID {

    private double kP, kI, kD, dt;
    private double last_error = 0;
    private double counter = 0;

    public VisionPID(double kP, double kI, double kD, double dt) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.dt = dt;
    }

    public void configure(double initial_error) {
        this.last_error = initial_error;
    }

    public double calculate(double error) {

        double ret;

        if (counter == 0) {
            ret = kP * error + kD * (error - error/2) / dt;
        } else {
            ret = kP * error + kD * (error - last_error) / dt;
            this.last_error = error;

        }
        counter++;
        return ret;
    }
}