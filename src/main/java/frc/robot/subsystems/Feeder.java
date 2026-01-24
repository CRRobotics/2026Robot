package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {

    private final SparkMax motor;

    private SparkMaxConfig configFeeder;


        public Feeder () {
            motor = new SparkMax(1, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            
            configFeeder = new SparkMaxConfig();

            motor.configure(configFeeder, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        }

        public void stop(){
            motor.set(0);
        }

        //Puts balls into feeder
        public void outtake() {
            motor.set(Constants.FeederConstants.feederSpeed);
        }

        public double getSpeed(){
            return Constants.FeederConstants.feederSpeed;
        }

}
