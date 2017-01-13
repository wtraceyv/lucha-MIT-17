package bc10Night;

import battlecode.common.*;

/**
 * -start working on methods for watering/caretaking, and 
 * simultaneously deciding when to make which bots and 
 * how many are needed
 * -work on handling bullets--when to shake trees, etc.
 * @author Wallisan
 *
 */

public class Gardener extends RootBot{
	
	private static int soldiersDispensed = 0;
	private static int treesPlanted = 0; 
	
	public static void go(){
		while (true){
			try{ 
				update(); 
				tryPlantTree(); 
				tryToWaterTrees(); 
				Nav.tryMove(Nav.randomDirection()); 
				Clock.yield();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go()
	
	public static boolean tryPlantTree() throws GameActionException {
		Direction newDir = Nav.randomDirection();
		if (treesPlanted < 5 && closeTrees.length <= 5 && rc.canPlantTree(newDir)){
			rc.plantTree(newDir);
			treesPlanted++; 
			System.out.println(me + " planted tree number " + treesPlanted); 
			System.out.println("I am at: " + here);
			return true; 
		}
		return false; 
	}
	
	public static boolean tryToWaterTrees() throws GameActionException{
		if (closeTrees.length>5){
			for(int i=closeTrees.length;i-->0;){
				if(closeTrees[i].getHealth()<GameConstants.BULLET_TREE_MAX_HEALTH && rc.canWater(closeTrees[i].getID())){
					rc.water(closeTrees[i].getID());
					break; 
				}
			}
		}
		return false; 
	}
	
	/**
	 * decide whether to dispense certain bots 
	 * 
	 * @return boolean whatever 
	 * @throws GameActionException
	 */
	public static boolean dispenseBots() throws GameActionException{
		Direction testDirection = Nav.randomDirection();
		if (soldiersDispensed < 2 && rc.canBuildRobot(RobotType.SOLDIER, testDirection)){
			rc.buildRobot(RobotType.SOLDIER, testDirection);
			soldiersDispensed++; 
			return true; 
		}
		return false; 
	}
}
