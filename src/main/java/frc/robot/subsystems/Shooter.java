package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final SparkMax shooterLeader;
    //private final SparkMax shooterFollower;
    private final SparkClosedLoopController shooterPID;
    private final RelativeEncoder shooterEncoder;

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
        configShooterLeader.closedLoop.pid(0.001, 0.00, 0.00).feedForward.kV(0.01);


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
    }



    @Override
    public void periodic() {
        System.out.println(this.getSpeed());
        SmartDashboard.putNumber("ShooterSpeed", this.getSpeed());
    }

    /**
     * Sets the speed of the shooter motors.
     * @param speed The speed (as a percentage of max voltage, -1.0 to 1.0)
     */
    private void setSpeed(double speed) {
        shooterLeader.set(speed);
        
    }

    /**
     * Gets the velocity of encoder in RPM
     * @return velocity of encoder in RPM
     */
    private double getSpeed(){
        return shooterEncoder.getVelocity();
    }
    
    private void setSpeedRPM(double speed) {
        shooterPID.setSetpoint(speed,ControlType.kVelocity);

    }
    
    /**
     * Stops the shooter motors.
     */
    private void stop() {
        shooterLeader.set(0);
    }

    public Command defaultShoot() {
        return (this.startEnd(
            () -> this.setSpeed(0.75),
            () -> this.stop()
        ));
    }

    public Command shootCommand(DoubleSupplier speed){
        return this.startEnd(
            () -> this.setSpeedRPM(speed.getAsDouble()),
            () -> this.stop()
            );
    }
}


