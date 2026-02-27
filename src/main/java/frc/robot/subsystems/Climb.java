package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
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
    }  


    /**
     * Starts climbing up if in range
     */
    // public void climbUp() {
    //     if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
    //         m_climb.set(ClimbConstants.climbSpeed);
    //     } else {
    //         m_climb.set(0);
    //     }
    // }

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

    // private boolean BottomSwitchTrigger(){
    //     return m_bottomlimitSwitch.get();
    // }

    // private boolean RungSwitchTrigger(){
    //     return m_rungSwitch.get();
    // }

    // public Trigger topLimitSwitch = new Trigger (this::TopSwitchTrigger);
    // public Trigger bottomLimitSwitch = new Trigger (this::BottomSwitchTrigger);
    // public Trigger rungLimitSwitch = new Trigger (this::RungSwitchTrigger);

    // public void climbDown() {
    //     if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
    //         m_climb.set(-ClimbConstants.climbSpeed);
    //     } else {
    //         m_climb.set(0);
    //     }
    // }

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

    // public Command climbExtend(){
    //     return this.startEnd(
    //         () -> this.climbUp(),
    //         () -> this.stop()
    //     );
    // }

    // public Command climbRetract(){
    //     return this.startEnd(
    //         () -> this.climbDown(),
    //         () -> this.stop()
    //     );
    // }

    //public Command turnCommand(){
        //return this.startEnd(
            //() -> this.turn(),
            //() -> this.stop()
        //);
    //}


}
