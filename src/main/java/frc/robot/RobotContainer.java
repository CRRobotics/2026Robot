// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.OIConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Acquisitions;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.KrakenTest;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final Hopper hopper;
  private final Turret turret;
  private final Shooter shooter;
  private final KrakenTest krakenTest;
  private final Acquisitions acquisition;
  private final Feeder feeder;
  private final Hood hood;
  private final Climb climb;
  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  private final SendableChooser<Command> autoChooser;

  private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
          .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    hopper = new Hopper();
    turret = new Turret();
    shooter = new Shooter();
    krakenTest = new KrakenTest();
    acquisition = new Acquisitions();
    feeder = new Feeder();
    hood = new Hood();
    climb = new Climb();

    SmartDashboard.putNumber("Hood Angle", 0);
    SmartDashboard.putNumber("Shoot Speed", 0);
    
    // Configure the button bindings
    //configureRealBindings();
    configureTestBindings();
    configureDefaults();
    
    // Run at the beginning of match?
    // turret.turnCommand().until(turret.atZero).andThen(turret.zeroCommand());
    // aquisition.ExtendAcquisitionArmCommand().andThen(aquisition.ChangeAqState());

    
    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * Use this method to define your button->command mappings. Button  s can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureRealBindings() {

    drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-MathUtil.applyDeadband(m_driverController.getLeftY() * Math.abs(m_driverController.getLeftY()), OIConstants.kDriveDeadband) * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-MathUtil.applyDeadband(m_driverController.getLeftX() * Math.abs(m_driverController.getLeftX()), OIConstants.kDriveDeadband) * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband) * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
            
   // new JoystickButton(m_operatorController, XboxController.Button.kA.value).whileTrue(hopper.runHopperCommand()).alongWith(feeder.runFeederCommand()));
    m_operatorController.y().whileTrue(shooter.shootCommand(() -> 4000).alongWith(feeder.runFeederCommand()));
    m_operatorController.a().whileTrue(feeder.runFeederCommand());
    m_operatorController.b().whileTrue(turret.turnCommand());
    m_operatorController.x().whileTrue(turret.reverseTurnCommand());
    m_operatorController.leftBumper().whileTrue(hood.turnCommand());
    m_operatorController.rightBumper().whileTrue(hood.reverseTurnCommand());
    //m_operatorController.leftTrigger().whileTrue(acquisition.ExtendAcquisitionArmCommand());
    //m_operatorController.rightTrigger().whileTrue(acquisition.RetractAcquisitionArmCommand());
    m_operatorController.back().whileTrue(hopper.runHopperCommand());
    m_operatorController.start().whileTrue(hopper.runReverseHopperCommand());

    // m_operatorController.rightTrigger().whileTrue(climb.climbExtend().until(climb.topLimitSwitch));
    // m_operatorController.leftTrigger().whileTrue(climb.climbRetract().until(climb.bottomLimitSwitch));
    //TODO: add command to drive forward until rung switch is activated
    
    //#TODO Test this
    // m_operatorController.back()
    //   .whileTrue(turret.turnToHub(() -> m_robotDrive.getPose())
    //   .alongWith(hood.turnToCommand(() -> ValueFromDistance.getAngleSpeed(ValueFromDistance.getDistanceToTarget(m_robotDrive.getPose(), null)).getAngle()))
    //   .andThen(Commands.waitUntil(turret.turretDone.and(hood.hoodDone)))
    //   .andThen(shooter.shootCommand(() -> ValueFromDistance.getAngleSpeed(ValueFromDistance.getDistanceToTarget(m_robotDrive.getPose(), null)).getSpeed()))
    //   .andThen(Commands.waitUntil(shooter.maxSpeed))
    //   .alongWith(feeder.runFeederCommand())
    //   .alongWith(hopper.runHopperCommand()));
    
    m_driverController.a().whileTrue(turret.turnToCommand(() -> 90));
    m_driverController.b().whileTrue(hood.turnToCommand(() -> 90));
    m_driverController.x().whileTrue(shooter.shootCommand(() -> 90));
    m_driverController.leftBumper().whileTrue(acquisition.AcquisitionIntakeCommand());
    m_driverController.rightBumper().whileTrue(acquisition.AcquisitionEjectCommand());
    m_driverController.leftTrigger().whileTrue(acquisition.ExtendAcquisitionArmCommand());
    m_driverController.rightTrigger().whileTrue(acquisition.RetractAcquisitionArmCommand());
    
  }

  private void configureTestBindings() {

    drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-MathUtil.applyDeadband(m_driverController.getLeftY() * Math.abs(m_driverController.getLeftY()), OIConstants.kDriveDeadband) * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-MathUtil.applyDeadband(m_driverController.getLeftX() * Math.abs(m_driverController.getLeftX()), OIConstants.kDriveDeadband) * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband) * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
            
   // new JoystickButton(m_operatorController, XboxController.Button.kA.value).whileTrue(hopper.runHopperCommand()).alongWith(feeder.runFeederCommand()));
    m_operatorController.y()
      .whileTrue(hood.turnToCommand(() -> SmartDashboard.getNumber("Hood Angle", 0))
      .alongWith(shooter.shootCommand(() -> SmartDashboard.getNumber("Shoot Speed", 0)))
      .andThen(Commands.waitUntil(hood.hoodDone.and(shooter.shooterDone)))
      .andThen(feeder.runFeederCommand()));
    m_operatorController.y().onFalse(shooter.shootFinished());

    m_operatorController.a().whileTrue(feeder.runFeederCommand());
    m_operatorController.b().whileTrue(turret.turnCommand());
    m_operatorController.x().whileTrue(turret.reverseTurnCommand());
    m_operatorController.leftBumper().whileTrue(hood.turnCommand());
    m_operatorController.rightBumper().whileTrue(hood.reverseTurnCommand());
    //m_operatorController.leftTrigger().whileTrue(acquisition.ExtendAcquisitionArmCommand());
    //m_operatorController.rightTrigger().whileTrue(acquisition.RetractAcquisitionArmCommand());
    m_operatorController.back().whileTrue(hopper.runHopperCommand());
    m_operatorController.start().whileTrue(hopper.runReverseHopperCommand());

    // m_operatorController.rightTrigger().whileTrue(climb.climbExtend().until(climb.topLimitSwitch));
    // m_operatorController.leftTrigger().whileTrue(climb.climbRetract().until(climb.bottomLimitSwitch));
    //TODO: add command to drive forward until rung switch is activated
    
    //#TODO Test this
    // m_operatorController.back()
    //   .whileTrue(turret.turnToHub(() -> m_robotDrive.getPose())
    //   .alongWith(hood.turnToCommand(() -> ValueFromDistance.getAngleSpeed(ValueFromDistance.getDistanceToTarget(m_robotDrive.getPose(), null)).getAngle()))
    //   .andThen(Commands.waitUntil(turret.turretDone.and(hood.hoodDone)))
    //   .andThen(shooter.shootCommand(() -> ValueFromDistance.getAngleSpeed(ValueFromDistance.getDistanceToTarget(m_robotDrive.getPose(), null)).getSpeed()))
    //   .alongWith(feeder.runFeederCommand())
    //   .alongWith(hopper.runHopperCommand()));
    
    m_driverController.leftBumper().whileTrue(acquisition.AcquisitionIntakeCommand());
    m_driverController.rightBumper().whileTrue(acquisition.AcquisitionEjectCommand());
    m_driverController.leftTrigger().whileTrue(acquisition.ExtendAcquisitionArmCommand());
    m_driverController.rightTrigger().whileTrue(acquisition.RetractAcquisitionArmCommand());

    // Pre-Match Checklist functions
    SmartDashboard.putData("Zero Turret", turret.resetTurretCommand()
      .until(turret.atZero)
      .andThen(turret.zeroTurretCommand()));
    
  }

  public void configureDefaults(){
    hood.atZero.onTrue(hood.zeroHoodCommand());
  }

  public Command getAutonomousCommand(){
    return autoChooser.getSelected();
  }
}
