package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Turret;

public class Turn extends Command{

    Turret turret;
    Double angle;

    public Turn(Turret turret){
        this.turret = turret;
        addRequirements(turret);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        turret.turn();
    }

    @Override 
    public void end(boolean interrupted){
        turret.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
