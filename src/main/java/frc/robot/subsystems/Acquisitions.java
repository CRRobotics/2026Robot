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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
    private Boolean armExtended;
    /**
     * Config for acquisition mototrs
     */

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
        

        spinMotor = new SparkMax(AcquisitionsConstants.aqSpinID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        armMotorLeft = new SparkMax(AcquisitionsConstants.aqArmLeftID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        armMotorRight = new SparkMax(AcquisitionsConstants.aqArmRightID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        
        armExtended = false;
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

    /**
     *Sets the acquisition motor speed to 0
        */
    private void stop(){
        spinPID.setSetpoint(0, ControlType.kVelocity);
    }

    /**
     * Sets motor speed to the opposite of eject speed defined in constants in order to intake fuel
     */

    private void intake() {
        spinPID.setSetpoint(-AcquisitionsConstants.aqIntakeSpeed, ControlType.kVelocity);
    }

    /**
     * Sets motor speed to the eject speed defined in constants in order to eject fuel
     */
    private void eject() {
        spinPID.setSetpoint(AcquisitionsConstants.aqIntakeSpeed, ControlType.kVelocity);
    }

    private void armDown(){
        armMotorLeft.set(0.1);
    }

    private void armUp(){
        armMotorLeft.set(-0.1);
    }

    private void armStop(){
        armMotorLeft.set(0);
    }

    // #todo angle has intended units of degrees, unsure what kind of units position control uses
    /**
     * Sets the arm to turn to a certain angle (defined by angle)
     * @param angle
     */
    private void armTurnTo (double angle) {
        armPIDLeft.setSetpoint(angle/360 * AcquisitionsConstants.armGearRatio,ControlType.kPosition);
    }

    /**
     * Returns the speed of the intake motors, which is equal to the aqIntakeSpeed set in constants
     * NOTE: the aqIntakeSpeed is set to 0 at the time of writing!
     * @return
     */
    public double getSpeed(){
        return AcquisitionsConstants.aqIntakeSpeed;
    }

    /**
     * Returns the angle of the arm from the encoder
     * @return
     */
    public double getArmAngle(){
        return armEncoder.getPosition() * 360;
    }

    /**
     * Runs the eject command to cause the acquisition to eject fuel
     * @return
     */
    public Command AcquisitionEjectCommand(){
        return this.startEnd(
            () -> this.eject(),
            () -> this.stop());
    }

    /**
     * The following few lines are used for defining commands
     */
    
    private void armMove(){
        armExtended = !armExtended;
    }
    
    public boolean armExtended(){
        return armExtended;
    }

    public Trigger aqDown = new Trigger(this::armExtended);

    /**
     * Flip arm down
     * @return command that flips aq arm down
     */
    public Command ChangeAqState(){
        return this.runOnce(() -> this.armMove());
    }

    public Command AcquisitionIntakeCommand(){
        return this.startEnd(
            () -> this.intake(),
            () -> this.stop());
    }

    public Command ExtendAcquisitionArmCommand(){
        //return this.runOnce(() -> this.armTurnTo(AcquisitionsConstants.armMotorDownAngle));
        return this.startEnd(
            () -> this.armDown(),
            () -> this.armStop());
    }

    public Command RetractAcquisitionArmCommand(){
        //return this.runOnce(() -> this.armTurnTo(AcquisitionsConstants.armMotorUpAngle));
        return this.startEnd(
            () -> this.armUp(),
            () -> this.armStop());
    }
}
