package frc.robot.util;

import edu.wpi.first.math.controller.ProfiledPIDController;
import frc.robot.Constants;

public class ProfiledPID{

    public ProfiledPIDController xController;
    public ProfiledPIDController yController;
    public ProfiledPIDController rotationController;

    public ProfiledPID(){
        xController = new ProfiledPIDController(
            Constants.DriveConstants.autoMoveK.kP,
            Constants.DriveConstants.autoMoveK.kI,
            Constants.DriveConstants.autoMoveK.kD,
            Constants.DriveConstants.autoMoveConstraints);
        yController = new ProfiledPIDController(
            Constants.DriveConstants.autoMoveK.kP,
            Constants.DriveConstants.autoMoveK.kI,
            Constants.DriveConstants.autoMoveK.kD,
            Constants.DriveConstants.autoMoveConstraints);
        rotationController = new ProfiledPIDController(
            Constants.DriveConstants.autoTurnK.kP,
            Constants.DriveConstants.autoTurnK.kI,
            Constants.DriveConstants.autoTurnK.kD,
            Constants.DriveConstants.autoTurnConstraints);
        rotationController.setIZone(Constants.DriveConstants.autoTurnK.iZone);
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
    }
}