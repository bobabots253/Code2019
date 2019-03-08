/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Drivetrain.Drivetrain;
import frc.robot.Hatch.HatchSubsystem;
import frc.robot.Misc.Constants;
import frc.robot.Misc.OI;
import frc.robot.Misc.OI.CamMode;
import frc.robot.Misc.OI.LEDMode;
import frc.robot.Shooter.ShooterSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  Compressor compressor = new Compressor(Constants.kPCM_ID);
  Spark LEDSpark = new Spark(1);
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static Drivetrain drivetrain;
  public static ShooterSubsystem shooter;
  public static HatchSubsystem hatch;
  public static OI oi;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    drivetrain = Drivetrain.getInstance();
    shooter = ShooterSubsystem.getInstance();
    hatch = HatchSubsystem.getInstance();
    oi = OI.getInstance();

    Robot.oi.setLEDMode(LEDMode.OFF);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
        SmartDashboard.putNumber("LEncoder", Drivetrain.leftMotorA.getSelectedSensorPosition());
        SmartDashboard.putNumber("REncoder", Drivetrain.rightMotorA.getSelectedSensorPosition());

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    hatch.retain(); //Drivers are stupider than you think
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    boolean retainingHatch;
    boolean ejectionStatus;
    if(HatchSubsystem.retainer.get() == Value.kForward) {
      retainingHatch = true;
   } else {
   retainingHatch = false;
   }

   if(HatchSubsystem.eject.get() == Value.kForward) {
    ejectionStatus = true;
   } else {
    ejectionStatus = false;
  } 

    SmartDashboard.putBoolean("RetainerUp?", retainingHatch);
    SmartDashboard.putBoolean("Ejecting Hatch? ", ejectionStatus);
    
    Scheduler.getInstance().run();
    
  }

  public void teleopInit() {
    shooter.resetEncoders();
    compressor.stop();
    Drivetrain.setBrakeMode();
  
    // Returns all pistons to default positions when robot is first enabled
    hatch.release();
    hatch.retract();

  }
  
  @Override
  public void disabledInit(){ 
      
    Robot.oi.setLEDMode(LEDMode.OFF);
    Robot.oi.setPipeline(1);
    Robot.oi.setCamMode(CamMode.DRIVER);
    
    

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  
}
