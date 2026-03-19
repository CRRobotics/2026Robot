package frc.robot.subsystems;
import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.HoodConstants;

public class Hood extends SubsystemBase {

    private final SparkMax hoodMotor;
    private final SparkClosedLoopController hoodPID;
    private SparkMaxConfig hoodConfig;
    private RelativeEncoder hoodEncoder;
    private double target;
    private static boolean safeForTrench;

    public Hood() {

        hoodMotor = new SparkMax(HoodConstants.hoodID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        hoodEncoder = hoodMotor.getEncoder();
        hoodConfig = new SparkMaxConfig();

    /**
     * Defines hoodEncoder, hoodMotor, hoodConfig, and hoodZero
     */
        hoodConfig.closedLoop.p(HoodConstants.p).maxOutput(0.3).minOutput(-0.2);
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
        if(!safeForTrench){
            hoodMotor.set(0.2);
        }

    }

    /**
     * starts turning hood opposite direction at speed 0.1
     */

    private void reverseTurn(){
        if(!safeForTrench){
            hoodMotor.set(-0.2);
        }
    }

    /**
     * Turns the hood to a given angle
     * @param angle the angle to turn to
     */
    public void turnTo (double angle) {
        if (!safeForTrench){
            SmartDashboard.putNumber("hood going to", angle);
            hoodPID.setSetpoint(angle / 360 * HoodConstants.hoodGearRatio,ControlType.kPosition);
            target = angle;

            Logger.recordOutput("Hood/TurnTo", angle);
        } else {
            System.out.println("Not safe for trench");
        }
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
        safeForTrench = true;
        hoodPID.setSetpoint(0, ControlType.kPosition);

    }

    private void notTrench(){
        safeForTrench = false;
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("HoodAngle", this.getAngle());
        Logger.recordOutput("Hood/AngleActual", this.getAngle());
        Logger.recordOutput("Hood/SafeForTrench", safeForTrench);
    }

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

    public Command notTrenchCommand(){
        return this.runOnce(() -> this.notTrench());
    }

    /**
     * @param angle supplies angle of desired angle
     * @return creates command that turns to desired angle
     */

    public Command turnToCommand(DoubleSupplier angle){
        return this.startEnd(
            () -> this.turnTo(angle.getAsDouble()),
            () -> this.turnTo(0)
        );
    }
}

