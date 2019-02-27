package frc.robot.Drivetrain;

public class GyroController {

    private double kP, kI, kD, dt;
    private double last_error = 0;
    private double total_error = 0;

    public GyroController(double kP, double kI, double kD, double dt) {
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
        total_error += error;

        ret = kP * error + kI * total_error + kD * (error - last_error) / dt;

        return ret;
    }
}