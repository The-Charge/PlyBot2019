// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.PlyBot2019.commands;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc2619.PlyBot2019.MathUtil;
import org.usfirst.frc2619.PlyBot2019.Robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnNDegreesAbsolute extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_targetAngle;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private int flipCtr;
    private double currentAngle, motorSpeed, endThreshold;
    private boolean flip;   //true = clockwise, false = counterclockwise
    private AHRS gyro;
    private final static int FLIPCTR_THRESHOLD = 10;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public TurnNDegreesAbsolute(double targetAngle) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_targetAngle = targetAngle;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        motorSpeed = .75;
        endThreshold = 1;
        flipCtr = 0;
        gyro = new AHRS(Port.kMXP);
        this.setTimeout(5);
        if(MathUtil.calcDirection(currentAngle, m_targetAngle) == 1)
            flip = true;
        else
            flip = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        currentAngle = gyro.getAngle(); //sets the currentAngle to the gyro's current angle
        
        SmartDashboard.putNumber("Gyro Angle: ", currentAngle);
        //Checks which direction to go
        if(MathUtil.calcDirection(currentAngle, m_targetAngle) == 1)
        {
            Robot.driveTrain.run(motorSpeed, -motorSpeed);

            if(!flip)
                flipCtr++;
            flip = true;
        }
        else
        {
            Robot.driveTrain.run(-1 * motorSpeed, motorSpeed);

            if(flip)
                flipCtr++;
            flip = false;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //Tests whether the angle is within a certain range so it knows when it's done
        
        if((Math.abs(currentAngle - m_targetAngle) <= endThreshold || 360 - Math.abs(currentAngle - m_targetAngle) <= endThreshold) && flipCtr > FLIPCTR_THRESHOLD && this.isTimedOut())    //flipCtr threshold can be changed
            return true;
        
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
