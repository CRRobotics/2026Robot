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

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Aquisitions extends SubsystemBase {

    private final SparkMax spinMotorLeft;
    private final SparkMax spinMotorRight;

    private final SparkMax armMotorLeft;
    private final SparkMax armMotorRight;

    private double intakeSpeed;

    private final SparkClosedLoopController armPIDLeft;

    private final AbsoluteEncoder armEncoder;
    private SparkMaxConfig armConfigLeft;
    private SparkMaxConfig armConfigRight;
    private SparkMaxConfig spinConfigLeft;
    private SparkMaxConfig spinConfigRight;


    private double aP;
    private double aI;
    private double aD;

        public Aquisitions () {

            armConfigLeft = new SparkMaxConfig();
            armConfigRight = new SparkMaxConfig();
            spinConfigLeft = new SparkMaxConfig();
            spinConfigRight = new SparkMaxConfig();

            spinConfigRight.idleMode(IdleMode.kCoast);
            spinConfigLeft.idleMode(IdleMode.kCoast);
            armConfigLeft.idleMode(IdleMode.kBrake);
            armConfigRight.idleMode(IdleMode.kBrake);
            
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(3,true); //follows armMotorLeft

            armConfigLeft.closedLoop.p(aP);
            armConfigLeft.closedLoop.p(aI);
            armConfigLeft.closedLoop.p(aD);
            
            spinMotorLeft = new SparkMax(1, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            spinMotorRight = new SparkMax(2, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorLeft = new SparkMax(3, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorRight = new SparkMax(4, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            

            armEncoder = armMotorRight.getAbsoluteEncoder();

            
            armMotorLeft.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            armMotorRight.configure(armConfigRight, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            spinMotorLeft.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            spinMotorRight.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            armPIDLeft = armMotorLeft.getClosedLoopController();
        }

        public void stop(){
            spinMotorLeft.set(0);
            spinMotorRight.set(0);
        }

        public void intake() {
            spinMotorRight.set(intakeSpeed);
            spinMotorLeft.set(-intakeSpeed);
        }

         public void eject() {
            spinMotorRight.set(-intakeSpeed);
            spinMotorLeft.set(intakeSpeed);
        }
        // #todo angle has intended units of degrees, unsure what kind of units position control uses
        public void armTurnTo (double angle) {
            armPIDLeft.setSetpoint(angle/360,ControlType.kPosition);

        }

        public double getSpeed(){
            return intakeSpeed;
        }

        public double getArmAngle(){
            return armEncoder.getPosition() * 360;
        }



}
