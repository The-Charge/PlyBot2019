package org.usfirst.frc2619.PlyBot2019;

import java.util.ArrayList;

public class VisionData {

    private static final int TIMESTAMP_ID = 0;
    private static final String SPLIT_CHAR = ":";

    private double timestamp;
    private ArrayList<Target> targets;

    public VisionData() {

    }

    /**
     * Parses a string from the camera to create a VisionData object
     * 
     * @return a new VisionData object
     */
    public static VisionData parseMessage(String msg) {
        String[] values = msg.split(SPLIT_CHAR);
        
        for (String targetInfo : values) {
            if 
        }

        return new VisionData();
    }

    private class Target {

        private int x_mid;
        private int y_mid;

        public Target(int x_mid, int y_mid) {
            this.x_mid = x_mid;
            this.y_mid = y_mid;
        }

    }
}