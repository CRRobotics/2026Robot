package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final SparkMax ShooterLeader;
    private final SparkMax ShooterFollower;

    private SparkMaxConfig configShooterLeader;
    private SparkMaxConfig configShooterFollower;
    public Shooter(){
        // Initialize motors and set device IDs
        configShooterLeader = new SparkMaxConfig();
        configShooterFollower = new SparkMaxConfig();
        
        configShooterFollower.follow(ShooterConstants.ShooterLeaderCANID);
        configShooterLeader.idleMode(IdleMode.kCoast);
        configShooterFollower.idleMode(IdleMode.kCoast);
        configShooterLeader.smartCurrentLimit(80);
        configShooterFollower.smartCurrentLimit(80);

        ShooterLeader = new SparkMax(ShooterConstants.ShooterFollowerCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        ShooterFollower = new SparkMax(ShooterConstants.ShooterFollowerCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

        ShooterLeader.configure(configShooterLeader, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        ShooterFollower.configure(configShooterFollower, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    }



    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    /**
     * Sets the speed of the shooter motors.
     * @param speed The speed (as a percentage of max voltage, -1.0 to 1.0)
     */
    public void setSpeed(double speed) {
        ShooterLeader.set(speed);
        
    }
     
    /**
     * Stops the shooter motors.
     */
    public void stop() {
        ShooterLeader.set(0);
    }


    public Command defaultShoot() {
        return (this.run(() -> this.setSpeed(0.1)));
    }

    public Command stopShoot() {
        return (this.run(() -> this.stop()));
    }
}


