package bc10Night;

import battlecode.common.*; 

/**
 * -start organizing when to make which bots 
 * -setup a good pathing/escape system for archon to 
 * detect things and run away when needed
 * @author Wallisan
 *
 */

public class ArchonBot extends RootBot{
	
	static Direction rand = Nav.randomDirection(); 
	static Direction awayFromEnemies = centerInitEnemyArchons.directionTo(here); 
	static int gardenersHired = 0; 
	static int lastRoundCalledBackup = Integer.MAX_VALUE;
	
	public static void go() throws GameActionException {
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
	
	public static void execute() throws GameActionException {
		if (tryHireGardener())
			return;
		if (safeRun())
			return; 
		if (cramped())
			return; 
	}
	
	/**
	 * make better algo for making gardeners in a universal, 
	 * systematic fashion 
	 * @return
	 * @throws GameActionException
	 */
	public static boolean tryHireGardener() throws GameActionException{
		Direction towards = here.directionTo(centerInitEnemyArchons);  
		if (rc.canHireGardener(towards) 
		   && gardenersHired < 4){
			rc.hireGardener(towards); 
			gardenersHired++;   
			return true; 
		}
		return false; 
	}// end tryHireGardener() 
	
	/**
	 * idly move away from enemies...yeah...
	 * @return
	 * @throws GameActionException
	 */
	public static boolean safeRun() throws GameActionException {
		if (Nav.tryMove(awayFromEnemies, 70, 5))
			return true; 
		return false; 
	}
	
	/**
	 * Check whether I need to call backup; if so, periodically 
	 * send out signals for it to be picked up by guard soldiers. 
	 * If not, reset everything at some point so soldiers know not 
	 * to come. 
	 * @return
	 * @throws GameActionException
	 */
	public static boolean safetyCheck() throws GameActionException {
		if (closeEnemies.length>0 && Math.abs(round-lastRoundCalledBackup)>10){
			lastRoundCalledBackup = round; 
			Comms.callBackup(closeEnemies[0].getLocation());
			awayFromEnemies = closeEnemies[0].getLocation().directionTo(here); 
			return true; 
		} else {
			lastRoundCalledBackup = -1; 
			Comms.clear(rc.getID());
			return false;
		}
	}// end safetyCheck() 
	
	/**
	 * just get a damn gardener out there! 
	 * @return
	 * @throws GameActionException
	 */
	public static boolean cramped() throws GameActionException {
		if (closeTrees.length>6 && gardenersHired < 2){
			Direction[] urgentPlantDirs = {Direction.getNorth(),
										   Direction.getNorth().rotateLeftDegrees(45),
										   Direction.getWest(),
										   Direction.getWest().rotateLeftDegrees(45),
										   Direction.getSouth(), 
										   Direction.getSouth().rotateLeftDegrees(45),
										   Direction.getEast(), 
										   Direction.getEast().rotateLeftDegrees(45)}; 
			for (Direction possibleDir : urgentPlantDirs){
				if (rc.canBuildRobot(RobotType.GARDENER, possibleDir)){
					rc.buildRobot(RobotType.GARDENER, possibleDir);
					return true; 
				}
			}
		}
		return false; 
	}// end cramped() 
	
}// end ArchonBot class 
