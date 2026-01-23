package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class KrakenTest extends SubsystemBase{

    private TalonFX motor;

    public KrakenTest(){
        motor = new TalonFX(0);
    }
    
    /**
   * Runs the kraken motor.
   *
   * @param speed input from -1 to 1.
   */
    public void run(double speed){
        motor.set(speed);
    }
    
}
