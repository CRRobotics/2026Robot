package frc.robot.subsystems;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.revrobotics.spark.SparkMax;
import com.studica.frc.AHRS;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Turret extends SubsystemBase {
    SparkMax turretMotor;
    SparkMaxConfig turretConfig;
    
    private double tP;
    private double tI;
    private double tD;

    public Turret () {
        SparkMax turretMotor = new SparkMax(0, MotorType.kBrushless);
        
        SparkMaxConfig turretConfig = new SparkMaxConfig();
        SparkAbsoluteEncoder turretEncoder = turretMotor.getAbsoluteEncoder();

        turretConfig.closedLoop.p(tP);
        turretConfig.closedLoop.p(tI);
        turretConfig.closedLoop.p(tD);

        turretMotor.set(0);
    }
    
    public void setSpeed(double turretSpeed) {
        turretMotor.set(turretSpeed);
    }
}