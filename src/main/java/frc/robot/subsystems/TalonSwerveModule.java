package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class TalonSwerveModule implements SwerveModule{

    private final TalonFX m_drivingTalon;
    private final TalonFX m_turningTalon;

    public TalonSwerveModule(int drivingTalonId, int turningTalonId, double chassisAngularOffset){
        m_drivingTalon = new TalonFX(drivingTalonId);
        m_turningTalon = new TalonFX(turningTalonId);
    }

    @Override
    public SwerveModuleState getState(){

    }

    @Override
    public SwerveModulePosition getPosition(){

    }

    @Override
    public void setDesiredState(SwerveModuleState desiredState){

    }

    @Override
    public void resetEncoders(){

    }
}
