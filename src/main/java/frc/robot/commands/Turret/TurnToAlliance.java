package frc.robot.commands.Turret;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.Turret;

public class TurnToAlliance extends Command{

    Turret turret;
    Supplier<Pose2d> pose;

    public TurnToAlliance(Turret turret, Supplier<Pose2d> pose){
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

        //#todo NOT DONE
        // theta, relative to field = heading of robot assuming 0 degrees points toward hub, clockwise negative
        // turn to angle alpha = 180 degrees - theta, clockwise negitive
        // 180 degrees becuase turret needs to point 180 degrees, field relative
        turret.turnTo(180 - curPose.getRotation().getDegrees());
    }

    @Override 
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
