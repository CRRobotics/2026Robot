package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;

import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {

    /**
     * Initalize climb motor and sensors
     */

    private final TalonFX m_climb;
    // private final AnalogInput lidarSensor;
    // private final DigitalInput barLimit;
    DigitalInput m_toplimitSwitch;
    DigitalInput m_bottomlimitSwitch;
    DigitalInput m_rungSwitch;
    private Slot0Configs m_climbConfig;
    private TalonFXConfigurator m_climbConfigurator;
    PositionVoltage m_climbRequest;

    public Climb() {

        m_climb = new TalonFX(ClimbConstants.climbMotorID);

        m_climbConfigurator = m_climb.getConfigurator();
        m_toplimitSwitch = new DigitalInput(ClimbConstants.TopLimitSwitchID);
        m_bottomlimitSwitch = new DigitalInput(ClimbConstants.BottomLimitSwitchID);
        m_rungSwitch = new DigitalInput(ClimbConstants.RungSwitchID);

        // lidarSensor = new AnalogInput(ClimbConstants.lidarSensorID);

        // barLimit = new DigitalInput(ClimbConstants.barLimitID);

        /**
         * Config motor
         */

        m_climbConfig = new Slot0Configs();

        m_climbConfig.kP = ClimbConstants.p;
        m_climbConfig.kI = ClimbConstants.i;
        m_climbConfig.kD = ClimbConstants.d;
        m_climb.setNeutralMode(NeutralModeValue.Brake);
        m_climbConfigurator.apply(m_climbConfig);
        m_climbRequest = new PositionVoltage(0).withSlot(0);
        
    }  


    /**
     * Starts climbing up if in range
     */
    public void climbExtend() {
        m_climb.setControl(m_climbRequest.withPosition(ClimbConstants.climbHeight));
    }

    /**
     * resets encoder position to 0
     */

    public void resetEncoder() {
        m_climb.setPosition(0);
    }

    /**
     * Starts climbing down if in range
     */
    // private boolean TopSwitchTrigger(){
    //     return m_toplimitSwitch.get();
    // }

    private boolean BottomSwitchTrigger(){
        return m_bottomlimitSwitch.get();
    }

    // private boolean RungSwitchTrigger(){
    //     return m_rungSwitch.get();
    // }

    // public Trigger topLimitSwitch = new Trigger (this::TopSwitchTrigger);
    public Trigger bottomLimitSwitch = new Trigger (this::BottomSwitchTrigger);
    // public Trigger rungLimitSwitch = new Trigger (this::RungSwitchTrigger);

    public void climbRetract() {
        m_climb.set(ClimbConstants.climbSpeed);
    }

    /**
     * Turns off motor
     */
    public void stop() {
        m_climb.set(0);
    }

    /**
     * @return analog input of lidar sensor
     */
    // public double getLidarOutput() {
    //     return lidarSensor.getVoltage();
    // }

    /**
     * @return digital input of limit switch
     */
    // public boolean getLimitOutput() {
    //     return barLimit.get();  
    // }

    public Command climbExtendCommand(){
        return this.startEnd(
            () -> this.climbExtend(),
            () -> this.stop()
        );
    }

    public Command climbRetractCommand(){
        return this.run(() -> this.climbRetract()).until(bottomLimitSwitch);
    }

    //public Command turnCommand(){
        //return this.startEnd(
            //() -> this.turn(),
            //() -> this.stop()
        //);
    //}
    


}
