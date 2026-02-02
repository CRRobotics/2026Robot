package frc.robot.commands.Acquisition;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisitions;

public class RetractAcquisitionArm extends Command{
    Acquisitions acquisition;

    public RetractAcquisitionArm(Acquisitions acquisition){
        
        this.acquisition = acquisition;
        addRequirements(acquisition);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        acquisition.armTurnTo(0);
    }

    @Override 
    public void end(boolean interrupted){
      
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}