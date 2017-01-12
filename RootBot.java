package bc10Night;

import battlecode.common.*; 

/**
 * seems to work WAY better when it's not getting in the way!
 * -work on confirming the other classes see this stuff-->
 * like, periodically have an archon print its "here" to 
 * make sure it knows and it's updating 
 * -work on methods that detect urgent threats and 
 * take care of them (unless archon/gardener..figure that out)
 * @author Wallisan
 *
 */

public class RootBot {
	
	public static RobotController rc = RobotPlayer.rc; 
	public static RobotType me; 
	public static MapLocation here = rc.getLocation(); 
	public static Direction facing; 
	public static int round; 
	
	public static Team allies; 
	public static Team enemies; 
	public static MapLocation[] initAllyArchonLocs; 
	public static MapLocation[] initEnemyArchonLocs; 
	
	public static RobotInfo[] closeAllies; 
	public static RobotInfo[] closeEnemies; 
	public static RobotInfo[] urgentEnemies;
	
	/**
	 * redo all this stuff! 
	 * make a good base structure for all the 
	 * other robots!
	 */
}// end class RootBot