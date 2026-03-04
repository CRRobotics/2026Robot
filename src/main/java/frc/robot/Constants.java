// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 0.5;
    public static final double kMaxAngularSpeed = 0.5 * Math.PI; // radians per second

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(24.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(24.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 1;
    public static final int kRearLeftDrivingCanId = 6;
    public static final int kFrontRightDrivingCanId = 4;
    public static final int kRearRightDrivingCanId = 7;

    public static final int kFrontLeftTurningCanId = 2;
    public static final int kRearLeftTurningCanId = 8;
    public static final int kFrontRightTurningCanId = 3;
    public static final int kRearRightTurningCanId = 5;

    //"are your gyro angles clockwise positive? if so you should negate them" - wpilib troubleshooting docs
    public static final boolean kGyroReversed = true;

    public static final double stateTrans = 0.1;
    public static final double stateTheta = 0.1;
    public static final double visionTrans = 0.5;
    public static final double visionTheta = 0.5;
  }

  public static final class AcquisitionsConstants {
    public static final double ArmUpSpeed = -0.1;
    public static final double ArmDownSpeed = 0.1;
    public static final double EjectSpeed = 0.5;
    public static final double IntakeSpeed = -0.5;
    public static final double ArmFeedForward = 1;
    public static final double SpinFeedForward = 1;
    public static final int servoChannel = 1;
    public static final int servoLength = 1;
    public static final int servoSpeed = 1;
    public static final double servoOpen = 1;
    public static final double servoClosed = 2;
    public static final int aqSpinID = 62;
    public static final double aqIntakeSpeed = 0.75;
    public static final double armP = 1;
    public static final double armI = 0;
    public static final double armD = 0;
    public static final double armGearRatio = 81;
    public static final double spinP = 9;
    public static final double spinI = 0;
    public static final double spinD = 0;
    public static final double armMotorDownAngle = 90;
    public static final double armMotorUpAngle = 0;
    
  }

  public static final class ClimbConstants {
    //change all values
    public static final int climbMotorID = 0;
    public static final int TopLimitSwitchID = 0;
    public static final int BottomLimitSwitchID = 1;
    public static final int RungSwitchID = 2;
    public static final int lidarSensorID = 3;
    public static final int barLimitID = 4;

    public static final double climbSpeed = 0;

    public static final double p = 0;
    public static final double i = 0;
    public static final double d = 0;

    public static final double lowerBoundVoltage = 0;
    
    public static final double climbHeight = 0.0;
  }

  public static final class TurretConstants {
    public static final int turretLeaderID = 13;
    public static final int turretFollowerID = 14;
    public static final double p = 0.5;
    public static final double i = 0;
    public static final double d = 0;
    public static final double turretGearRatio = 52.29;
    public static final int limitID = 5;
    public static final double turretTolerance = 0.5;
    // -6.25, -5.75 inches
    public static final Transform2d turretTransform = new Transform2d(-0.159,-0.146,null);
    public static final Translation2d turretTranslation = new Translation2d(-0.159,-0.146);
  }

  public static final class HoodConstants {
    public static final int hoodID = 22;
    public static final double p = 0.5;
    public static final double i = 0.000;
    public static final double d = 0;
    public static final double hoodGearRatio = 46/15 * 12;
    public static final double hoodTolerance = 0.5;
    public static final int zeroID = 6;
  }

  public static final class FeederConstants {
    public static final int feederID = 24;
    public static final double feederSpeed = -1;
    public static final int feederMotorCANID = 24;
  }
  
  public static final class HopperConstants {
    public static final int hopperID = 23;
    public static final double hopperSpeed = 0.5;
    public static final int hopperMotorCANID = 23;
  }

  public static final class ModuleConstants {       
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
    // more teeth will result in a robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.025;
    public static final int kOperatorControllerPort = 1;
  }

  //Not actually used for pathplanner
  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static final class FieldConstants {
    public static final double redhubX = 11.901;
    public static final double redhubY = 4.021;
    public static final double bluehubX = 4.612;
    public static final double bluehubY = 4.021;
    public static final Pose2d blueUpperTarget = new Pose2d(0,0,new Rotation2d(0));
    public static final Pose2d blueLowerTarget = new Pose2d(0,0,new Rotation2d(0));
    public static final Pose2d redUpperTarget = new Pose2d(0,0,new Rotation2d(0));
    public static final Pose2d redLowerTarget = new Pose2d(0,0,new Rotation2d(0));
    public static final double upperEquator = 4.763;
    public static final double lowerEquator = 3.279;
    public static final Translation2d redHubTranslation = new Translation2d(11.901,4.021);
    public static final Translation2d blueHubTranslation = new Translation2d(4.612,4.021);

  }

  public static final class ShooterConstants {
    public static final double ShooterTolerance = 100;
    public static final double ShooterSpeedTopLimit = 100;
    public static double ShooterP = 0.0005;
    public static double ShooterI = 0.000000005;
    public static double ShooterD = 0.0;
    public static double ShooterFFW = 0.00016;
    public static int ShooterLeaderCANID = 20;
    public static int ShooterFollowerCANID = 56;
  }

  public static final boolean isSimulated = false;

  public static final Map<String, Transform3d> cameras = getCameraMap(); // Map of camera names to robot-to-cam transforms
  // Populates a HashMap to assign to cameras
  private static Map<String, Transform3d> getCameraMap() {
    Map<String, Transform3d> camerasTemp = new HashMap<String, Transform3d>();
    //                                                                Coords of important point on mount + mount to camera translation
    camerasTemp.put("ArducamA", new Transform3d(new Translation3d((-2.5 - 1.859)*IN_TO_M, (12.75 + 0.253)*IN_TO_M, 16*IN_TO_M), new Rotation3d(0, Math.toRadians(-18), Math.toRadians(90))));
    camerasTemp.put("ArducamC", new Transform3d(new Translation3d((-2.5 - 0.335)*IN_TO_M, (12.75 + 0.253)*IN_TO_M, 16*IN_TO_M), new Rotation3d(0, Math.toRadians(-18), Math.toRadians(150))));
    camerasTemp.put("ArducamD", new Transform3d(new Translation3d((-2.5 - 3.453)*IN_TO_M, (12.75 + 0.253)*IN_TO_M, 16*IN_TO_M), new Rotation3d(0, Math.toRadians(-18), Math.toRadians(30))));
    return camerasTemp;
  }

  public static final double IN_TO_M = 0.0254;
  public static final double LIMELIGHT_X_OFFSET = 0.13;
}
