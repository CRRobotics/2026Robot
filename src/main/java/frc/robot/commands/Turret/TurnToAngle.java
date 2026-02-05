package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Turret;

public class TurnToAngle extends Command{

    Turret turret;
    Double angle;

    public TurnToAngle(Turret turret, Double angle){
        this.turret = turret;
        this.angle = angle;
        addRequirements(turret);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        turret.turnTo(angle);
    }

    @Override 
    public void end(boolean interrupted){
        turret.turnTo(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
