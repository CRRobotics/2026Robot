package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

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

    private double aP;
    private double aI;
    private double aD;

        public Aquisitions () {
            spinMotorLeft = new SparkMax(1, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            spinMotorRight = new SparkMax(2, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorLeft = new SparkMax(3, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorRight = new SparkMax(4, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            
            armEncoder = armMotorRight.getAbsoluteEncoder();
            armConfigLeft = new SparkMaxConfig();
            armConfigRight = new SparkMaxConfig();

            armMotorLeft.configure(armConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            armConfigLeft.closedLoop.p(aP);
            armConfigLeft.closedLoop.p(aI);
            armConfigLeft.closedLoop.p(aD);
            armConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            armConfigRight.follow(3,true); //follows armMotorLeft

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
