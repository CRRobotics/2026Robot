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
import frc.robot.util.LinearServo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Acquisitions extends SubsystemBase {

    // only 1 spin
    private final SparkMax spinMotor;
    //private final SparkMax armMotorRight;

    private final SparkClosedLoopController spinPID;
    private final LinearServo servo;

    private SparkMaxConfig spinConfig;
    private Boolean armExtended;
    /**
     * Config for acquisition mototrs
     */

    public Acquisitions () {
        SmartDashboard.putBoolean("released", false);
        spinConfig = new SparkMaxConfig();
        servo = new LinearServo(AcquisitionsConstants.servoChannel, AcquisitionsConstants.servoLength, AcquisitionsConstants.servoSpeed);

        spinConfig.idleMode(IdleMode.kCoast);
        spinConfig.smartCurrentLimit(105); //adjust this value for maximum current input--105 is ideal for NEO vortex
        

        spinMotor = new SparkMax(AcquisitionsConstants.aqSpinID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        
        armExtended = false;

        /**
         * MAKE SURE TO ADJUST THESE FEED FORWARD VALUES BEFORE RUNNING! THEY ARE CURRENTLY SET TO 1
         */
        spinConfig.closedLoop.pid(AcquisitionsConstants.spinP, AcquisitionsConstants.spinI, AcquisitionsConstants.spinD).feedForward.kV(AcquisitionsConstants.SpinFeedForward);



        //armMotorRight.configure(armConfigRight, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        spinMotor.configure(spinConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        spinPID = spinMotor.getClosedLoopController();
    }

    /**
     *Sets the acquisition motor speed to 0
        */
    private void stop(){
        //spinPID.setSetpoint(0, ControlType.kVelocity);
        spinMotor.set(0);
    }

    /**
     * Sets motor speed to the opposite of eject speed defined in constants in order to intake fuel
     */

    private void intake() {
        spinMotor.set(AcquisitionsConstants.IntakeSpeed);
        //spinPID.setSetpoint(-AcquisitionsConstants.aqIntakeSpeed, ControlType.kVelocity);
        //spinPID.setSetpoint(AcquisitionsConstants.IntakeSpeed,ControlType.kVelocity);
    }

    /**
     * Sets motor speed to the eject speed defined in constants in order to eject fuel
     */
    private void eject() {
        //spinPID.setSetpoint(AcquisitionsConstants.aqIntakeSpeed, ControlType.kVelocity);
        spinMotor.set(AcquisitionsConstants.EjectSpeed);
    }

    /**
     * Run once at the beginning of match
     */
    public void release(){
        SmartDashboard.putBoolean("released", true);
        servo.setPosition(AcquisitionsConstants.servoOpen);
    }

    /**
     * Should only be run in pit/before inside arena
     */
    public void store(){
        servo.setPosition(AcquisitionsConstants.servoClosed);
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

    /**
     * @return is arm extended?
     */
    
    public boolean armExtended(){
        return armExtended;
    }

    /**
     * trigger for aquisition being fully extended
     */

    public Trigger aqDown = new Trigger(this::armExtended);

    /**
     * Flip arm down
     * @return command that flips aq arm down
     */
    public Command ChangeAqState(){
        return this.runOnce(() -> this.armMove());
    }

    /**
     * @return creates command that runs acquisiton intake motors
     */

    public Command AcquisitionIntakeCommand(){
        return this.startEnd(
            () -> this.intake(),
            () -> this.stop());
    }

    /**
     * @return crewtes command that extends AQ arm
     */



    /**
     * Drops acquisition arm
     * @return
     */
    public Command Deploy(){
        return this.runOnce(
            () -> this.release()
        );
    }

    /**
     * re-inserts linear actuator
     * @return
     */
    public Command Prepare(){
        return this.runOnce(
            () -> this.store()
        );
    }

}
