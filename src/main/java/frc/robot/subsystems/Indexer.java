package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {

    private final SparkMax hopperMotor;
    private final SparkMax feederMotor;

    private SparkMaxConfig configIndexer;


        public Indexer () {
            hopperMotor = new SparkMax(Constants.IndexerConstants.hopperMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            feederMotor = new SparkMax(Constants.IndexerConstants.feederMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

            configIndexer = new SparkMaxConfig();

            feederMotor.configure(configIndexer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            hopperMotor.configure(configIndexer, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        }

        public void stop(){
            hopperMotor.set(0);
            feederMotor.set(0);

        }

        //Puts balls into feeder
        public void runIndexer() {
            hopperMotor.set(Constants.IndexerConstants.hopperSpeed);
            feederMotor.set(Constants.IndexerConstants.feederSpeed);
        }

        public double getFeederSpeed(){
            return Constants.IndexerConstants.feederSpeed;
        }

        public double getHopperSpeed(){
            return Constants.IndexerConstants.hopperSpeed;
        }

}
