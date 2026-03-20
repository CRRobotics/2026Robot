package frc.robot.util.ShooterCalculations;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class MoveShoot{
    /**
     * Calculates the displacement from the robot to where the robot should shoot while moving.
     * @param curPos The robot's current field-relative position in meters
     * @param curVel The robot's current field-relative velocity in meters/sec
     * @param targetPos The position of where the robot wants to shoot field-relative in meters
     * @param timeToTarget The time it is expected to take for the fuel to get to the target in seconds
     * @return A translation from the robot's current position to the place it should shoot in meters
     */
    public static Translation2d getHubFinalDisplacement(Translation2d curPos, Translation2d curVel, Translation2d targetPos, double timeToTarget) {
        Translation2d curToTarget = targetPos.minus(curPos); // Target current position relative to robot
        Translation2d velRelativeX0 = curVel.times(curToTarget.dot(curVel) / curVel.getSquaredNorm()); // The projection of curToTarget onto curVel
        Translation2d velRelativeY = curToTarget.minus(velRelativeX0); // The perpendicular vector to curVel that points to targetPos
        Translation2d velRelativeXf = velRelativeX0.plus(curVel.times(timeToTarget)); // Coordinate of target relative to robot after robot moves for timeToTarget seconds
        Translation2d curToFinal = velRelativeXf.plus(velRelativeY); // Final pose of target relative to the robot's initial reference frame
        return curToFinal;
    }
    public static Pose2d GetRobotDisplacement(Pose2d curPos, ChassisSpeeds chassisspeeds, Double timeToTarget) {
        Transform2d VelocityTransform = new Transform2d(timeToTarget*chassisspeeds.vxMetersPerSecond, timeToTarget*chassisspeeds.vyMetersPerSecond, new Rotation2d(0));
        Pose2d TargetTranslation = (curPos.plus(VelocityTransform));
        return TargetTranslation;
    }
}