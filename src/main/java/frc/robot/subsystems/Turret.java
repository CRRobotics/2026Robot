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

public class Turret extends SubsystemBase {

    private final SparkMax turretMotor;
    private final SparkClosedLoopController turretPID;
    private SparkMaxConfig turretConfig;
    private RelativeEncoder turretEncoder;
    private DigitalInput turretZero;

    public Turret() {


        turretMotor = new SparkMax(TurretConstants.turretID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        
        turretEncoder = turretMotor.getEncoder();
        turretConfig = new SparkMaxConfig();

        turretZero = new DigitalInput(TurretConstants.limitID);

        turretConfig.closedLoop.p(TurretConstants.p);
        turretConfig.closedLoop.p(TurretConstants.i);
        turretConfig.closedLoop.p(TurretConstants.d);
        turretConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder); 
        turretConfig.idleMode(IdleMode.kBrake);

        turretMotor.configure(turretConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        turretPID = turretMotor.getClosedLoopController();
        
    }

    public void turn(){
        turretMotor.set(0.1);
    }

    public void turnTo (double angle) {
        turretPID.setSetpoint(angle/360 * TurretConstants.turretGearRatio,ControlType.kPosition);
    }

    public double getAngle(){
        return turretEncoder.getPosition() / TurretConstants.turretGearRatio;
    }

    public void stop(){
        turretMotor.set(0);
    }

    public void resetEncoder(){
        turretEncoder.setPosition(0);
    }

    private boolean zeroTrigger(){
        return turretZero.get();
    }

    public Trigger atZero = new Trigger(this::zeroTrigger);

    public Command turnCommand(){
        return this.run(() -> this.turn());
    }

    public Command zeroCommand(){
        return this.runOnce(() -> this.resetEncoder());
    }

}

