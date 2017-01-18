package bc10Night;

import battlecode.common.*; 

/**
 * update works fine without exceptions now 
 * -maybe cut out direction
 * -work on methods that detect urgent threats and 
 * take care of them (unless archon/gardener..need escape sequence)
 * @author Wallisan
 *
 */

public class RootBot {
	
	public static RobotController rc = RobotPlayer.rc; 
	public static RobotType me = rc.getType(); 
	public static MapLocation here = rc.getLocation(); 
	public static Direction facing; // probably not *super* useful?
	public static int round = rc.getRoundNum(); 
	
	public static Team allies = rc.getTeam(); 
	public static Team enemies = allies.opponent(); 
	public static MapLocation[] initAllyArchons = rc.getInitialArchonLocations(allies); 
	public static MapLocation[] initEnemyArchons = rc.getInitialArchonLocations(enemies);
	public static MapLocation centerInitEnemyArchons = MapMath.centerOfUnits(initEnemyArchons); 
	public static MapLocation centerInitAllyArchons = MapMath.centerOfUnits(initAllyArchons); 
	
	public static RobotInfo[] closeAllies = rc.senseNearbyRobots(me.sensorRadius,allies); 
	public static RobotInfo[] closeEnemies = rc.senseNearbyRobots(me.sensorRadius,enemies); 
	public static RobotInfo[] urgentEnemies = rc.senseNearbyRobots(me.sensorRadius/2,enemies);
	public static TreeInfo[] closeTrees = rc.senseNearbyTrees();
	
	public static void update(){
		here = rc.getLocation();
		round = rc.getRoundNum();
		closeAllies = rc.senseNearbyRobots(me.sensorRadius,allies);
		closeEnemies = rc.senseNearbyRobots(me.sensorRadius,enemies);
		urgentEnemies = rc.senseNearbyRobots(me.sensorRadius/2,enemies);
		closeTrees = rc.senseNearbyTrees();
	}
	
	
}// end class RootBot
