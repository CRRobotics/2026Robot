package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {

    /**
     * initialize motor and config
     */

    private final SparkMax feederMotor;

    private SparkMaxConfig configFeeder;


        public Feeder () {
            configFeeder = new SparkMaxConfig();

            configFeeder.idleMode(IdleMode.kCoast);

            feederMotor = new SparkMax(FeederConstants.feederMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

            feederMotor.configure(configFeeder, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        }

        /**
         * stops feeder
         */

        public void stop(){
            feederMotor.set(0.0);

        }

        /**
         * Puts balls into feeder
         */
        
        public void runFeeder() {
            feederMotor.set(FeederConstants.feederSpeed);
        }

        /**
         * @return speed feeder runs at
         */

        public double getFeederSpeed(){
            return FeederConstants.feederSpeed;
        }

        /**
         * @return command that runs feeder
         */

        public Command runFeederCommand(){
            return (this.startEnd(
                () -> this.runFeeder(),
                () -> this.stop()
            ));
        }

}
