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

public class Hopper extends SubsystemBase {

    private final SparkMax hopperMotor;

    private SparkMaxConfig configHopper;


        public Hopper () {
            configHopper = new SparkMaxConfig();

            configHopper.idleMode(IdleMode.kCoast);

            configHopper.closedLoop.p(HopperConstants.p);
            configHopper.closedLoop.p(HopperConstants.i);
            configHopper.closedLoop.p(HopperConstants.d);

            hopperMotor = new SparkMax(IndexerConstants.hopperMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

            hopperMotor.configure(configHopper, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);


        }

        public void stop(){
            hopperMotor.set(0);
        }

        //Puts balls into feeder
        public void runFeeder() {
            hopperMotor.set(IndexerConstants.hopperSpeed);
        }

        public double getFeederSpeed(){
            return IndexerConstants.feederSpeed;
        }


}
