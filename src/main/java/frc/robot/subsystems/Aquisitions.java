package frc.robot.subsystems;

import com.ctre.phoenix6.sim.TalonFXSimState.MotorType;
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

    private final AbsoluteEncoder aquisitionEncoder;
    private SparkMaxConfig aquisitionConfigLeft;
    private SparkMaxConfig aquisitionConfigRight;

    private double aP;
    private double aI;
    private double aD;

        public Aquisitions () {
            spinMotorLeft = new SparkMax(1, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            spinMotorRight = new SparkMax(2, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorLeft = new SparkMax(3, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            armMotorRight = new SparkMax(4, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            
            aquisitionEncoder = armMotorRight.getAbsoluteEncoder();
            aquisitionConfigLeft = new SparkMaxConfig();
            aquisitionConfigRight = new SparkMaxConfig();

            armMotorLeft.configure(aquisitionConfigLeft, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

            aquisitionConfigLeft.closedLoop.p(aP);
            aquisitionConfigLeft.closedLoop.p(aI);
            aquisitionConfigLeft.closedLoop.p(aD);
            aquisitionConfigLeft.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 

            aquisitionConfigRight.follow(0,true);

            armPIDLeft = armMotorLeft.getClosedLoopController();
        }



        public void intake() {
            spinMotorRight.set(intakeSpeed);
            spinMotorLeft.set(-intakeSpeed);
        }

        public void armTurnTo (double angle) {
            armPIDLeft.setSetpoint(angle/360,ControlType.kPosition);

            System.out.println(armMotorRight.getAppliedOutput());
            System.out.println(armMotorLeft.getAppliedOutput());

        }

        public double getSpeed(){
            return intakeSpeed;
        }

        public double getArmAngle(){
            return aquisitionEncoder.getPosition() * 360;
        }



}
