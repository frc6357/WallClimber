// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmReleaseCommand;
import frc.robot.commands.ArmVacuumCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ExtendCommand;
import frc.robot.commands.FrameReleaseCommand;
import frc.robot.commands.FrameVacuumCommand;
import frc.robot.commands.RetractCommand;
import frc.robot.subsystems.ClimbSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final PneumaticHub hub = new PneumaticHub(1);

  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem(hub);
  private final Joystick joystick = new Joystick(Constants.OPERATOR_JOYSTICK);

  private final ExampleCommand m_autoCommand = new ExampleCommand(climbSubsystem);

  private Solenoid retract;
  private Solenoid extend;
  
  private Solenoid frameVacuum;
  private Solenoid armVacuum;
  private Solenoid frameRelease;
  private Solenoid armRelease;
  


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    hub.enableCompressorDigital();
    retract = new Solenoid(PneumaticsModuleType.REVPH, 0);
    extend = new Solenoid(PneumaticsModuleType.REVPH, 1);
    frameVacuum = new Solenoid(PneumaticsModuleType.REVPH, 2);
    frameRelease = new Solenoid(PneumaticsModuleType.REVPH, 3);
    armVacuum = new Solenoid(PneumaticsModuleType.REVPH, 4);
    armRelease = new Solenoid(PneumaticsModuleType.REVPH, 5);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton frameVacuumButton = new JoystickButton(joystick, 1);
    frameVacuumButton.whenPressed(new FrameVacuumCommand(climbSubsystem, frameVacuum, frameRelease));
    JoystickButton frameReleaseButton = new JoystickButton(joystick, 2);
    frameReleaseButton.whenPressed(new FrameReleaseCommand(climbSubsystem, frameVacuum, frameRelease));
    JoystickButton armVacuumButton = new JoystickButton(joystick, 3);
    armVacuumButton.whenPressed(new ArmVacuumCommand(climbSubsystem, armVacuum, armRelease));
    JoystickButton armReleaseButton = new JoystickButton(joystick, 4);
    armReleaseButton.whenPressed(new ArmReleaseCommand(climbSubsystem, armVacuum, armRelease));
    JoystickButton extendButton = new JoystickButton(joystick, 5);
    extendButton.whenPressed(new ExtendCommand(climbSubsystem, extend, retract));
    JoystickButton retractButton = new JoystickButton(joystick, 6);
    retractButton.whenPressed(new RetractCommand(climbSubsystem, extend, retract));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
