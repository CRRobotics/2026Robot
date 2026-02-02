package frc.robot.commands.Acquisition;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisitions;
public class AcquisitionEject extends Command{
    Acquisitions acquisition;

    public AcquisitionEject(Acquisitions aquisition){
        
        this.acquisition = aquisition;
        addRequirements(aquisition);
    }

    @Override
    public void initialize(){
        acquisition.eject();
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