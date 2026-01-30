package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
public class RunIndexer extends Command{
    private Indexer indexer;

    public RunIndexer(Indexer indexer){
        
        this.indexer = indexer;
        addRequirements(indexer);
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