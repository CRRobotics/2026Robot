package frc.robot.subsystems;

import static edu.wpi.first.units.Units.*;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutAngularVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.ShooterConstants;
import frc.robot.util.ShooterCalculations.ValueFromDistance;

public class Shooter extends SubsystemBase {
    private final SparkMax shooterLeader;
    //private final SparkMax shooterFollower;
    private final SparkClosedLoopController shooterPID;
    private final RelativeEncoder shooterEncoder;
    private final SysIdRoutine m_sysIdRoutine;
    private final MutVoltage m_appliedVoltage;
    private final MutAngle m_angle;
    private final MutAngularVelocity m_velocity;
    private double target;


    private SparkMaxConfig configShooterLeader;
    //private SparkMaxConfig configShooterFollower;
    public Shooter(){
        // Initialize motors and set device IDs
        configShooterLeader = new SparkMaxConfig();
        //configShooterFollower = new SparkMaxConfig();
        
        //configShooterFollower.follow(ShooterConstants.ShooterLeaderCANID, true);

    /**
     * Makes the ShooterFollower actually follow the ShooterLeader
     */
        configShooterLeader.idleMode(IdleMode.kCoast);
        configShooterLeader.closedLoop.pid(ShooterConstants.ShooterP, ShooterConstants.ShooterI, ShooterConstants.ShooterD).feedForward.kV(ShooterConstants.ShooterFFW);
        configShooterLeader.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);


        //configShooterFollower.idleMode(IdleMode.kCoast);

    /**
     * Lets the robot shooter come to a sliding halt
     */
        configShooterLeader.smartCurrentLimit(80);
        //configShooterFollower.smartCurrentLimit(80);

    /**
     * A safety limiter to stop the shooter from taking up too many amps, stops the robot from catching on fire
     */
        shooterLeader = new SparkMax(ShooterConstants.ShooterLeaderCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        //shooterFollower = new SparkMax(ShooterConstants.ShooterFollowerCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

    /**
     * Define ShooterLeader
     * Define ShooterFollower
     */
        shooterLeader.configure(configShooterLeader, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        //shooterFollower.configure(configShooterFollower, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        shooterPID = shooterLeader.getClosedLoopController();
        shooterEncoder = shooterLeader.getEncoder();

    /**
     * Configure ShooterLeader
     * Configure ShooterFollower
     */

     // Mutable holder for unit-safe voltage values, persisted to avoid reallocation.
        m_appliedVoltage = Volts.mutable(0);
        // Mutable holder for unit-safe linear distance values, persisted to avoid reallocation.
        m_angle = Radians.mutable(0);
        // Mutable holder for unit-safe linear velocity values, persisted to avoid reallocation.
        m_velocity = RadiansPerSecond.mutable(0);

        // Create a new SysId routine for characterizing the shooter.
        m_sysIdRoutine =
            new SysIdRoutine(
                // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
                new SysIdRoutine.Config(),
                new SysIdRoutine.Mechanism(
                    // Tell SysId how to plumb the driving voltage to the motor(s).
                    shooterLeader::setVoltage,
                    // Tell SysId how to record a frame of data for each motor on the mechanism being
                    // characterized.
                    log -> {
                        // Record a frame for the shooter motor.
                        log.motor("shooter-wheel")
                            .voltage(
                                m_appliedVoltage.mut_replace(
                                    shooterLeader.get() * RobotController.getBatteryVoltage(), Volts))
                            .angularPosition(m_angle.mut_replace(((Encoder) shooterEncoder).getDistance(), Rotations))
                            .angularVelocity(
                                m_velocity.mut_replace(((Encoder) shooterEncoder).getRate(), RotationsPerSecond));
                    },
                    // Tell SysId to make generated commands require this subsystem, suffix test state in
                    // WPILog with this subsystem's name ("shooter")
                    this));
        target = 0.0;
    }

    @Override
    public void periodic() {
        //System.out.println(this.getSpeed());
        SmartDashboard.putNumber("ShooterSpeed", this.getSpeed());
        Logger.recordOutput("Shooter/ActualSpeed", this.getSpeed());
        //System.out.println((ValueFromDistance.getAngleSpeedTime(SmartDashboard.getNumber("Distance to Hub", 1), ValueFromDistance.shootMap0)).getAngle() + "_____" + (ValueFromDistance.getAngleSpeedTime(SmartDashboard.getNumber("Distance to Hub", 1), ValueFromDistance.shootMap0)).getSpeed());
    }

    /**
     * Sets the speed of the shooter motors.
     * @param speed The speed (as a percentage of max voltage, -1.0 to 1.0)
     */
    private void setSpeed(double speed) {
        shooterLeader.set(speed);
        target = speed;
        Logger.recordOutput("Shooter/SetSpeedPct", this.getSpeed());
    }

    /**
     * Gets the velocity of encoder in RPM
     * @return velocity of encoder in RPM
     */
    private double getSpeed(){
        return shooterEncoder.getVelocity();
    }

    /**
     * @param speed speed of motor
     * sets speed of motor
     */
    
    private void setSpeedRPM(double speed) {
        shooterPID.setSetpoint(speed,ControlType.kVelocity);
        Logger.recordOutput("Shooter/SetSpeedRPM", speed);
    }
    
    /**
     * @return is shooter at specified speed?
     */

    public boolean atSpeed(){
        return Math.abs(this.getSpeed() - target) > ShooterConstants.ShooterTolerance;
    }
    
    /**
     * trigger for shooter being done
     */

    public Trigger shooterDone = new Trigger(() -> this.atSpeed());

    /**
     * Stops the shooter motors.
     */
    private void stop() {
        shooterLeader.set(0);
    }

    /**
     * @return creates command that starts shooting at speed
     */

    public Command defaultShoot() {
        return (this.startEnd(
            () -> this.setSpeed(0.75),
            () -> this.stop()
        ));
    }

    /**
     * @param speed supplier that gives speed in double
     * @return runs shoot systems
     */

    public Command shootCommand(DoubleSupplier speed){
        return this.startEnd(
            () -> this.setSpeedRPM(speed.getAsDouble()),
            () -> this.stop()
            );
    }

    /**
     * @return creates command that ensures shooter is finished
     */

    public Command shootFinished(){
        return this.runOnce(() -> this.stop());
    }
    public Command shootAsDoubleCommand(double speed){
        return this.startEnd(
            () -> this.setSpeedRPM(speed),
            () -> this.stop()
            );
    }

    /**
     * Returns a command that will execute a quasistatic test in the given direction.
     *
     * @param direction The direction (forward or reverse) to run the test in
     */
    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_sysIdRoutine.quasistatic(direction);
    }

    /**
     * Returns a command that will execute a dynamic test in the given direction.
     *
     * @param direction The direction (forward or reverse) to run the test in
     */
    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_sysIdRoutine.dynamic(direction);
    }
}


