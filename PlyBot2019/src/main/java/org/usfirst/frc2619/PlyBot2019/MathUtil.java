package org.usfirst.frc2619.PlyBot2019;

import org.usfirst.frc2619.PlyBot2019.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MathUtil {

	public static double calcDirection(double currentAmps, double desiredDegrees) {
		currentAmps = Math.toRadians(currentAmps);
		desiredDegrees = Math.toRadians(desiredDegrees);
		double current_x = Math.cos(currentAmps);
		double current_y = Math.sin(currentAmps);
		double desired_x = Math.cos(desiredDegrees);
		double desired_y = Math.sin(desiredDegrees);
		double direction = Math.signum(current_x * desired_y - desired_x * current_y);
		return direction;
	}	//return +1 if direction is CW, -1 if CCW - copied from SteamBot

    public static double adjSpeed(double speed) {
    	double dB = SmartDashboard.getNumber("Deadband", .1);
    	double root = SmartDashboard.getNumber("Root", 1);
    	double power = SmartDashboard.getNumber("Power", 3);
    	speed = MathUtil.deadband(speed, dB);
    	speed = MathUtil.delin(speed, dB , root, power);
    	return speed;
    }
	
	public static double deadband(double speed, double dead){
		if (-dead < speed && speed < dead) 
			return 0;
		else
			return speed;
	}//checks if speed is inbetween -dB and +dB then it should be set to zero
	
	public static double delin(double speed, double dead, double root, double pwr){
		double evn = (pwr/root) % 2;
		double invdB = Math.pow(1 - dead,-1);
		double cons = pwr/root;
		if (speed != 0) { //Makes sure deadband doesn't bypass the calculations
			if (speed > 0) //Speed is greater than zero and so there are no exceptions
				return Math.pow(invdB * (speed - dead), cons);
			else if (evn != 0) //Less than zero, checks for even power
				return Math.pow(invdB * (speed + dead), cons);
			else //To stay negative, a "-" must be put at the beginning to maintain negativity of speed
				return -Math.pow(invdB * (speed + dead), cons);
		}
		else return 0;
	}//
}