package org.usfirst.frc2619.PlyBot2019;

import java.util.ArrayList;


public class VisionData {

    private static final String TIMESTAMP_ID = "TIME";
    private static final String TARGET_ID = "TARGET";
    private static final String PAIR_ID = "PAIR";
    private static final String MSG_SPLIT_CHAR = "|";
    private static final String DATA_SPLIT_CHAR = ",";

    private double timestamp;
    private ArrayList<Target> targets;

    public VisionData(double timestamp, ArrayList<Target> targets) {
        this.timestamp = timestamp;
        this.targets = targets;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    /**
     * Check if any vision targets were detected
     */
    public boolean hasTargets() {
        return targets.size() > 0;
    }

    /**
     * Returns the target whose x coordinate is closest to the center of the screen
     * Returns default target if no targets are found
     */
    public Target findClosestTargetCoord() {
        int numTargets = targets.size();

        if (numTargets == 0)
            return new Target();

        Target closestTarget = targets.get(0);
        for (int i = 1; i < numTargets; i++) {
            if (Math.abs( VisionUtil.CENTER_X - targets.get(i).x) <
                Math.abs(VisionUtil.CENTER_X - closestTarget.x)) {
                    closestTarget = targets.get(i);
                }
        }

        return closestTarget;
    }

    /**
     * Returns the yaw of the target closest to the center
     */
    public double findClosestTargetYaw() {
        return VisionUtil.calculateYaw(findClosestTargetCoord().getX());
    }

    /*
    public Target findClosestTargetDist() {

        int numTargets = targets.size();

        if (numTargets == 0)
            return new Target();

        Target closestTarget = targets.get(0);
        for (int i = 1; i < numTargets; i++) {
            if (Math.abs( VisionUtil.CENTER_X - targets.get(i).x) <
                Math.abs(VisionUtil.CENTER_X - closestTarget.x)) {
                    closestTarget = targets.get(i);
                }
        }

        return closestTarget;
    }

    public Target findClosestTargetRange() {

        int numTargets = targets.size();

        if (numTargets == 0)
            return new Target();

        Target closestTarget = targets.get(0);
        for (int i = 1; i < numTargets; i++) {
            if (VisionUtil.calculateRange(targets.get(i), true) <
                VisionUtil.calculateRange(closestTarget)) {
                    closestTarget = targets.get(i);
                }
        }

        return closestTarget;
    }
    */

    /**
     * Parses a string from the camera to create a VisionData object
     * 
     * @return a new VisionData object
     */
    public static VisionData parseMessage(String msg) {
        String[] msgData = msg.split(MSG_SPLIT_CHAR);

        double camTimestamp = 0.0;
        ArrayList<Target> camTargets = new ArrayList<Target>();
        
        for (String values : msgData) {
            String[] data = values.split(DATA_SPLIT_CHAR);
            String label = data[0];

            if (label == TIMESTAMP_ID)
                camTimestamp = Double.parseDouble(data[1]);

            else if (label == TARGET_ID) {
                int x = Integer.parseInt(data[1]);
                int y = Integer.parseInt(data[2]);
                Target tempTarget = new Target(x, y);

                camTargets.add(tempTarget);
            }

            else if (label == PAIR_ID) {
                int xLeft = Integer.parseInt(data[1]);
                int yLeft = Integer.parseInt(data[2]);
                int xRight = Integer.parseInt(data[3]);
                int yRight = Integer.parseInt(data[4]);
                Target tempTarget = new TargetPair(xLeft, yLeft, xRight, yRight);

                camTargets.add(tempTarget);
            }
        }

        VisionData tempVisionData = new VisionData(camTimestamp, camTargets);
        return tempVisionData;
    }

    /**
     * A target object to represent the reflective tape coordinates.
     * Instances of this super class represent single pieces of angled tape.
     */
    public static class Target {

        private int x;
        private int y;

        /**
         * @param x The x value of the target's center
         * @param y The y value of the target's center
         */

        public Target() {
            this.x = VisionUtil.CENTER_X;
            this.y = VisionUtil.CENTER_Y;
        }
        
        public Target(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    /**
     * TargetPair represents a pair of angled tape that outline a port.
     * Coordinates of each piece of tape, as well as the center of the two, are stored.
     */
    public static class TargetPair extends Target {

        private int xLeft;
        private int yLeft;
        private int xRight;
        private int yRight;

        /**
         * @param xLeft The x value of the left target
         * @param yLeft The y value of the left target
         * @param xRight The x value of the right target
         * @param yRight the y value of the right target
         */
        public TargetPair(int xLeft, int yLeft, int xRight, int yRight) {
            // Average the angles to find the center coordinates
            super( (int)(VisionUtil.calculateYaw(xLeft)+VisionUtil.calculateYaw(xRight))/2,
                   (int)(VisionUtil.calculatePitch(yLeft)+VisionUtil.calculatePitch(yRight))/2 );
            this.xLeft = xLeft;
            this.yLeft = yLeft;
            this.xRight = xRight;
            this.yRight = yRight;
        }

        public int getXLeft() {
            return xLeft;
        }

        public int getYLeft() {
            return yLeft;
        }

        public int getXRight() {
            return xRight;
        }

        public int getYRight() {
            return yRight;
        }
    }
}