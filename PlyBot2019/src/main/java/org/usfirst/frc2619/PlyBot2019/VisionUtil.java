package org.usfirst.frc2619.PlyBot2019;

import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc2619.PlyBot2019.VisionData;
import org.usfirst.frc2619.PlyBot2019.VisionData.Target;

public class VisionUtil {
    
    // SerialPort constants
    public static final int DEFAULT_BAUD_RATE = 115200;
    public static final SerialPort.Port DEFAULT_SERIAL_PORT = SerialPort.Port.kUSB;

    // Camera constants
    public static final int CAM_WIDTH = 320;
    public static final int CAM_HEIGHT = 240;
    public static final int CAM_FPS = 60;

    // Calculation constats
    public static final int CENTER_X = (int)(CAM_WIDTH / 2);
    public static final int CENTER_Y = (int)(CAM_HEIGHT / 2);

    public static final double FOV_H = 65;
    public static final double FOV_V = 49.75;

    public static final double FOCAL_LENGTH_H = CAM_WIDTH / (2 * Math.tan(FOV_H / 2));
    public static final double FOCAL_LENGTH_V = CAM_HEIGHT / (2 * Math.tan(FOV_V / 2));

    /**
     * Creates a SerialPort object with the default Baud Rate and Port
     * 
     * @return a SerialPort object
     */
    public static SerialPort createSerialPort() {
        return createSerialPort(DEFAULT_BAUD_RATE, DEFAULT_SERIAL_PORT);
    }

    /**
     * Creates a SerialPort object with the given Baud Rate and Port parameters
     * 
     * @return a SerialPort object
     */
    public static SerialPort createSerialPort(int baudRate, SerialPort.Port port) {
        return new SerialPort(baudRate, port);
    }

    /**
     * Writes a value to a SerialPort and checks if wrote successfully
     */
    public static void pingSerialPort(SerialPort serialPort) {
        String msg = "ping";
        int bytes = 0;

        System.out.println("Pinging Serial Port...");

        if (serialPort == null) {
            System.out.println("Serial Port not valid");
        }
        else {
            bytes = serialPort.writeString(msg);
            System.out.println("Wrote " +  bytes + "/" + msg.length() + " bytes, cmd: " + msg);
        }
    }
    
    /**
     * Reads a value from a Serial Port
     * 
     * @return a String sent throught the Serial Port
     */
    public static String readSerialPort(SerialPort serialPort) {
        try {
            return serialPort.readString();
        }
        catch (Exception e) {
            System.out.println("Unable to read from Serial Port - port is not valid");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Reads a string and creates a packet of data
     * 
     * @return a VisionData object
     */
    public static VisionData parseMessage(String msg) {
        return VisionData.parseMessage(msg);
    }

    /**
     * Calculates horizontal angle from center to a coordinate.
     * 
     * @param x x coordinate
     * @return horizontal angle from center to a coordinate
     */
    public static double calculateYaw(int x) {
        return Math.atan( (CENTER_X - x) / FOCAL_LENGTH_H );
    }

    /**
     * Calculates the rough pixel coordinate given an angle
     * 
     * @param yaw horizontal angle
     * @return rough x coordinate of angle
     */
    public static int yawToX(double yaw) {
        return (int)( CENTER_X - FOCAL_LENGTH_H * Math.tan(yaw) );
    }

    /**
     * Calculates vertical angle from center to a coordinate.
     * 
     * @param y coordinate
     * @return vertical angle from center to a coordinate
     */
    public static double calculatePitch(int y) {
        return Math.atan( (CENTER_Y - y) / FOCAL_LENGTH_V );
    }

    /**
     * Calculates the rough pixel coordinate given an angle
     * 
     * @param yaw vertical angle
     * @return rough y coordinate of angle
     */
    public static int pitchToY(double pitch) {
        return (int)( CENTER_Y - FOCAL_LENGTH_V * Math.tan(pitch) );
    }

    /**
     * Calculates the distance from the camera directly to the target coordinates
     * 
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @param targetHeight The height of the target
     * @return Range from camera to target
     */
    public static double calculateRange(int x, int y, double targetHeight) {
        double height = targetHeight - getCamHeight();
        double yaw = calculateYaw(x);
        double pitch = calculatePitch(y);

        return Math.sqrt( Math.pow((height/Math.sin(pitch)), 2) + (Math.pow((height * Math.tan(yaw) / Math.tan(pitch)), 2)) );
    }

    /**
     * Calculates the distance from the camera directly to the target
     * 
     * @param target The target to find the range to
     * @param targetHeight The height of the target
     * @return Range from the robot to the target
     */
    public static double calculateRange(Target target, double targetHeight) {
        int x = target.getX();
        int y = target.getY();

        return calculateRange(x, y, targetHeight);
    }

    /**
     * Calculates the ground distance from the robot to the target coordinates
     * 
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @param targetHeight The height of the target
     * @return Ground distance from the robot to the target
     */
    public static double calculateHorizontalDistance(int x, int y, double targetHeight) {
        double height = targetHeight - getCamHeight();
        double yaw = calculateYaw(x);
        double pitch = calculatePitch(y);

        return Math.sqrt( Math.pow((height/Math.tan(pitch)), 2) + (Math.pow((height * Math.tan(yaw) / Math.tan(pitch)), 2)) );

    }

    /**
     * Calculates the ground distance from the robot to the target
     * 
     * @param target The target to find the distance to
     * @param targetHeight The height of the target
     * @return Ground distance from the robot to the target
     */
    public static double calculateHorizontalDistance(Target target, double targetHeight) {
        int x = target.getX();
        int y = target.getY();

        return calculateHorizontalDistance(x, y, targetHeight);
    }

    public static double getCamHeight() {
        return 2.0;
    }

}