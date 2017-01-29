package bc10Night;

import battlecode.common.*;

/**
 * holds methods for directed movement/pathing
 * 
 * @author Wallisan
 */

public class Nav extends RootBot {
	
	static Direction randomDir = randomDirection(); 
	
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
		return tryMove(forward, 60, 3); 
	}
	
	public static boolean goTo(MapLocation target, float goalRadius) throws GameActionException {
		if (here.isWithinDistance(target, goalRadius))
			return false;
		Direction forward = here.directionTo(target); 
		return tryMove(forward, 70, 3); 
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
	
	/**
	 * testing now 
	 * @return
	 * @throws GameActionException
	 */
	public static boolean goToArchon() throws GameActionException {
		for (MapLocation possibleStorm : broadcastedEnemyArchons){
			if (possibleStorm!=null && !MapMath.floatEquals(possibleStorm.x, 0) 
					                && !MapMath.floatEquals(possibleStorm.x, 0) 
					                && Nav.goTo(possibleStorm)){
				return true; 
			}
		}
		return false; 
	}
	
	/** dodging and inactive screensavers() here */ 
	
	public static boolean dodge() throws GameActionException {
		return false; 
	}
	
	public static boolean sideDodge(BulletInfo bullet) throws GameActionException {
		Direction danger = bullet.dir;  
		return (tryMove(danger.rotateLeftDegrees(90),10,2)
			  ||tryMove(danger.rotateRightDegrees(90),10,2)); 
	}
	
	public static boolean circleDodge(BulletInfo bullet) throws GameActionException {
		return false; 
	}
	
	/**
	 * @param bullet
	 * @return whether said bullet is dangerous or not 
	 */
	public static boolean bulletIsDangerous(BulletInfo bullet){
		Direction danger = bullet.location.directionTo(here); 
		float gap = here.distanceTo(bullet.getLocation()); 
		float theta = danger.radiansBetween(bullet.dir); 
		if (Math.abs(theta)>Math.PI/2)
			return false; 
		float sideDist = (float) Math.abs(gap * Math.sin(theta)); 
		return (sideDist <= me.bodyRadius); 
	}// bulletIsDangerous end 
	
	/**
	 * Noah's ... weird implementation .. eee
	 * @param moving
	 * @return
	 * @throws GameActionException
	 */
	public static Direction screensaver(Direction moving) throws GameActionException{
        if(rc.canMove(moving)){
            rc.move(moving);
            return moving;
        }
        else return moving.rotateLeftDegrees(90);
    }
	
	/**
	 * mine, but not sure if operational 
	 * @throws GameActionException
	 */
	public static boolean screensaver() throws GameActionException {
		if (tryMove(randomDir)){
			return true; 
		} else {
			try{
			randomDir = randomDirection(); 
			screensaver(); 
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return false; 
	}
	
	/**
	 * will try soon 
	 * @param from
	 * @throws GameActionException
	 */
    public static void goAway(RobotType from) throws GameActionException{
        if(findFriendlyBot(from) != null){
            Direction away = findFriendlyBot(from).getLocation().directionTo(here);
            Nav.tryMove(away);
        }
    }
	
}// end Nav class 
