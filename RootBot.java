package bc10Night;

import battlecode.common.*; 

/**
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
	public static float victoryPointCost = 7.5f + round*12.5f/3000; 
	
	public static Team allies = rc.getTeam(); 
	public static Team enemies = allies.opponent(); 
	public static MapLocation[] initAllyArchons = rc.getInitialArchonLocations(allies); 
	public static MapLocation[] initEnemyArchons = rc.getInitialArchonLocations(enemies);
	public static MapLocation[] broadcastedEnemyArchons = new MapLocation[3]; 
	public static MapLocation centerInitEnemyArchons = MapMath.centerOfUnits(initEnemyArchons); 
	public static MapLocation centerInitAllyArchons = MapMath.centerOfUnits(initAllyArchons); 
	
	public static RobotInfo[] closeAllies = rc.senseNearbyRobots(me.sensorRadius,allies); 
	public static RobotInfo[] closeEnemies = rc.senseNearbyRobots(me.sensorRadius,enemies); 
	public static RobotInfo[] urgentEnemies = rc.senseNearbyRobots(me.sensorRadius/2,enemies);
	public static TreeInfo[] closeTrees = rc.senseNearbyTrees(); 
	
	public static void update() throws GameActionException {
		here = rc.getLocation();
		round = rc.getRoundNum();
		broadcastedEnemyArchons = Comms.readArchonLocations(); 
		closeAllies = rc.senseNearbyRobots(me.sensorRadius,allies);
		closeEnemies = rc.senseNearbyRobots(me.sensorRadius,enemies);
		closeTrees = rc.senseNearbyTrees();
	}
	
	/**
	 * find and return specified type of robot so we can annoy it
	 * or run away accordingly
	 * @param type of robot needed to be found
	 * @return the last robot in the list of nearby bots of that type found
	 */
	public static RobotInfo findBot(RobotType type){
		for (int i = closeEnemies.length; i-->0;){
			if (closeEnemies[i].getType()==type)
				return closeEnemies[i];
		}
		return null;
	}
	
	
}// end class RootBot
