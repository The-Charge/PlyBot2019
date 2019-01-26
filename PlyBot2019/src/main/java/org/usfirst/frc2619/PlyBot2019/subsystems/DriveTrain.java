// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.PlyBot2019.subsystems;


import org.usfirst.frc2619.PlyBot2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import com.kauailabs.navx.frc.AHRS;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
	import edu.wpi.first.wpilibj.SPI.Port;
	import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	import com.ctre.phoenix.motorcontrol.ControlMode;
	import com.ctre.phoenix.motorcontrol.FeedbackDevice;
	import com.ctre.phoenix.motorcontrol.NeutralMode;
/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX leftFrontMotor;
    private WPI_TalonSRX leftRearMotor;
    private WPI_TalonSRX rightFrontMotor;
    private WPI_TalonSRX rightRearMotor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private final static double SPEED_P_CONSTANT = 0.3;
	private final static double SPEED_I_CONSTANT = 0.001;
	private final static double SPEED_D_CONSTANT = 0.0;
	private final static double SPEED_F_CONSTANT = 0.12;
    
    public double speedP = SPEED_P_CONSTANT;
	public double speedI = SPEED_I_CONSTANT;
	public double speedD = SPEED_D_CONSTANT;
	public double speedF = SPEED_F_CONSTANT;
	
	private final int MAX_TICKS_PER_SECOND = 8691;
    public final double TICKS_PER_FOOT = 4320;
    private final int TIMEOUT_MS = 10;
    
    public double MotionMagicP = 2;
    public double MotionMagicI = 0.001;
    public double MotionMagicD = 0;
    public double MotionMagicF = 0.72;
    public int MotionMagicAcceleration = 2500;
    public int MotionMagicVelocity = 8000;
    public int MotionMagicPIDIndex = 0;
    public int MotionMagicPIDSlot = 0;
    public double MotionMagicDistance;
    public double correctionR = 1.02;
    
    public final double TIMEOUT = 0.002;
    private final AHRS ahrs = new AHRS(Port.kMXP);
    public double turn_outer_speed;
	public final double TURN_OUTER_SPEED_DEFAULT = 0.5;
	public double turn_inner_speed;
	public final double TURN_INNER_SPEED_DEFAULT = -0.5;
    
    public static boolean isReversed = false;
    public boolean driveLocked = false;
    public boolean quarterSpeed = false;
    public boolean halfSpeed = false;

    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftFrontMotor = new WPI_TalonSRX(11);
        
        
        
        leftRearMotor = new WPI_TalonSRX(10);
        
        
        
        rightFrontMotor = new WPI_TalonSRX(2);
        
        
        
        rightRearMotor = new WPI_TalonSRX(3);
        
        
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new TankDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void driveTrainInit() {
    	leftFrontMotor.setNeutralMode(NeutralMode.Brake);
    	leftRearMotor.setNeutralMode(NeutralMode.Brake);
    	rightFrontMotor.setNeutralMode(NeutralMode.Brake);
    	rightRearMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void run(double l, double r) {
    	double leftSpeed = l;
    	double rightSpeed = r;
    	
    	if (quarterSpeed) {
	    		leftSpeed = l *.25;
	    		rightSpeed = r *.25;
    	}
    	else if (halfSpeed) {
    		leftSpeed = l *.5;
    		rightSpeed = r *.5;
    	}
    		
    	if (driveLocked) {
			double avSpeed = (leftSpeed + rightSpeed) / 2.0;
			leftSpeed = avSpeed;
			rightSpeed = avSpeed;
			if (!isReversed) {
				leftFrontMotor.set(leftSpeed);
				rightFrontMotor.set(rightSpeed);
			} 
			else {
				leftFrontMotor.set(-1 * leftSpeed);
				rightFrontMotor.set(-1 * rightSpeed);
			}
		} 
    	else if (!isReversed) {
			leftFrontMotor.set(leftSpeed);
			rightFrontMotor.set(rightSpeed);
		} 
    	else {
			leftFrontMotor.set(-1 * leftSpeed);
			rightFrontMotor.set(-1 * rightSpeed);
		}
    }

    public void stop() {
    	leftFrontMotor.set(0);
    	rightFrontMotor.set(0);
    }
    
    public void setPercentVBus() {
    	leftFrontMotor.set(ControlMode.PercentOutput, 0);
    	rightFrontMotor.set(ControlMode.PercentOutput, 0);
    }
    
    
    public void MotionMagicInit(double distance) {
    	//rightFrontMotor.follow(leftFrontMotor);
    	
    	MotionMagicDistance = distance;
    	leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	rightFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	
    	leftFrontMotor.config_kP(0, MotionMagicP, TIMEOUT_MS);
    	leftFrontMotor.config_kI(0, MotionMagicI, TIMEOUT_MS);
    	leftFrontMotor.config_kD(0, MotionMagicD, TIMEOUT_MS);
    	leftFrontMotor.config_kF(0, MotionMagicF, TIMEOUT_MS);
    	
    	rightFrontMotor.config_kP(0, MotionMagicP, TIMEOUT_MS);
    	rightFrontMotor.config_kI(0, MotionMagicI, TIMEOUT_MS);
    	rightFrontMotor.config_kD(0, MotionMagicD, TIMEOUT_MS);
    	rightFrontMotor.config_kF(0, MotionMagicF, TIMEOUT_MS);
    	
    	leftFrontMotor.configMotionAcceleration(MotionMagicAcceleration, TIMEOUT_MS);
    	leftFrontMotor.configMotionCruiseVelocity(MotionMagicVelocity, TIMEOUT_MS);
    	
    	rightFrontMotor.configMotionAcceleration((int)(correctionR*MotionMagicAcceleration), TIMEOUT_MS);
    	rightFrontMotor.configMotionCruiseVelocity((int)(correctionR*MotionMagicVelocity), TIMEOUT_MS);
    	
    	leftFrontMotor.setSelectedSensorPosition(0, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.setSelectedSensorPosition(0, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	MotionMagicDistance *= TICKS_PER_FOOT;
    	leftFrontMotor.set(ControlMode.MotionMagic, MotionMagicDistance);
    	rightFrontMotor.set(ControlMode.MotionMagic, correctionR*MotionMagicDistance);
    }
    
    public void MotionMagicInit(double distance, int backVelocity, int backAcceleration) {
    	MotionMagicDistance = distance;
    	leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	rightFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	
    	leftFrontMotor.config_kP(0, MotionMagicP, TIMEOUT_MS);
    	leftFrontMotor.config_kI(0, MotionMagicI, TIMEOUT_MS);
    	leftFrontMotor.config_kD(0, MotionMagicD, TIMEOUT_MS);
    	leftFrontMotor.config_kF(0, MotionMagicF, TIMEOUT_MS);
    	
    	rightFrontMotor.config_kP(0, MotionMagicP, TIMEOUT_MS);
    	rightFrontMotor.config_kI(0, MotionMagicI, TIMEOUT_MS);
    	rightFrontMotor.config_kD(0, MotionMagicD, TIMEOUT_MS);
    	rightFrontMotor.config_kF(0, MotionMagicF, TIMEOUT_MS);
    	
    	leftFrontMotor.configMotionAcceleration(backAcceleration, TIMEOUT_MS);
    	leftFrontMotor.configMotionCruiseVelocity(backVelocity, TIMEOUT_MS);
    	
    	rightFrontMotor.configMotionAcceleration((int)(correctionR*backAcceleration), TIMEOUT_MS);
    	rightFrontMotor.configMotionCruiseVelocity((int)(correctionR*backVelocity), TIMEOUT_MS);
    	
    	leftFrontMotor.setSelectedSensorPosition(0, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.setSelectedSensorPosition(0, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	MotionMagicDistance *= TICKS_PER_FOOT;
    	leftFrontMotor.set(ControlMode.MotionMagic, MotionMagicDistance);
    	rightFrontMotor.set(ControlMode.MotionMagic, correctionR*MotionMagicDistance);
    }
    
    public boolean isAtPIDDestination() {
		return (Math.abs(this.leftFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) - MotionMagicDistance) < 500) || (Math.abs(this.rightFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) - MotionMagicDistance) < 500);// || this.leftFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) < -MotionMagicDistance + 6000)
	}
    
    public void initSpeedMode() {    	
    	leftFrontMotor.set(ControlMode.Velocity, 0);
    	rightFrontMotor.set(ControlMode.Velocity, 0);
    	
    	leftFrontMotor.config_kP(1, speedP, TIMEOUT_MS);
    	leftFrontMotor.config_kI(1, speedI, TIMEOUT_MS);
    	leftFrontMotor.config_kD(1, speedD, TIMEOUT_MS);
    	leftFrontMotor.config_kF(1, speedF, TIMEOUT_MS);

    	rightFrontMotor.config_kP(1, speedP, TIMEOUT_MS);
    	rightFrontMotor.config_kI(1, speedI, TIMEOUT_MS);
    	rightFrontMotor.config_kD(1, speedD, TIMEOUT_MS);
    	rightFrontMotor.config_kF(1, speedF, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(1, 0);
    	rightFrontMotor.selectProfileSlot(1, 0);
    }
    
    public void setSpeedPID(double setSpeed) {
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		SmartDashboard.putNumber("Current", getCurrent());
	}
    
    public double getCurrent() {
		return leftFrontMotor.getOutputCurrent();
	}
    
    public double getYaw() {
    	return ahrs.getYaw();
    }
    
    public void writeDashboardValues() {
    	SmartDashboard.putBoolean("Inverted",isReversed);
    }
}

