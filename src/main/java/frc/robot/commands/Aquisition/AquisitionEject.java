package frc.robot.commands.AquisitionEject;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeEndEffector;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Constants;

public class AquisitionEject extends Command{
    Aquisitions aquisition;

    public Aquisition(Aquisition aquisition){
        
        this.aquisition = aquisition;
        //addRequirements();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        aquisition.eject();
    }

    @Override 
    public void end(boolean interrupted){
        aquisition.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}