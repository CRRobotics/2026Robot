package frc.robot.subsystems;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.FieldConstants;
import frc.robot.Constants.TurretConstants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
    /**
     * Importing code for the robot
     */

public class Turret extends SubsystemBase {

    private final SparkMax turretMotor;
    private boolean IsRed;
    private final SparkClosedLoopController turretPID;
    private SparkMaxConfig turretConfig;
    private RelativeEncoder turretEncoder;
    //private DigitalInput turretZero;
    private double target;
    /**
     * Initiates motor
     * Helps connect motor to turret
     */

    public Turret() {


        turretMotor = new SparkMax(TurretConstants.turretLeaderID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

        turretEncoder = turretMotor.getEncoder();
        turretConfig = new SparkMaxConfig();

        //turretZero = new DigitalInput(TurretConstants.limitID);
    /**
     * Defines turretEncoder, turretMotor, turretConfig, and turretZero
     */
        turretConfig.closedLoop.p(TurretConstants.p);
        turretConfig.closedLoop.i(TurretConstants.i);
        turretConfig.closedLoop.d(TurretConstants.d);
        turretConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder); 
        CheckAlliance();
    /**
     * Configures the pid as well as the feedbackSensor
     */
        turretConfig.idleMode(IdleMode.kBrake);
    /**
     * Configures how the turret comes to a stop
     * Engages full break rather than letting it coast
     */
        turretMotor.configure(turretConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    /**
     * Configures the motor of the turret
     */
        turretPID = turretMotor.getClosedLoopController();
     /**
     * Defines the PID
     */       
        target = 0;
    }

    private void turn(){
        turretMotor.set(0.5);
    }

    private void reverseTurn(){
        turretMotor.set(-0.5);
    }

    private void CheckAlliance(){
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            if (alliance.get() == DriverStation.Alliance.Red){
                IsRed = true;
            }
            else {
                IsRed = false;
            }
        }
        else {
            IsRed= false;
        };
    }
    /**
     * Turns the turret to a given angle
     * @param angle the angle to turn to
     */
    private void turnTo (double angle) {
        turretPID.setSetpoint(angle/360 * TurretConstants.turretGearRatio,ControlType.kPosition);
        target=angle;
    }

    /**
     * When the robot starts, the turrest gear ratio will probably be 1-1
     */
    public double getAngle(){
        return turretEncoder.getPosition() * 360 / TurretConstants.turretGearRatio;
    }

    /**
     * This lets the robot get the angular position of the turret
     */
    private void stop(){
        turretMotor.set(0);
    }

    /**
     * This sets the speed of the turret to 0 when the stop is initiated
     */
    private void resetEncoder(){
        turretEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("TurretAngle", this.getAngle());
    }

    /**
     * This resets the position the robot thinks its at to 0 when the resetEncoder is initiated
     */
    private boolean zeroTrigger(){
        return false;
        //return turretZero.get();
    }

    public Trigger atZero = new Trigger(this::zeroTrigger);
    /**
     * This sets up the triggers for the turret. 
     */
    public Command turnCommand(){
        return this.startEnd(
            () -> this.turn(),
            () -> this.stop()
        );
    }

    private boolean atAngle(){
        return Math.abs(this.getAngle() - target) < TurretConstants.turretTolerance;
    }

    public Trigger turretDone = new Trigger(() -> this.atAngle());
    
    public Command turnToCommand(DoubleSupplier angle){
        return this.runOnce(
            () -> this.turnTo(angle.getAsDouble()));
    }

    public Command reverseTurnCommand(){
        return this.startEnd(
            () -> this.reverseTurn(),
            () -> this.stop()
        );
    }

    /**
     * Supplies a command that resets the encoders for turret
     * @return the command that resets the encoders for turret
     */
    public Command zeroCommand(){
        return this.runOnce(() -> this.resetEncoder());
    }

    public Command turnToAlliance(Supplier<Pose2d> pose){
        Pose2d curPose = pose.get();
        CheckAlliance();
        //#todo NOT DONE
        // theta, relative to field = heading of robot assuming 0 degrees points toward hub, clockwise negative
        // turn to angle alpha = 180 degrees - theta, clockwise negitive
        // 180 degrees becuase turret needs to point 180 degrees, field relative
        if (IsRed == false){
            return this.runOnce(() -> this.turnTo(180 - curPose.getRotation().getDegrees()));
        }
        else {
            return this.runOnce(() -> this.turnTo(-(180 - curPose.getRotation().getDegrees())));
        }
    }

    public Command turnToHub(Supplier<Pose2d> pose){
        CheckAlliance();
        Pose2d curPose = pose.get();
        // Blue alliance angle calculations
        // turnto angle alpha, realtive to robot = beta - theta, clockwise negative
        // beta, relative to field = arctan((Huby - Roboty)/(Hubx - Robotx)), clockwise negative
        // theta, relative to field = heading of robot assuming 0 degrees points toward hub, clockwise negative
        if (IsRed == true){
            return this.runOnce(() -> this.turnTo(Math.atan((FieldConstants.redhubY - curPose.getY())/(FieldConstants.redhubX - curPose.getX()))*(180/Math.PI) - curPose.getRotation().getDegrees()));
        }
        else {
            return this.runOnce(() -> this.turnTo(Math.atan((FieldConstants.bluehubY - curPose.getY())/(FieldConstants.bluehubX - curPose.getX()))*(180/Math.PI) - curPose.getRotation().getDegrees()));

        }
    }

}

