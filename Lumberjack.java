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
		if (tryChopTrees()){
			return; 
		}
		if (Nav.goToArchon()){
			return;
		}
		if (Nav.goTo(centerInitEnemyArchons))
			return; 
	}
	
	/**
	 * try to hurt actual enemies if needed 
	 * @return whether an attack was successful/performed 
	 * @throws GameActionException
	 */
	public static boolean tryAttack() throws GameActionException {
		for (RobotInfo bot : closeEnemies){
			if (rc.canStrike() && closeAllies.length==0){
				rc.strike();
				return true; 
			}
			Nav.tryMove(here.directionTo(bot.getLocation()));
		}
		return false; 
	}
	
	/**
	 * use canInteractWithTree()!
	 * @return
	 * @throws GameActionException
	 */
	public static boolean tryChopTrees() throws GameActionException {
		if (closeTrees.length==0)
			return false; 
		for (TreeInfo tree : closeTrees){
			if (tree.getTeam()!=allies && rc.canChop(tree.getID())){
				rc.chop(tree.getID());
				return true; 
			}
		}
		return false; 
	}
	
}// end Lumberjack class 
