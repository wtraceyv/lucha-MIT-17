package bc10Night;

import battlecode.common.*;

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
        boolean moved = false;
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
	 * navigate in some direction
	 * -MINE
	 */
	public static boolean tryMoveWalter(Direction dir) throws GameActionException{
		Direction right = dir.rotateLeftDegrees(40);  
		Direction left = dir.rotateLeftDegrees(40); 
		if (rc.canMove(dir)){
			rc.move(dir); 
			return true; 
		}
		if (rc.canMove(left)){
			rc.move(left); 
			return true; 
		}
		if (rc.canMove(right)){
			rc.move(right); 
			return true; 
		}
		return false; 
	}
	
	public static boolean toTree(){
		return false; 
	}
	/**
	 * simple attempt to make it to a destination, 
	 * perhaps later use A*?
	 * 
	 * @param target
	 * 
	 * -MINE?
	 */
	public static boolean goTo(MapLocation target) throws GameActionException {
		if (here.equals(target))
			return false; 
		Direction forward = here.directionTo(target); 
		MapLocation front = here.add(forward); // why? 
		if (rc.canMove(forward)){
			rc.move(forward); 
			return true; 
		}
		
		return false; 
	}
}
