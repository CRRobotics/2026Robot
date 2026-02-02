package frc.robot.commands.Acquisition;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisitions;

public class AcquisitionIntake extends Command{
    Acquisitions acquisition;

    public AcquisitionIntake(Acquisitions acquisition){
        
        this.acquisition = acquisition;
        addRequirements(acquisition);
    }

    @Override
    public void initialize(){
        acquisition.intake();
    }

    @Override 
    public void end(boolean interrupted){
        acquisition.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}