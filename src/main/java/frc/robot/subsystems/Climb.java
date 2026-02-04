package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {
    private final TalonFX m_climb;
    private final AnalogInput lidarSensor;
    private final DigitalInput barLimit;

    private Slot0Configs m_climbConfig;

    public Climb() {
        m_climb = new TalonFX(ClimbConstants.climbMotorID);
        m_climb.set(0);

        lidarSensor = new AnalogInput(ClimbConstants.lidarSensorID);

        barLimit = new DigitalInput(ClimbConstants.barLimitID);

        m_climbConfig = new Slot0Configs();

        m_climbConfig.kP = ClimbConstants.p;
        m_climbConfig.kI = ClimbConstants.i;
        m_climbConfig.kD = ClimbConstants.d;

        m_climb.getConfigurator().apply(m_climbConfig);
    }  

    public void climbUp() {
        if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
            m_climb.set(ClimbConstants.climbSpeed);
        } else {
            m_climb.set(0);
        }
    }

    public void climbDown() {
        if (lidarSensor.getVoltage() >= ClimbConstants.lowerBoundVoltage) {
            m_climb.set(-ClimbConstants.climbSpeed);
        } else {
            m_climb.set(0);
        }
    }

    public void stop() {
        m_climb.set(0);
    }
    
    public boolean getLimitOutput() {
        return barLimit.get();  
    }

    public double getLidarOutput() {
        return lidarSensor.getVoltage();
    }
}
