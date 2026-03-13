package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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
        private void stop(){
            hopperMotor.set(0);
        }

        /**
         * Sets the hopper motor to constant speed
         */
        private void runHopper() {
            hopperMotor.set(HopperConstants.hopperSpeed);
        }

        private void runReverseHopper(){
            hopperMotor.set(-HopperConstants.hopperSpeed);
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

        /**
         * @return creates command that runs hopper in reverse
         */

        public Command runReverseHopperCommand(){
            return (this.startEnd(
                () -> this.runReverseHopper(),
                () -> this.stop()
            ));
        }

        public Command specialHopperCommand(){
            return this.runOnce(() -> this.runHopper())
                .andThen(Commands.waitSeconds(3))
                .andThen(this.runOnce(() -> this.runReverseHopper()))
                .andThen(Commands.waitSeconds(0.5))
                .repeatedly()
                .finallyDo(() -> this.stop())
            ;
        }
}
