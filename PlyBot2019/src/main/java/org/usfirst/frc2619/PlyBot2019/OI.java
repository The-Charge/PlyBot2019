// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.PlyBot2019;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc2619.PlyBot2019.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton intakeInFastBtn;
    public JoystickButton intakeInSlowBtn;
    public JoystickButton intakeOutFastBtn;
    public JoystickButton intakeOutSlowBtn;
    public Joystick buttonBox;
    public JoystickButton extendBtn;
    public JoystickButton retractBtn;
    public Joystick leftJoystick;
    public Joystick rightJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        rightJoystick = new Joystick(1);
        
        leftJoystick = new Joystick(0);
        
        retractBtn = new JoystickButton(leftJoystick, 4);
        retractBtn.whileHeld(new Retract());
        extendBtn = new JoystickButton(leftJoystick, 3);
        extendBtn.whenPressed(new Extend());
        buttonBox = new Joystick(2);
        
        intakeOutSlowBtn = new JoystickButton(buttonBox, 4);
        intakeOutSlowBtn.whileHeld(new RunIntake(0.2));
        intakeOutFastBtn = new JoystickButton(buttonBox, 6);
        intakeOutFastBtn.whileHeld(new RunIntake(1));
        intakeInSlowBtn = new JoystickButton(buttonBox, 3);
        intakeInSlowBtn.whileHeld(new RunIntake(-0.2));
        intakeInFastBtn = new JoystickButton(buttonBox, 5);
        intakeInFastBtn.whileHeld(new RunIntake(-1));


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("TankDrive", new TankDrive());
        SmartDashboard.putData("XBoxDrive", new XBoxDrive());
        SmartDashboard.putData("TurnNDegreesAbsolute: angle", new TurnNDegreesAbsolute(90));
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive2Feet", new DriveXFeetMotionMagic(2, 0, 0));
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive1Foot", new DriveXFeetMotionMagic(1, 0, 0));
        SmartDashboard.putData("Extend", new Extend());
        SmartDashboard.putData("Retract", new Retract());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        SmartDashboard.putData("Turn to 45 Degrees Absolute", new TurnNDegreesAbsolute(45));
        SmartDashboard.putData("Turn to 90 Degrees Absolute", new TurnNDegreesAbsolute(90));
        SmartDashboard.putData("Turn to 180 Degrees Absolute", new TurnNDegreesAbsolute(180));
        SmartDashboard.putData("Turn to 340 Degrees Absolute", new TurnNDegreesAbsolute(340));
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getButtonBox() {
        return buttonBox;
    }

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

}
