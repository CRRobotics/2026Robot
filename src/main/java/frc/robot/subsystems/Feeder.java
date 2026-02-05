package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.HopperConstants;
import frc.robot.Constants.IndexerConstants;

public class Feeder extends SubsystemBase {

    private final SparkMax feederMotor;

    private SparkMaxConfig configFeeder;


        public Feeder () {
            configFeeder = new SparkMaxConfig();

            configFeeder.idleMode(IdleMode.kCoast);

            
            configFeeder.closedLoop.p(FeederConstants.p);
            configFeeder.closedLoop.p(FeederConstants.i);
            configFeeder.closedLoop.p(FeederConstants.d);


            feederMotor = new SparkMax(IndexerConstants.feederMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

            feederMotor.configure(configFeeder, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);


        }

        public void stop(){
            feederMotor.set(0);

        }

        //Puts balls into feeder
        public void runFeeder() {
            feederMotor.set(IndexerConstants.feederSpeed);
        }

        public double getFeederSpeed(){
            return IndexerConstants.feederSpeed;
        }

}
