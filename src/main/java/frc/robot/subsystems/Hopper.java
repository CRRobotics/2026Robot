package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {

    private final SparkMax motor;

    private SparkMaxConfig configHopper;


        public Hopper () {
            motor = new SparkMax(1, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            
            configHopper = new SparkMaxConfig();

            motor.configure(configHopper, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        }

        public void stop(){
            motor.set(0);
        }

        //Puts balls into feeder
        public void loadFeeder() {
            motor.set(Constants.IndexerConstants.hopperSpeed);
        }

        public double getSpeed(){
            return Constants.IndexerConstants.hopperSpeed;
        }

}
