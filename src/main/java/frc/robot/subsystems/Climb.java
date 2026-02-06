package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;

import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {

    /**
     * Initalize climb motor and sensors
     */

    private final TalonFX m_climb;
    private final AnalogInput lidarSensor;
    private final DigitalInput barLimit;

    private Slot0Configs m_climbConfig;

    public Climb() {

        m_climb = new TalonFX(ClimbConstants.climbMotorID);
        m_climb.set(0);

        lidarSensor = new AnalogInput(ClimbConstants.lidarSensorID);

        barLimit = new DigitalInput(ClimbConstants.barLimitID);

        /**
         * Config motor
         */

        m_climbConfig = new Slot0Configs();

        m_climbConfig.kP = ClimbConstants.p;
        m_climbConfig.kI = ClimbConstants.i;
        m_climbConfig.kD = ClimbConstants.d;

        m_climb.getConfigurator().apply(m_climbConfig);
    }  


    /**
     * Starts climbing up if in range
     */

    public void climbUp() {
        if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
            m_climb.set(ClimbConstants.climbSpeed);
        } else {
            m_climb.set(0);
        }
    }


    /**
     * Starts climbing down if in range
     */

    public void climbDown() {
        if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
            m_climb.set(-ClimbConstants.climbSpeed);
        } else {
            m_climb.set(0);
        }
    }

    /**
     * Turns off motor
     */

    public void stop() {
        m_climb.set(0);
    }

    /**
     * @return digital input of limit switch
     */
    
    public boolean getLimitOutput() {
        return barLimit.get();  
    }

    /**
     * @return analog input of lidar sensor
     */

    public double getLidarOutput() {
        return lidarSensor.getVoltage();
    }
}
