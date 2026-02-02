package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.TurretConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    private final SparkMax turretMotor;
    private final SparkClosedLoopController turretPID;
    private SparkMaxConfig turretConfig;
    private AbsoluteEncoder turretEncoder;

        public Turret() {


            turretMotor = new SparkMax(TurretConstants.turretID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            
            turretEncoder = turretMotor.getAbsoluteEncoder();
            turretConfig = new SparkMaxConfig();


            turretConfig.closedLoop.p(TurretConstants.p);
            turretConfig.closedLoop.p(TurretConstants.i);
            turretConfig.closedLoop.p(TurretConstants.d);
            turretConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 
            
            turretMotor.configure(turretConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            turretPID = turretMotor.getClosedLoopController();
            
        }

        public void turnTo (double angle) {
            turretPID.setSetpoint(angle/360,ControlType.kPosition);
        }

        public double getAngle(){
            return turretEncoder.getPosition();
        }



}

