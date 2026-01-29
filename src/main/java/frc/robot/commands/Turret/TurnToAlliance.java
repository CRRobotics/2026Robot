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
        //#todo NOT DONE
        turret.turnTo(0);
    }

    @Override 
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
