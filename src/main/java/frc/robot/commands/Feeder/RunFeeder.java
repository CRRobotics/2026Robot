package frc.robot.commands.Feeder;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
public class RunFeeder extends Command{
    private Feeder feeder;

    public RunFeeder(Feeder feeder){
        
        this.feeder = feeder;
        addRequirements(feeder);
    }

    @Override
    public void initialize(){
        feeder.runFeeder();

    }

    @Override 
    public void end(boolean interrupted){
        feeder.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}