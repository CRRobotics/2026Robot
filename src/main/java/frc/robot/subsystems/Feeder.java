package frc.robot.subsystems;
import org.littletonrobotics.junction.Logger;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.util.Configs.FeederConfig;

public class Feeder extends SubsystemBase {

    /**
     * initialize motor and config
     */

    private final SparkMax feederMotor;
    private final RelativeEncoder feederEncoder;
    private final SparkClosedLoopController FeederPID;
    private SparkMaxConfig configFeeder;
    
        public Feeder () {
            configFeeder = new SparkMaxConfig();
            configFeeder.closedLoop.pid(FeederConstants.FeederP, FeederConstants.FeederI, FeederConstants.FeederD).feedForward.kV(FeederConstants.FeederFFW);
            configFeeder.idleMode(IdleMode.kCoast);
            feederMotor = new SparkMax(FeederConstants.feederMotorCANID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
            feederMotor.configure(configFeeder, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            feederEncoder = feederMotor.getEncoder();
            FeederPID = feederMotor.getClosedLoopController();

        }

        public void periodic () {
            SmartDashboard.putNumber("Feeder Speed", getFeederSpeed());
            Logger.recordOutput("Feeder Speed Constant", FeederConstants.feederSpeed);
            Logger.recordOutput("AngleSpeedTime/Input/Distance", feederEncoder.getVelocity());
        }
        /**
         * stops feeder
         */
        private void stop(){
            feederMotor.set(0.0);
        }

        /**
         * Puts balls into feeder
         */
        private void runFeeder(double speed) {
            FeederPID.setSetpoint(speed,ControlType.kVelocity);
        }

        /**
         * runs feeder in reverse
         */

        private void runReverseFeeder(){
            feederMotor.set(-FeederConstants.feederSpeed);
        }

        /**
         * @return speed feeder runs at
         */
        public double getFeederSpeed(){
            return feederEncoder.getVelocity();
        }

        /**
         * @return command that runs feeder
         */
        public Command runFeederCommand(){
            return (this.startEnd(
                () -> this.runFeeder(SmartDashboard.getNumber("Input Feeder Speed", 0)),
                () -> this.stop()
            ));
        }

        /**
         * @return creates a command that runs feeder in reverse
         */
        public Command runReverseFeederCommand(){
            return (this.startEnd(
                () -> this.runReverseFeeder(),
                () -> this.stop()
            ));
        }

}
