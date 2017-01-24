package bc10Night;

import battlecode.common.*;

public class Lumberjack extends RootBot{

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
		if (tryAttack())
			return; 
//		if (Nav.goToArchon()){
//			return;
//		}
		if (Nav.goTo(centerInitEnemyArchons) && !tryChopTrees())
			return; 
		if (closeTrees.length>0 && tryChopTrees()){
			return; 
		}
	}
	
	/**
	 * try to hurt actual enemies if needed 
	 * @return whether an attack was successful/performed 
	 * @throws GameActionException
	 */
	public static boolean tryAttack() throws GameActionException {
		for (RobotInfo bot : closeEnemies){
			if (here.distanceTo(bot.getLocation())>GameConstants.LUMBERJACK_STRIKE_RADIUS){
				Nav.goTo(bot.getLocation());
			}
			if (rc.canStrike()){
				rc.strike();
				return true; 
			}
		}
		return false; 
	}
	
	/**
	 * use canInteractWithTree()!
	 * @return
	 * @throws GameActionException
	 */
	public static boolean tryChopTrees() throws GameActionException {
		for (TreeInfo tree : closeTrees){
			if (rc.canChop(tree.getID())){
				rc.chop(tree.getID());
				return true; 
			} else {
				Nav.goTo(tree.getLocation()); 
				return true; 
			}
		}
		return false; 
	}
	
}// end Lumberjack class 
