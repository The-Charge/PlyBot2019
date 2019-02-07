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

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc2619.PlyBot2019.Robot;
import org.usfirst.frc2619.PlyBot2019.VisionData;
import org.usfirst.frc2619.PlyBot2019.VisionUtil;

/**
 *
 */
public class LockOnTargetDrive extends PIDCommand {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    String serialInfo;
    VisionData camInfo;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LockOnTargetDrive() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        super("LockOnTargetDrive", 1.0, 0.0, 0.0, 0.02);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(0.2);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        
        serialInfo = VisionUtil.readSerialPort(Robot.visionPort);
        camInfo = VisionUtil.parseMessage(serialInfo);

        if (camInfo.hasTargets()) {
            double targetYaw = camInfo.findClosestTargetYaw();
            return -targetYaw;
        } else
            return 0.0;
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        double leftSpeed = -Robot.oi.getLeftJoystick().getY();
        double rightSpeed = -Robot.oi.getRightJoystick().getY();

        if (camInfo.hasTargets()) {
            leftSpeed += output;
            rightSpeed -= output;

            // Adjust values to be with [-1, 1]
            if (Math.abs(leftSpeed) > 1 || Math.abs(rightSpeed) > 1) {
                double largerSpeed = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                leftSpeed /= largerSpeed;
                rightSpeed /= largerSpeed;
            }
        }

        Robot.driveTrain.run(leftSpeed, rightSpeed);
        
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveTrain.setPercentVBus();
        getPIDController().setSetpoint(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
