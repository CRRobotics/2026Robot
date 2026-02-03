package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.AcquisitionsConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Acquisitions extends SubsystemBase {

    // only 1 spin
    private final SparkMax spinMotor;
    private final SparkMax armMotorLeft;
    private final SparkMax armMotorRight;

    private final SparkClosedLoopController armPIDLeft;
    private final SparkClosedLoopController spinPID;

    private final AbsoluteEncoder armEncoder;
    private SparkMaxConfig armConfigLeft;
    private SparkMaxConfig armConfigRight;
    private SparkMaxConfig spinConfig;


        public Acquisitions () {
            armConfigLeft = new SparkMaxConfig();
            armConfigRight = new SparkMaxConfig();
            spinConfig = new SparkMaxConfig();

            spinConfig.idleMode(IdleMode.kCoast);
            armConfigLeft.idleMode(IdleMode.kBrake);
            armConfigRight.idleMode(IdleMode.kBrake);
            spinConfig.smartCurrentLimit(105); //adjust this value for maximum current input--105 is ideal for NEO vortex
            armConfigLeft.smartCurrentLimit(105);
            armConfigRight.smartCurrentLimit(105);
            
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(3,true); //follows armMotorLeft
            
            spinMotor = new SparkMax(AcquisitionsConstants.aqSpinID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorLeft = new SparkMax(AcquisitionsConstants.aqArmLeftID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorRight = new SparkMax(AcquisitionsConstants.aqArmRightID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            

            armEncoder = armMotorRight.getAbsoluteEncoder();

            spinConfig.closedLoop.p(AcquisitionsConstants.spinP);
            spinConfig.closedLoop.p(AcquisitionsConstants.spinI);
            spinConfig.closedLoop.p(AcquisitionsConstants.spinD);

            armConfigLeft.closedLoop.p(AcquisitionsConstants.armP);
            armConfigLeft.closedLoop.p(AcquisitionsConstants.armI);
            armConfigLeft.closedLoop.p(AcquisitionsConstants.armD);
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(AcquisitionsConstants.aqArmLeftID,true); //follows armMotorLeft

            armMotorLeft.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            armMotorRight.configure(armConfigRight, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            spinMotor.configure(spinConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            armPIDLeft = armMotorLeft.getClosedLoopController();
            spinPID = spinMotor.getClosedLoopController();
        }

        public void stop(){
            spinMotor.set(0);
        }

        public void intake() {
            spinMotor.set(-AcquisitionsConstants.aqIntakeSpeed);
        }

         public void eject() {
            spinMotor.set(AcquisitionsConstants.aqIntakeSpeed);
        }
        // #todo angle has intended units of degrees, unsure what kind of units position control uses
        public void armTurnTo (double angle) {
            armPIDLeft.setSetpoint(angle/360,ControlType.kPosition);

        }

        public double getSpeed(){
            return AcquisitionsConstants.aqIntakeSpeed;
        }

        public double getArmAngle(){
            return armEncoder.getPosition() * 360;
        }



}
