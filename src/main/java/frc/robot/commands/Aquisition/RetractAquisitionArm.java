package frc.robot.commands.Aquisition;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aquisitions;

public class RetractAquisitionArm extends Command{
    Aquisitions aquisition;

    public RetractAquisitionArm(Aquisitions aquisition){
        
        this.aquisition = aquisition;
        addRequirements(aquisition);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        aquisition.armTurnTo(0);
    }

    @Override 
    public void end(boolean interrupted){
      
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}