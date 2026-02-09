package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HopperConstants;

public class Hopper extends SubsystemBase {

    /**
     * initialize motor and construct class
     */

    private final SparkMax hopperMotor;

    private SparkMaxConfig configHopper;
        public Hopper () {
            configHopper = new SparkMaxConfig();

            configHopper.idleMode(IdleMode.kCoast);

            hopperMotor = new SparkMax(HopperConstants.hopperMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);

            hopperMotor.configure(configHopper, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);


        }

        /**
         * stops motor
         */

        public void stop(){
            hopperMotor.set(0);
        }

        /**
         * Sets the hopper motor to constant speed
         */

        public void runHopper() {
            hopperMotor.set(HopperConstants.hopperSpeed);
        }

        /**
         * @return double speed hopper runs at
         */

        public double getHopperSpeed(){
            return HopperConstants.hopperSpeed;
        }

        /**
         * @return command that runs hopper
         */

        public Command runHopperCommand(){
            return (this.startEnd(
                () -> this.runHopper(),
                () -> this.stop()
            ));
        }
}
