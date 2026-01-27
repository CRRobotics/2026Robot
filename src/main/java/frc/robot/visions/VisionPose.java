package frc.robot.visions;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 * A class meant to store data about the pose of the robot according to Visions.
 * @author Elijah Kelly
 * @version 0.0.0
 */
public class VisionPose {
    private Pose2d pose;
    private double timestamp;

    /**
     * Constructs a VisionPose with the specified pose and timestamp.
     * @param pose The Pose2d of the robot
     * @param timestamp The timestamp in seconds from which the pose used data to calculate
     */
    public VisionPose(Pose2d pose, double timestamp) {
        this.pose = pose;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a VisionPose with the specified pose and timestamp.
     * @param pose The Pose3d of the robot, to be converted to a Pose2d
     * @param timestamp The timestamp in seconds from which the pose used data to calculate
     */
    public VisionPose(Pose3d pose, double timestamp) {
        this.pose = new Pose2d(pose.getX(), pose.getY(), new Rotation2d(pose.getRotation().getZ()));
        this.timestamp = timestamp;
    }

    /**
     * Returns the robot pose according to this Visions data.
     * @return A Pose2d with the pose of the robot on the field
     */
    public Pose2d getPose() {
        return pose;
    }

    /**
     * Returns the timestamp from which the Vision data was taken.
     * @return The timestamp in seconds of the pose
     */
    public double getTimestamp() {
        return timestamp;
    }

    @Override
    /**
     * Returns a string representation of the VisionPose.
     * @return A string with the pose and timestamp
     */
    public String toString() {
        return "VisionPose: pose = " + pose + ", timestamp = " + timestamp + "sec";
    }

    @Override
    /**
     * Returns whether this object is equal to the specified otherObject.
     * @param otherObject The object to compare to
     * @return true if the objects are equal, false if not.
     */
    public boolean equals(Object otherObject) {
        if (otherObject == null || otherObject.getClass() != VisionPose.class)
            return false;
        VisionPose other = (VisionPose)otherObject;

        return other.pose.equals(this.pose) && other.timestamp == this.timestamp;
    }
}
