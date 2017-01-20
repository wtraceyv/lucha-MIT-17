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
        return tryMove(dir,45,3);
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
	 * simple attempt to make it to a destination
	 * 
	 * @param target
	 * 
	 * doesn't really return correctly -- get more in depth here. 
	 */
	public static boolean goTo(MapLocation target) throws GameActionException {
		if (here.isWithinDistance(target, me.sensorRadius))
			return false; 
		Direction forward = here.directionTo(target);
		return tryMove(forward, 45, 3); 
	}
	
	/**
	 * basic dodge-a-bullet deal 
	 * @return false if failed dodge
	 */
	public static boolean dodge(){
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
	public static boolean avoidLumberjack() throws GameActionException{
		RobotInfo toAvoid; 
		for (RobotInfo enemy : closeEnemies){
			if (enemy.getType()==RobotType.LUMBERJACK){
				toAvoid = enemy; 
				Direction retreatDir = toAvoid.getLocation().directionTo(here); 
				float distance = here.distanceTo(enemy.getLocation()); 
				if (distance <= GameConstants.LUMBERJACK_STRIKE_RADIUS && Nav.tryMove(retreatDir, 45, 3)){
					return true;  
				}
			}
		}
		return false; 
	}
	public static void screensaver(){
		Direction initailDirection = randomDirection();
		if(rc.canMove(initialDirection)){
			rc.move(initialDirection);
		}
		else{
			trymove(initialDirection.rotateRightDegrees(90));
		}
	}
}// end Nav class 
