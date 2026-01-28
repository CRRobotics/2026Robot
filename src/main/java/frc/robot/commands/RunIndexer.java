package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
public class RunIndexer extends Command{
    private Indexer indexer;

    public RunIndexer(Indexer indexer){
        
        this.indexer = indexer;
        //addRequirements();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        indexer.runIndexer();
    }

    @Override 
    public void end(boolean interrupted){
        indexer.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}