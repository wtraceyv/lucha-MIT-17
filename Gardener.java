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
	
	// make method to reset these, and find out when that 
	// would be appropriate?
	private static int soldiersDispensed = 0;
	private static int treesPlanted = 0; 
	private static int scoutsDispensed = 0; 
	
	public static void go(){
		while (true){
			try{ 
				update(); 
				execute();  
				Clock.yield();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go()
	
	/**
	 * how to plant a tree if needed 
	 * -->use as boolean method? would it help? 
	 * @return
	 * @throws GameActionException
	 */
	public static void treeGroupPhase() throws GameActionException {
		// setup directions to plant trees in 
		Direction negativeDir = here.directionTo(centerInitEnemyArchons);
		Direction[] plantingDirs = new Direction[7]; 
		float offset = (float) 45; 
		for (int i=7, j=0;i-->0;){
			plantingDirs[j] = negativeDir.rotateLeftDegrees(offset); 
			offset += 45; 
			j++; 
		}
		// plant the trees
		for (Direction d : plantingDirs){
			if (rc.canPlantTree(d)){
				rc.plantTree(d);
				treesPlanted++; 
			}
			else 
				continue; 
		}
	}
	
	/**
	 * attempt to water trees if appropriate 
	 * 
	 * @return
	 * @throws GameActionException
	 */
	public static boolean tryToWaterTrees() throws GameActionException{
		if (closeTrees.length>0){
			for(int i=closeTrees.length;i-->0;){
				if(closeTrees[i].getHealth()<GameConstants.BULLET_TREE_MAX_HEALTH && rc.canWater(closeTrees[i].getID())){
					rc.water(closeTrees[i].getID());
				}
			}
			return true; 
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
		return false;
	}
	
	/**
	 * main method to execute other than update() in go() 
	 * --> keeps try/catch in Gardener loop less cluttered 
	 * @throws GameActionException
	 */
	public static void execute() throws GameActionException {
		Direction randTest = Nav.randomDirection(); 
		if (scoutsDispensed<1&&rc.canBuildRobot(RobotType.SCOUT, randTest)){
			rc.buildRobot(RobotType.SCOUT, randTest);
			scoutsDispensed++;
		}
		treeGroupPhase();  
		tryToWaterTrees(); 
		dispenseBots();
		if (rc.getTeamBullets()>10000){
			rc.donate(10000);
		}
	}
}
