package frc.robot.subsystems;
import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.HoodConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Hood extends SubsystemBase {

    private final SparkMax hoodMotor;
    private final SparkClosedLoopController hoodPID;
    private SparkMaxConfig hoodConfig;
    private RelativeEncoder hoodEncoder;
    private DigitalInput hoodZero;
    private double target;

    public Hood() {

        hoodMotor = new SparkMax(HoodConstants.hoodID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        hoodEncoder = hoodMotor.getEncoder();
        hoodConfig = new SparkMaxConfig();
        hoodZero = new DigitalInput(HoodConstants.zeroID);

    /**
     * Defines hoodEncoder, hoodMotor, hoodConfig, and hoodZero
     */
        hoodConfig.closedLoop.p(HoodConstants.p).maxOutput(0.3).minOutput(-0.1);
        hoodConfig.closedLoop.i(HoodConstants.i);
        hoodConfig.closedLoop.d(HoodConstants.d);
        hoodConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder); 
    /**
     * Configures the pid as well as the feedbackSensor
     */
        hoodConfig.idleMode(IdleMode.kBrake);
    /**
     * Configures how the hood comes to a stop
     * Engages full break rather than letting it coast
     */
        hoodMotor.configure(hoodConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    /**
     * Configures the motor of the hood
     */
        hoodPID = hoodMotor.getClosedLoopController();
     /**
     * Defines the PID
     */      
        target = 0;
    }

    /**
     * starts turning hood at speed 0.1
     */

    private void turn(){
        hoodMotor.set(0.2);
    }

    /**
     * starts turning hood opposite direction at speed 0.1
     */

    private void reverseTurn(){
        hoodMotor.set(-0.1);
    }

    /**
     * Turns the hood to a given angle
     * @param angle the angle to turn to
     */
    public void turnTo (double angle) {
        System.out.println("Running Hood");
        hoodPID.setSetpoint(angle / 360 * HoodConstants.hoodGearRatio,ControlType.kPosition);
        target = angle;
    }

    /**
     * When the robot starts, the ____ gear ratio will probably be 1-1
     */
    public double getAngle(){
        return hoodEncoder.getPosition() * 360 / HoodConstants.hoodGearRatio;
    }

    /**
     * This lets the robot get the angular position of the hood
     */
    private void stop(){
        hoodMotor.set(0);
    }

    /**
     * sets current hood position to zero
     */

    private void zeroHood(){
        hoodEncoder.setPosition(0);
        System.out.println("Zeroed Hood");
    }

    private void underTrench(){
         hoodPID.setSetpoint(0, ControlType.kPosition);

    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("HoodAngle", this.getAngle());
    }

    /**
     * This resets the position the robot thinks its at to 0 when the resetEncoder is initiated
     */
    private boolean atLimit(){
        return hoodZero.get();
    }

    /**
     * creates trigger for zero position
     */

    public Trigger atZero = new Trigger(this::atLimit);

    /**
     * @return creates command that zeroes hood
     */

    public Command zeroHoodCommand(){
        return runOnce(() -> this.zeroHood());
    }

    /**
     * This sets up the triggers for the hood. 
     */
    private boolean atAngle(){
        return Math.abs(this.getAngle() - target) < HoodConstants.hoodTolerance;
    }

    /**
     * creates trigger for hood at done angle
     */

    public Trigger hoodDone = new Trigger(() -> this.atAngle());

    /**
     * @return creates command that starts turning hood
     */

    public Command turnCommand(){
        return this.startEnd(() -> this.turn(),
        () -> this.stop());
    }

    /**
     * @return creates command that starts turning hood reverse
     */

    public Command reverseTurnCommand(){
        return this.startEnd(
            () -> this.reverseTurn(),
            () -> this.stop());
    }

    public Command underTrenchCommand(){
        return this.runOnce(() -> this.underTrench());
    }

    /**
     * @param angle supplies angle of desired angle
     * @return creates command that turns to desired angle
     */

    public Command turnToCommand(DoubleSupplier angle){
        return this.runOnce(() -> this.turnTo(angle.getAsDouble()));
    }
}

