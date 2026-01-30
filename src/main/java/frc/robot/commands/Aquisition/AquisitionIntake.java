package frc.robot.commands.Aquisition;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aquisitions;

public class AquisitionIntake extends Command{
    Aquisitions aquisition;

    public AquisitionIntake(Aquisitions aquisition){
        
        this.aquisition = aquisition;
        addRequirements(aquisition);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        aquisition.intake();
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