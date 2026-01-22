package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class KrakenTest extends SubsystemBase{

    private TalonFX motor;

    public KrakenTest(){
        motor = new TalonFX(0);
    }

    public void run(){
        motor.set(0.1);
    }
    
}
