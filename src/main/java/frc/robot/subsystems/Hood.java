package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Value;

import java.util.function.DoubleSupplier;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import frc.robot.ShooterCalculations.AngleSpeedTime;
import frc.robot.ShooterCalculations.ValueFromDistance;
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
    private AbsoluteEncoder hoodEncoder;
    private DigitalInput hoodZero;
    private double target;

    public Hood() {


        hoodMotor = new SparkMax(HoodConstants.hoodID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

        hoodEncoder = hoodMotor.getAbsoluteEncoder();
        hoodConfig = new SparkMaxConfig();

    /**
     * Defines hoodEncoder, hoodMotor, hoodConfig, and hoodZero
     */
        hoodConfig.closedLoop.p(HoodConstants.p);
        hoodConfig.closedLoop.i(HoodConstants.i);
        hoodConfig.closedLoop.d(HoodConstants.d);
        hoodConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder); 
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

    private void turn(){
        hoodMotor.set(0.1);
    }

    private void reverseTurn(){
        hoodMotor.set(-0.1);
    }

    /**
     * Turns the hood to a given angle
     * @param angle the angle to turn to
     */
    public void turnTo (double angle) {
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

    @Override
    public void periodic(){
        SmartDashboard.putNumber("HoodAngle", this.getAngle());
    }

    /**
     * This resets the position the robot thinks its at to 0 when the resetEncoder is initiated
     */
    private boolean zeroTrigger(){
        return hoodZero.get();
    }

    public Trigger atZero = new Trigger(this::zeroTrigger);

    /**
     * This sets up the triggers for the hood. 
     */
    private boolean atAngle(){
        return Math.abs(this.getAngle() - target) < HoodConstants.hoodTolerance;
    }

    public Trigger hoodDone = new Trigger(() -> this.atAngle());

    public Command turnCommand(){
        return this.startEnd(() -> this.turn(),
        () -> this.stop());
    }

    public Command reverseTurnCommand(){
        return this.startEnd(
            () -> this.reverseTurn(),
            () -> this.stop());
    }

    public Command turnToCommand(DoubleSupplier angle){
        return this.runOnce(() -> this.turnTo(angle.getAsDouble()));
    }
}

