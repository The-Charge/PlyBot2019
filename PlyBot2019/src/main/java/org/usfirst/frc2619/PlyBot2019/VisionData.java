package org.usfirst.frc2619.PlyBot2019;

import java.util.ArrayList;


public class VisionData {
    private static final String MSG_SPLIT_CHAR = "/";
    private static final String DATA_SPLIT_CHAR = ",";

    private ArrayList<Target> targets;

    public VisionData(ArrayList<Target> targets) {
        this.targets = targets;
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

            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);

            camTargets.add(new Target(x, y));
        }

        VisionData tempVisionData = new VisionData(camTargets);
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
}