package bc10Night;

import battlecode.common.*;

/**
 * holds methods for directed movement/pathing
 * 
 * @author Wallisan
 */

public class Nav extends RootBot {
	
	static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
	/**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
	
	/**
	 * simple attempt to make it to a destination, 
	 * perhaps later use A*?
	 * 
	 * @param target
	 * 
	 * doesn't really return correctly -- get more in depth here. 
	 */
	public static boolean goTo(MapLocation target) throws GameActionException {
		if (here.isWithinDistance(target, me.sensorRadius))
			return false; 
		Direction forward = here.directionTo(target); 
		//MapLocation front = here.add(forward); // why? 
		if (rc.canMove(forward)){
			rc.move(forward); 
		}
		else if (closeTrees.length > 0 && !(me==RobotType.SCOUT)){
			Direction[] paths; 
			if (leftPrefer(target)){
				paths = new Direction[] {forward, forward.rotateLeftDegrees(90), forward.rotateRightDegrees(90)};
			}
			else 
				paths = new Direction[] {forward, forward.rotateRightDegrees(90), forward.rotateLeftDegrees(90)};
			for (int i = 0; i++<paths.length;){
				if (rc.canMove(paths[i])){
					rc.move(paths[i]);
					return true; 
				}
			}
		}
		return false; 
	}
	
	public static boolean leftPrefer(MapLocation target){
		Direction targetDir = here.directionTo(target); 
		MapLocation leftChoice = here.add(targetDir.rotateLeftDegrees(90)); 
		MapLocation rightChoice = here.add(targetDir.rotateRightDegrees(90)); 
		return (target.distanceTo(leftChoice) < target.distanceTo(rightChoice)); 
	}
	/**
	 * basic dodge-a-bullet deal 
	 * @return false if failed dodge
	 */
	public static boolean simpleDodge(){
		return false; 
	}
	
	/**
	 * try to maintain a certain fixed distance while doing 
	 * anything--particularly fighting; don't just get close 
	 * and stop, keep distance and ability to dodge
	 * 
	 * @param distance
	 * @return false if movement fails 
	 */
	public static boolean maintainDistance(RobotInfo bot, float distance) throws GameActionException{
		Direction negativeDir = new Direction(here,bot.getLocation()); 
		float leftRightOffset = 150; 
		Direction[] retreatDirs = {negativeDir.rotateLeftDegrees(leftRightOffset), negativeDir.rotateLeftDegrees(180), negativeDir.rotateRightDegrees(leftRightOffset)};
		boolean hasMoved = false; 
		
		while (here.isWithinDistance(bot.getLocation(), distance)){
			for (Direction dir : retreatDirs){
				if (rc.canMove(dir)){
					rc.move(dir);
					hasMoved = true; 
					return true; 
				}
			}
			if (!hasMoved){
				leftRightOffset -= 20;  
			}
		}
		return false; 
	}
	
}// end Nav class 
