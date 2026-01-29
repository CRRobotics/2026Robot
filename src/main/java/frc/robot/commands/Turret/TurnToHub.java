package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.Turret;

public class TurnToHub extends Command{
    private Turret turret;
    private Supplier<Pose2d> pose;

    public TurnToHub(Turret turret, Supplier<Pose2d> pose){
        
        this.turret = turret;
        this.pose = pose;
        addRequirements(turret);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        Pose2d curPose = pose.get();
        // Blue alliance angle calculations
        // turnto angle alpha, realtive to robot = beta - theta, clockwise negative
        // beta, relative to field = arctan((Huby - Roboty)/(Hubx - Robotx)), clockwise negative
        // theta, relative to field = heading of robot assuming 0 degrees points toward hub, clockwise negative
        turret.turnTo(Math.atan((FieldConstants.hubY - curPose.getY())/(FieldConstants.hubX - curPose.getX())) - curPose.getRotation().getDegrees());
    }

    @Override 
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
