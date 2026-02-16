package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TurretConstants;

public class Shooter extends SubsystemBase {
    private final SparkMax ShooterLeader;
    private final SparkMax ShooterFollower;
    private final SparkClosedLoopController ShooterPID;

    private SparkMaxConfig configShooterLeader;
    private SparkMaxConfig configShooterFollower;
    public Shooter(){
        // Initialize motors and set device IDs
        configShooterLeader = new SparkMaxConfig();
        configShooterFollower = new SparkMaxConfig();
        
        configShooterFollower.follow(ShooterConstants.ShooterLeaderCANID, true);
    /**
     * Makes the ShooterFollower actually follow the ShooterLeader
     */
        configShooterLeader.idleMode(IdleMode.kCoast);
        configShooterLeader.closedLoop.pid(0.00001, 0.00, 0.00);

        configShooterFollower.idleMode(IdleMode.kCoast);
    /**
     * Lets the robot shooter come to a sliding halt
     */
        configShooterLeader.smartCurrentLimit(80);
        configShooterFollower.smartCurrentLimit(80);
    /**
     * A safety limiter to stop the shooter from taking up too many amps, stops the robot from catching on fire
     */
        ShooterLeader = new SparkMax(ShooterConstants.ShooterLeaderCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        ShooterFollower = new SparkMax(ShooterConstants.ShooterFollowerCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
    /**
     * Define ShooterLeader
     * Define ShooterFollower
     */
        ShooterLeader.configure(configShooterLeader, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        ShooterFollower.configure(configShooterFollower, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        ShooterPID = ShooterLeader.getClosedLoopController();
    /**
     * Configure ShooterLeader
     * Configure ShooterFollower
     */
    }



    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    /**
     * Sets the speed of the shooter motors.
     * @param speed The speed (as a percentage of max voltage, -1.0 to 1.0)
     */
    private void setSpeed(double speed) {
        ShooterLeader.set(speed);
        
    }
    
    private void setSpeedRPM(double speed) {
        ShooterPID.setSetpoint(speed,ControlType.kVelocity);

    }
    
    /**
     * Stops the shooter motors.
     */
    private void stop() {
        ShooterLeader.set(0);
    }


    public Command defaultShoot() {
        return (this.startEnd(
            () -> this.setSpeed(1),
            () -> this.stop()
        ));
    }

    public Command shootCommand(DoubleSupplier speed){
        return this.run(() -> this.setSpeed(speed.getAsDouble()));
    }
}


