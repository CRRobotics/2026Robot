package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.TurretConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
    /**
     * Importing code for the robot
     */

public class Turret extends SubsystemBase {

    private final SparkMax turretMotor;
    private final SparkClosedLoopController turretPID;
    private SparkMaxConfig turretConfig;
    private RelativeEncoder turretEncoder;
    private DigitalInput turretZero;
    /**
     * Initiates motor
     * Helps connect motor to turret
     */

    public Turret() {


        turretMotor = new SparkMax(TurretConstants.turretID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

        turretEncoder = turretMotor.getEncoder();
        turretConfig = new SparkMaxConfig();

        turretZero = new DigitalInput(TurretConstants.limitID);
    /**
     * Defines turretEncoder, turretMotor, turretConfig, and turretZero
     */
        turretConfig.closedLoop.p(TurretConstants.p);
        turretConfig.closedLoop.i(TurretConstants.i);
        turretConfig.closedLoop.d(TurretConstants.d);
        turretConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder); 
    /**
     * Configures the pid as well as the feedbackSensor
     */
        turretConfig.idleMode(IdleMode.kBrake);
    /**
     * Configures how the turret comes to a stop
     * Engages full break rather than letting it coast
     */
        turretMotor.configure(turretConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    /**
     * Configures the motor of the turret
     */
        turretPID = turretMotor.getClosedLoopController();
     /**
     * Defines the PID
     */       
    }

    private void turn(){
        turretMotor.set(0.1);
    }

    private void reverseTurn(){
        turretMotor.set(-0.1);
    }

    /**
     * Turns the turret to a given angle
     * @param angle the angle to turn to
     */
    private void turnTo (double angle) {
        turretPID.setSetpoint(angle/360 * TurretConstants.turretGearRatio,ControlType.kPosition);
    }
    /**
     * When the robot starts, the turrest gear ratio will probably be 1-1
     */
    public double getAngle(){
        return turretEncoder.getPosition() / TurretConstants.turretGearRatio;
    }
    /**
     * This lets the robot get the angular position of the turret
     */
    private void stop(){
        turretMotor.set(0);
    }
    /**
     * This sets the speed of the turret to 0 when the stop is initiated
     */
    private void resetEncoder(){
        turretEncoder.setPosition(0);
    }
    /**
     * This resets the position the robot thinks its at to 0 when the resetEncoder is initiated
     */
    private boolean zeroTrigger(){
        return turretZero.get();
    }

    public Trigger atZero = new Trigger(this::zeroTrigger);
    /**
     * This sets up the triggers for the turret. 
     */
    public Command turnCommand(){
        return this.startEnd(
            () -> this.turn(),
            () -> this.stop()
        );
    }
    public Command reverseTurnCommand(){
        return this.startEnd(
            () -> this.reverseTurn(),
            () -> this.stop()
        );
    }

    /**
     * Supplies a command that resets the encoders for turret
     * @return the command that resets the encoders for turret
     */
    public Command zeroCommand(){
        return this.runOnce(() -> this.resetEncoder());
    }

}

