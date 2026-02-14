package frc.robot.ShooterCalculations;

/**
 * Wrapper class. I still don't know why this is necessary, but VScode gives me errors if I don't add it, so whatever.
 */
public class AngleSpeed {
    private double angle;
    private double speed;
    
    public AngleSpeed(double angle, double speed) {
        this.angle = angle;
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}