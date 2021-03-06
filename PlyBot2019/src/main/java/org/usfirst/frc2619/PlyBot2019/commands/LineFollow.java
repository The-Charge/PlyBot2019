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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2619.PlyBot2019.Robot;
import org.usfirst.frc2619.PlyBot2019.subsystems.SensorBar;

import edu.wpi.first.wpilibj.DigitalInput;


/**
 *
 */
public class LineFollow extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    public DigitalInput[] sensArray; //One array to hold the sensors in this command
    private double doemem; //Degree of Error Memory - Remember the previous doe for the next cycle

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LineFollow(){

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        sensArray = new DigitalInput[5];
        for (int senspace = 0; senspace < 5; senspace++){ // Loading in all sensors into array seperate from sensorBar
            sensArray[senspace] = Robot.sensorBar.getSensor(senspace);
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        doemem = 9999;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        boolean[] sensbool = new boolean[5]; //Var to hold boolean values and be easily changed
        int ctr = 0; //self explanatory - used to count current sensors active
        int total = 0; //self explanatory - total of sensor assigned integer positions
        double doe = 9999; // Degree Of Error - calculated with total and ctr
        double leftspeed = 0; //Speed for left side of drive train
        double rightspeed = 0; //Speed for right side of drive train

        for (int x = 0; x < 5; x++){ //Get boolean values from digital input sensors
            sensbool[x] = sensArray[x].get();
            //sensbool[x] = false; //FOR TESTING ONLY!!!
        }

        for (int y = 0; y < 0; y++){ //Gathering data for doe
            if(sensbool[y]){
                total += y*1000;
                ctr++;
            }
        }
        
        if (ctr==0){
            total = -1; //Null sensor situation - way far left or right checker
            doe = 9999; //Ensure doe reads an impossible value
        } 
        else doe = (total / ctr) - 2000; //Calculate Degree of Error

        SmartDashboard.putNumber("Current Degree", doe);
        //doe = SmartDashboard.getNumber("Degree", 0); //FOR TESTING PURPOSES ONLY
        //If structure to change speed
        if (doe <= -2000){
            leftspeed = 0;
            rightspeed = .5;
            doemem = doe;
        }
        else if(doe <= -1500){
            leftspeed = .125;
            rightspeed = .5;
            doemem = doe;
        }
        else if(doe <= -1000){
            leftspeed = .4;
            rightspeed = .5;
            doemem = doe;
        }
        else if(doe <= -500){
            leftspeed = .45;
            rightspeed = .5;
            doemem = doe;
        }
        else if(doe <= 0){
            leftspeed = .5;
            rightspeed = .5;
            doemem = doe;
        }
        else if (doe <= 500){
            leftspeed = .5;
            rightspeed = .45;
            doemem = doe;
        }
        else if (doe <= 1000){
            leftspeed = .5;
            rightspeed = .4;
            doemem = doe;
        }
        else if(doe <= 1500){
            leftspeed = .5;
            rightspeed = .125;
            doemem = doe;
        }
        else if(doe <= 2000){
            leftspeed = .5;
            rightspeed = 0;
            doemem = doe;
        }
        else if(doemem == 9999 && doe == 9999){
            leftspeed = .5;
            rightspeed = .5;
        }
        else if(doemem < 0 && doe == 9999){ //All sensors see nothing, check last value
            leftspeed = -.5;
            rightspeed = .5;
        }
        else if(doemem > 0 && doe == 9999){
            leftspeed = .5;
            rightspeed = -.5;
        }
        else{
            leftspeed = 0;
            rightspeed = 0;
        }

        Robot.driveTrain.initSpeedMode();
        Robot.driveTrain.setIndivSpeedPID(leftspeed, -rightspeed);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
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
