package frc.robot.commands.Aquisition;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aquisitions;
public class AquisitionEject extends Command{
    Aquisitions aquisition;

    public AquisitionEject(Aquisitions aquisition){
        
        this.aquisition = aquisition;
        addRequirements(aquisition);
    }

    @Override
    public void initialize(){
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