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

import frc.robot.Constants.AquisitionsConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Aquisitions extends SubsystemBase {

    // only 1 spin
    private final SparkMax spinMotor;

    private final SparkMax armMotorLeft;
    private final SparkMax armMotorRight;

    private final SparkClosedLoopController armPIDLeft;

    private final AbsoluteEncoder armEncoder;
    private SparkMaxConfig armConfigLeft;
    private SparkMaxConfig armConfigRight;
    private SparkMaxConfig spinConfig;


        public Aquisitions () {
            armConfigLeft = new SparkMaxConfig();
            armConfigRight = new SparkMaxConfig();
            spinConfig = new SparkMaxConfig();

            spinConfig.idleMode(IdleMode.kCoast);
            armConfigLeft.idleMode(IdleMode.kBrake);
            armConfigRight.idleMode(IdleMode.kBrake);
            
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(3,true); //follows armMotorLeft
            
            spinMotor = new SparkMax(AquisitionsConstants.aqSpinID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorLeft = new SparkMax(AquisitionsConstants.aqArmLeftID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorRight = new SparkMax(AquisitionsConstants.aqArmRightID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            

            armEncoder = armMotorRight.getAbsoluteEncoder();

            


            armConfigLeft.closedLoop.p(AquisitionsConstants.p);
            armConfigLeft.closedLoop.p(AquisitionsConstants.i);
            armConfigLeft.closedLoop.p(AquisitionsConstants.d);
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(AquisitionsConstants.aqArmLeftID,true); //follows armMotorLeft

            armMotorLeft.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            armMotorRight.configure(armConfigRight, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            spinMotor.configure(spinConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            armPIDLeft = armMotorLeft.getClosedLoopController();
        }

        public void stop(){
            spinMotor.set(0);
        }

        public void intake() {
            spinMotor.set(-AquisitionsConstants.aqIntakeSpeed);
        }

         public void eject() {
            spinMotor.set(AquisitionsConstants.aqIntakeSpeed);
        }
        // #todo angle has intended units of degrees, unsure what kind of units position control uses
        public void armTurnTo (double angle) {
            armPIDLeft.setSetpoint(angle/360,ControlType.kPosition);

        }

        public double getSpeed(){
            return AquisitionsConstants.aqIntakeSpeed;
        }

        public double getArmAngle(){
            return armEncoder.getPosition() * 360;
        }



}
