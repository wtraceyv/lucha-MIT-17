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
	private static int treesPlanted = 0; 
	private static int soldiersDispensed = 0;
	private static int scoutsDispensed = 0; 
	private static boolean needLumberjacks = false; 
	private static boolean needScouts = true;
	private static int born = round; 
	
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
	 * main method to execute other than update() in go() 
	 * --> keeps try/catch in Gardener loop less cluttered 
	 * @throws GameActionException
	 */
	public static void execute() throws GameActionException {
		treeGroupPhase();  
		tryToWaterTrees(); 
		dispenseBots();
		buyPoints(); 
	}
	
	/**
	 * some kind of system to periodically decide to buy victory points...
	 * cater to new system of changing price 
	 * --> later in match, I'll probably have a billion bullets to use 
	 * anyway; try to be efficient about this and not let bullets 
	 * overflow uselessly 
	 * @throws GameActionException
	 */
	public static void buyPoints() throws GameActionException {
		if (scoutsDispensed>2 && soldiersDispensed>2){
			if (round > 50 && rc.getTeamBullets() >= victoryPointCost*75){
				rc.donate(victoryPointCost * 75);
			} else if (round > 500 && rc.getTeamBullets() >= victoryPointCost*50){
				rc.donate(victoryPointCost * 50);
			} else if (round > 800 && rc.getTeamBullets() >= victoryPointCost * 25){
				rc.donate(victoryPointCost * 25); 
			} else if (rc.getTeamBullets() >= victoryPointCost * 10){
				rc.donate(victoryPointCost * 10); 
			}
		}
	}
	
	/**
	 * how to plant a tree if needed  
	 * -->use as boolean method? would it help? 
	 * @return
	 * @throws GameActionException
	 */
	public static void treeGroupPhase() throws GameActionException {
		// setup directions to plant trees in
        Direction negativeDir = here.directionTo(centerInitEnemyArchons);
        Direction[] plantingDirs = new Direction[]{Direction.getNorth(),Direction.getWest(),Direction.getSouth()};
//        float offset = (float) 45;
//        for (int i=7, j=0;i-->0;){
//            plantingDirs[j] = negativeDir.rotateLeftDegrees(offset);
//            offset += 45;
//            j++;
//        }
        // plant the trees
        for (Direction d : plantingDirs){
            if (rc.canPlantTree(d)){
                rc.plantTree(d);
                treesPlanted++;
            }
        }
		// planting more in moving formation? (testing only) 	
	}
	
	/**
	 * attempt to water trees if appropriate 
	 * 
	 * @return
	 * @throws GameActionException
	 */
	public static void tryToWaterTrees() throws GameActionException{
		if (rc.canWater()){
			for(TreeInfo tree : closeTrees){
				if (rc.canWater(tree.getID()) && tree.getHealth()<GameConstants.BULLET_TREE_MAX_HEALTH-GameConstants.WATER_HEALTH_REGEN_RATE){
					rc.water(tree.getID());
				}
			}
		}
	}
	
	/**
	 * decide whether to dispense certain bots 
	 * --> needLumberjacks is alternating between true/false, 
	 * --> so making of a lumberjack or soldier alternates for a gardener 
	 * --> at the moment...
	 * 
	 * @return boolean whatever 
	 * @throws GameActionException
	 */
	public static boolean dispenseBots() throws GameActionException{
		Direction newRand = Nav.randomDirection();
		if (rc.canBuildRobot(RobotType.SCOUT, newRand) && !needLumberjacks && scoutsDispensed<1){
			rc.buildRobot(RobotType.SCOUT, newRand);
			scoutsDispensed++;
		} 
		if (rc.canBuildRobot(RobotType.SOLDIER, newRand) && !needLumberjacks){
			rc.buildRobot(RobotType.SOLDIER, newRand);
			soldiersDispensed++; 
			needLumberjacks = true; 
			needScouts = true; 
			return true; 
		} else if (rc.canBuildRobot(RobotType.LUMBERJACK, newRand)) {
			rc.buildRobot(RobotType.LUMBERJACK, newRand);
			needLumberjacks = false; 
			return true; 
		}
		newRand = Nav.randomDirection(); 
		if (round > 500 && rc.canBuildRobot(RobotType.TANK, newRand)){
			rc.buildRobot(RobotType.TANK, newRand); 
			return true; 
		}
		return false;
	}
	
}// end Gardener class 
