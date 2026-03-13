package frc.robot.subsystems.vision;

import java.util.Optional;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class NetworkTablesIO {
    /*@AutoLog
    public static class NetworkTablesInputs {
        private Translation2d latestLimelightPose = new Translation2d();
    }*/ // This could be useful with more time

    public static double getDouble(String table, String key) {
        return NetworkTableInstance.getDefault().getTable(String.valueOf(table)).getEntry(key).getDouble(0);
    }
    
    public static double[] getArray(String table, String key) {
        return NetworkTableInstance.getDefault().getTable(String.valueOf(table)).getEntry(key).getDoubleArray(new double[]{});
    }

    /**
     * Returns the current pose the Limelight is reporting for the nearest fuel.
     * @return A Translation2d for the robot-relative pose of the fuel, if there is any detected
     */
    public static Optional<Translation2d> getLimelightPoseReport() {
        double[] pieceData = getArray("limelight","llpython");
        if (pieceData.length == 0 || (pieceData[0] == 0 && pieceData[1] == 0)){
            // System.out.println("Empty Array");
            return Optional.empty();
        }
        
        // System.out.println(pieceData[0] + ", " + pieceData[1]);

        Translation2d closestPiece = new Translation2d(
            pieceData[0] * Constants.IN_TO_M,
            new Rotation2d(pieceData[1])
        ).minus(new Translation2d(Constants.LIMELIGHT_X_OFFSET, Constants.LIMELIGHT_Y_OFFSET));
        Logger.recordOutput("Vision/ObjectDetection/FuelTranslation", closestPiece);
        return Optional.of(closestPiece);
    }
}