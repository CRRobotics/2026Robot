package frc.robot.commands.Acquisition;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisitions;

public class ExtendAcquisitionArm extends Command{
    Acquisitions acquisition;

    public ExtendAcquisitionArm(Acquisitions acquisition){
        
        this.acquisition = acquisition;
        addRequirements(acquisition);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        acquisition.armTurnTo(90);
    }

    @Override 
    public void end(boolean interrupted){
      
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}