package org.usfirst.frc2619.PlyBot2019;

import java.util.ArrayList;

public class VisionData {
    private static final String MSG_SPLIT_CHAR = "/";
    private static final String DATA_SPLIT_CHAR = ",";

    private ArrayList<Target> targets;

    public VisionData() {
        this.targets = new ArrayList<Target>();
    }

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

    public String toString() {
        String output = "";

        for (Target target : targets) {
            output += "(" + target.getX() + "," + target.getY() + "), ";
        }

        return output;
    }

    /**
     * Parses a string from the camera to create a VisionData object
     * 
     * @return a new VisionData object
     */
    public static VisionData parseMessage(String msg) {
        msg = msg.trim();
        
        if (msg.equals(""))
            return new VisionData();
        else {
            String[] msgParts = msg.split(DATA_SPLIT_CHAR);

            if (msgParts.length % 2 != 0) {
                // error occurred
                return new VisionData();
            }
            
            ArrayList<Target> targets = new ArrayList<Target>();

            for (int i = 0; i < msgParts.length-1; i += 2) {
                try {
                    int x = Integer.parseInt(msgParts[i].trim());
                    int y = Integer.parseInt(msgParts[i+1].trim());
                    
                    targets.add(new Target(x, y));
                } catch(Exception e) {
                    // error parsing string to int
                    System.out.println("Error parsing string to integer");
                    return new VisionData();
                }
            }

            return new VisionData(targets);
        }
        
        /*
        if (msg.equals("")) {
            return new VisionData();
        }
        else {
            String[] targetPairs = msg.split(MSG_SPLIT_CHAR);
            ArrayList<Target> targets = new ArrayList<Target>();

            for (String coordPair : targetPairs) {
                String[] coords = coordPair.split(DATA_SPLIT_CHAR);

                int x = 0;
                int y = 0;
                try {
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);

                } catch(Exception e) {
                    return new VisionData();
                }

                Target tempTarget = new Target(x, y);
                targets.add(tempTarget);
            }

            return new VisionData(targets);
        }
        */
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