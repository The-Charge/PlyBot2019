package org.usfirst.frc2619.PlyBot2019;

import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc2619.PlyBot2019.VisionData;

public class VisionUtil {
    
    //SerialPort constants
    public static final int DEFAULT_BAUD_RATE = 115200;
    public static final SerialPort.Port DEFAULT_SERIAL_PORT = SerialPort.Port.kUSB;

    // Camera constants
    public static final int CAM_WIDTH = 320;
    public static final int CAM_HEIGHT = 240;
    public static final int CAM_FPS = 60;

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

        return new VisionData();
    }

}