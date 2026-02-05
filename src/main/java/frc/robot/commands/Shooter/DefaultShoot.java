package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
public class DefaultShoot extends Command{
    private Shooter shooter;

    public DefaultShoot(Shooter shooter){
        
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        shooter.setSpeed(0.1);
    }

    @Override 
    public void end(boolean interrupted){
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}