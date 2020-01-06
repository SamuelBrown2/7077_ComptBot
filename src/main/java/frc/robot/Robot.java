/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;





public class Robot extends TimedRobot {
  public static DifferentialDrive m_myRobot;
  public XboxController m_controller;
	private static final int kMotorPortLeftFront = 1; //PWM ports
  private static final int kMotorPortLeftRear = 0; 
  private static final int kMotorPortRightFront = 3;
  private static final int kMotorPortRightRear = 2;
  private static final int kMotorPortElevator1 = 4;
  public static final int kControllerPort = 0; //This is the USB port Driver Station says the controller is in

  double LDTMotor;
  double RDTMotor;
  double scaleFactor;
  Spark m_LeftFront = new Spark(kMotorPortLeftFront);
  Spark m_LeftRear = new Spark(kMotorPortLeftRear);
  Spark m_RightFront = new Spark(kMotorPortRightFront);
  Spark m_RightRear = new Spark(kMotorPortRightRear); 
  SpeedControllerGroup m_LeftGroup = new SpeedControllerGroup(m_LeftFront, m_LeftRear);
  SpeedControllerGroup m_RightGroup = new SpeedControllerGroup(m_RightFront, m_RightRear);
  Spark m_Elevator = new Spark(kMotorPortElevator1);
  DifferentialDrive m_drive = new DifferentialDrive(m_LeftGroup, m_RightGroup);
  double stick1val,stick2val;
  
  DoubleSolenoid frontSol = new DoubleSolenoid(0,1); //The numbers are the same as on the PCM
  DoubleSolenoid rearSol = new DoubleSolenoid(6,7); //The numbers are the same as on the PCM
  Compressor com = new Compressor();
  Timer timer;

  @Override
  public void robotInit() {
    m_controller = new XboxController(kControllerPort);
    scaleFactor = 0.5;
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    stick1val = -Math.pow(m_controller.getY(Hand.kLeft),3);
    stick2val = -Math.pow(m_controller.getY(Hand.kRight),3);

    //LDTMotor = -m_controller.getY(Hand.kLeft);
    //RDTMotor = m_controller.getY(Hand.kRight);
    m_drive.setSafetyEnabled(false);
    //m_drive.tankDrive(LDTMotor*scaleFactor, RDTMotor*scaleFactor);
    m_drive.tankDrive(stick1val,stick2val);
    
    //m_LeftFront.set(stick1val);
    //m_LeftRear.set(stick1val);
    //m_RightFront.set(stick2val);
    //m_RightRear.set(stick2val);
    
    
     if (m_controller.getBumper(Hand.kRight) == true)
     {
       m_Elevator.set(-.75);
       
    }
    if (m_controller.getBumper(Hand.kLeft) == true)
    {
      m_Elevator.set(.75);
    }
    else if (m_controller.getBumper(Hand.kRight) == false)
    {
        m_Elevator.set(0);
    }
    
    if (m_controller.getAButton()==true) //If A button pressed
    {
      frontSol.set(DoubleSolenoid.Value.kForward); //Make dosSol forward
    }
    if (m_controller.getBButton()==true) //If Y button pressed
    {
      frontSol.set(DoubleSolenoid.Value.kReverse); //Make dosSol reverse
    }
    if (m_controller.getXButton()==true) //If X button pressed
    {
      rearSol.set(DoubleSolenoid.Value.kForward); //Make tresSol forward
    }
    if (m_controller.getYButton()==true) //If B button pressed
    {
      rearSol.set(DoubleSolenoid.Value.kReverse); //Make tresSol reverse
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  
    stick1val = -Math.pow(m_controller.getY(Hand.kLeft),3);
    stick2val = Math.pow(m_controller.getY(Hand.kRight),3);

    //LDTMotor = -m_controller.getY(Hand.kLeft);
    //RDTMotor = m_controller.getY(Hand.kRight);
    m_drive.setSafetyEnabled(false);
    //m_drive.tankDrive(LDTMotor*scaleFactor, RDTMotor*scaleFactor);
    
    m_LeftFront.set(stick1val);
    m_LeftRear.set(stick1val);
    m_RightFront.set(stick2val);
    m_RightRear.set(stick2val);
    
    if(timer.getMatchTime() > 10.1 && timer.getMatchTime() < 10.9)
    {
      frontSol.set(DoubleSolenoid.Value.kReverse);
      rearSol.set(DoubleSolenoid.Value.kReverse);
    }  
    //System.out.println(timer.getMatchTime());

    
     if (m_controller.getBumper(Hand.kRight) == true)
     {
       m_Elevator.set(-.75);
       
    }
    if (m_controller.getBumper(Hand.kLeft) == true)
    {
      m_Elevator.set(.75);
    }
    else if (m_controller.getBumper(Hand.kRight) == false)
    {
        m_Elevator.set(0);
    }
    
    if (m_controller.getAButton()==true) //If A button pressed
    {
      frontSol.set(DoubleSolenoid.Value.kForward); //Make dosSol forward
    }
    if (m_controller.getBButton()==true) //If Y button pressed
    {
      frontSol.set(DoubleSolenoid.Value.kReverse); //Make dosSol reverse
    }
    if (m_controller.getXButton()==true) //If X button pressed
    {
      rearSol.set(DoubleSolenoid.Value.kForward); //Make tresSol forward
    }
    if (m_controller.getYButton()==true) //If B button pressed
    {
      rearSol.set(DoubleSolenoid.Value.kReverse); //Make tresSol reverse
    }
    
  }
}