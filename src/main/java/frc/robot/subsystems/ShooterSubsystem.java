package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;



public class ShooterSubsystem extends SubsystemBase {
    TalonFX m_shooterMotorFollower;
    TalonFX m_shooterMotorLeader;
    CANcoder m_shooterEncoder;
    public ShooterSubsystem(){
        // Initialize motors and set device IDs
        TalonFX m_shooterMotorLeader = new TalonFX(ShooterConstants.kLeaderMotorPort);
        TalonFX m_shooterMotorFollower = new TalonFX(ShooterConstants.kLeaderMotorPort);
        m_shooterMotorLeader.set(0);
        m_shooterMotorFollower.set(0);
    }



    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    /**
     * Sets the speed of the shooter motors.
     * @param speed The speed (as a percentage of max voltage, -1.0 to 1.0)
     */
    public void setSpeed() {
        m_shooterMotorLeader.set(ShooterConstants.leadingMotorSpeed);
        m_shooterMotorFollower.set(ShooterConstants.leadingMotorSpeed);
    }
     

    /**
     * Stops the shooter motors.
     */
    public void stopShooter() {
        m_shooterMotorLeader.set(0);
    }
}


