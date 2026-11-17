package frc.robot.subsystems;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AcquisitionsConstants;
import frc.robot.Constants.HoodConstants;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class AcquisitionArma extends SubsystemBase {

    /**
     * Initalize climb motor and sensors
     */

    // private final TalonFX m_climb;
    // // private final AnalogInput lidarSensor;
    // // private final DigitalInput barLimit;
    // DigitalInput m_toplimitSwitch;
    // DigitalInput m_bottomlimitSwitch;
    // DigitalInput m_rungSwitch;
    // private Slot0Configs m_climbConfig;
    // private TalonFXConfigurator m_climbConfigurator;
    // PositionVoltage m_climbRequest;
    // private final SparkClosedLoopController armPID;
    private final SparkMax armMotor;
    private final SparkMaxConfig armConfig;
    // private double target;



    public AcquisitionArma() {
        
        armMotor = new SparkMax(AcquisitionsConstants.acqArmCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        armConfig.smartCurrentLimit(105); //adjust this value for maximum current input--105 is ideal for NEO vortex
        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        // armConfig.closedLoop.p(AcquisitionsConstants.armP);
        // armConfig.closedLoop.i(AcquisitionsConstants.armI);
        // armConfig.closedLoop.d(AcquisitionsConstants.armD);
        armConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder); 
    }

    //     m_climb = new TalonFX(ClimbConstants.climbMotorID);

    //     m_climbConfigurator = m_climb.getConfigurator();
    //     m_toplimitSwitch = new DigitalInput(ClimbConstants.TopLimitSwitchID);
    //     m_bottomlimitSwitch = new DigitalInput(ClimbConstants.BottomLimitSwitchID);
    //     m_rungSwitch = new DigitalInput(ClimbConstants.RungSwitchID);

    //     lidarSensor = new AnalogInput(ClimbConstants.lidarSensorID);

    //     barLimit = new DigitalInput(ClimbConstants.barLimitID);

    //     /**
    //      * Config motor
    //      */

    //     m_climbConfig = new Slot0Configs();

    //     m_climbConfig.kP = ClimbConstants.p;
    //     m_climbConfig.kI = ClimbConstants.i;
    //     m_climbConfig.kD = ClimbConstants.d;
    //     m_climb.setNeutralMode(NeutralModeValue.Brake);
    //     m_climbConfigurator.apply(m_climbConfig);
    //     m_climbRequest = new PositionVoltage(0).withSlot(0);
        
    // }  
    // armPID = armMotor.getClosedLoopController();

    // public void turnTo (double angle) {
    //     SmartDashboard.putNumber("hood going to", angle);
    //     armPID.setSetpoint(angle / 360 * AcquisitionsConstants.ArmGearRatio,ControlType.kPosition);
    //     target = angle;
    //         Logger.recordOutput("Arm/TurnTo", angle);
    // }


    // /**
    //  * Starts climbing up if in range
    //  */
    // public void climbExtend() {
    //     m_climb.set(ClimbConstants.climbSpeed);
    //     //m_climb.setControl(m_climbRequest.withPosition(ClimbConstants.climbHeight));
    // }

    public void acqArmExtend(double speed){
        armMotor.set(speed);
    }
//     }
//     public Command ArmturnToCommand(DoubleSupplier angle){
//         return this.startEnd(
//             () -> this.turnTo(angle.getAsDouble()),
//             () -> this.turnTo(60)
//         );
//     }
//     public Command ArmturnBackCommand(DoubleSupplier angle){
//     return this.startEnd(
//         () -> this.turnTo(angle.getAsDouble()),
//         () -> this.turnTo(10)
//     );
// }

        public void acqArmRetract(double speed){
        armMotor.set(-speed);
    }

    // /**
    //  * resets encoder position to 0
    //  */

    // public void resetEncoder() {
    //     m_climb.setPosition(0);
    // }

    // /**
    //  * Starts climbing down if in range
    //  */
    // private boolean TopSwitchTrigger(){
    //     return m_toplimitSwitch.get();
    // }

    // private boolean BottomSwitchTrigger(){
    //     return m_bottomlimitSwitch.get();
    // }

    // private boolean RungSwitchTrigger(){
    //     return m_rungSwitch.get();
    // }

    // public Trigger topLimitSwitch = new Trigger (this::TopSwitchTrigger);
    // public Trigger bottomLimitSwitch = new Trigger (this::BottomSwitchTrigger);
    // public Trigger rungLimitSwitch = new Trigger (this::RungSwitchTrigger);

    // public void climbRetract() {
    //     m_climb.set(-ClimbConstants.climbSpeed);
    // }

    /**
     * Turns off motor
    //  */
    // public void stop() {
    //     m_climb.set(0);
    

    public void stop(){
        armMotor.set(0);
    }

    // /**
    //  * @return analog input of lidar sensor
    //  */
    // public double getLidarOutput() {
    //     return lidarSensor.getVoltage();
    // }

    // /**
    //  * @return digital input of limit switch
    //  */
    // public boolean getLimitOutput() {
    //     return barLimit.get();  
    // }

    public Command acqArmExtendCommand(){
        return this.startEnd(
            () -> this.acqArmExtend(AcquisitionsConstants.acqArmSpeed), 
            () -> this.stop()
            );
    }


        public Command acqArmRetractCommand(){
        return this.startEnd(
            () -> this.acqArmRetract(AcquisitionsConstants.acqArmSpeedUp), 
            () -> this.stop()
            );
    //     }
    //     public Command climbExtendCommand(){
    //     return this.startEnd(
    //         () -> this.climbExtend(),
    //         () -> this.stop()
    //     );
    // }

    // public Command climbRetractCommand(){
    //     return this.startEnd(
    //         () -> this.climbRetract(), 
    //         () -> this.stop()
    //         );
    //     //return this.run(() -> this.climbRetract()).until(bottomLimitSwitch);
    // }

    // public Command turnCommand(){
    //     return this.startEnd(
    //         () -> this.turn(),
    //         () -> this.stop()
    //     );
    // }



    }}

