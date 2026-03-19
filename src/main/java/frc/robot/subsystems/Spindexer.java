package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SpindexerConstants;

public class Spindexer extends SubsystemBase{
    private final SparkMax spindexerMotor;
    private final RelativeEncoder spindexerEncoder;
    private SparkMaxConfig configFeeder;
    
    public Spindexer() {
        configFeeder = new SparkMaxConfig();
        configFeeder.idleMode(IdleMode.kCoast);
        spindexerMotor = new SparkMax(SpindexerConstants.spindexerID, com.revrobotics.spark.SparkLowLevel.MotorType.kBrushless);
        spindexerMotor.configure(configFeeder, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        spindexerEncoder = spindexerMotor.getEncoder();
    }

    public void periodic () {
    }
    /**
     * stops spindexer
     */
    private void stop(){
        spindexerMotor.set(0.0);
    }

    /**
     * run spindexer
     */
    private void runSpindexer() {
        spindexerMotor.set(SpindexerConstants.spindexerSpeed);
    }

    /**
     * runs spindexer in reverse
     */

    private void runReverseSpindexer(){
        spindexerMotor.set(-SpindexerConstants.spindexerSpeed);
    }

    /**
     * @return speed spindexer runs at
     */
    public double getFeederSpeed(){
        return spindexerEncoder.getVelocity();
    }

    /**
     * @return command that runs spindexer
     */
    public Command runSpindexerCommand(){
        return this.startEnd(
            () -> this.runSpindexer(),
            () -> this.stop()
        );
    }

    /**
     * @return creates a command that runs spindexer in reverse
     */
    public Command runReverseSpindexerCommand(){
        return this.startEnd(
            () -> this.runReverseSpindexer(),
            () -> this.stop()
        );
    }
}
