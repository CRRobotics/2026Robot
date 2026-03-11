package frc.robot.util.ShooterCalculations;

/**
 * Wrapper class. I still don't know why this is necessary, but VScode gives me errors if I don't add it, so whatever.
 */
public class AngleSpeedTime {
    private double angle;
    private double speed;
    private double time;
    
    public AngleSpeedTime(double angle, double speed, double time) {
        this.angle = angle;
        this.speed = speed;
        this.time = time;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }
}