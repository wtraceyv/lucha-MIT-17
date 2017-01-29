package bc10Night;

import battlecode.common.*; 

/**
 * functions/micro are very primitive
 * these are pretty much just for testing purposes right now 
 * --when to shake trees for bullets??
 * --use a random three pick or incrementing int in radio broadcast 
 * to keep track and use modulo to assign soldiers to different roles?
 * 
 * @author Wallisan
 */

public class SoldierBot extends RootBot{
	
	static int roundsSpentStuck = 0; 
	static boolean royalBackupGuard = (int) (100 * Math.random())+1 < 10; 
	
	public static void go() throws GameActionException {
		while(true){
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
		if (tryAttack()){
			roundsSpentStuck = 0; 
			return; 
		}
		if (guardFriendlyArchon(royalBackupGuard)){
			roundsSpentStuck = 0; 
			return; 
		}
		if (Nav.goToArchon()){
			roundsSpentStuck = 0; 
			return; 
		}
		if (Nav.goTo(centerInitEnemyArchons)){
			roundsSpentStuck = 0; 
			return; 
		}
		roundsSpentStuck++; 
		if (roundsSpentStuck > 10 && closeTrees.length > 0 
								  && closeTrees[0].getTeam()!=allies
								  && rc.canFirePentadShot()){
			rc.firePentadShot(here.directionTo(closeTrees[0].getLocation()));
		}// chop our own damn trees if the lumberjacks can't ???
		
	}// end execute() 
	
	/**
	 * working on this right now -- helper methods will improve micro
	 * @return true if attack is overall successful
	 * @throws GameActionException
	 */
	public static boolean tryAttack() throws GameActionException {
		for (RobotInfo bot : closeEnemies){
			Direction target = here.directionTo(bot.getLocation()); 
			if (here.distanceTo(bot.getLocation())<=
					GameConstants.LUMBERJACK_STRIKE_RADIUS*3 
					&&rc.canFirePentadShot()){
				rc.firePentadShot(target);
			} else if (rc.canFireSingleShot()){
				rc.fireSingleShot(target);
			}
			Nav.goTo(bot.getLocation()); 
			return true;
		}
		return false; 
	}// end tryAttack() 
	
	/**
	 * check if any Archons currently need help, 
	 * and if so, go to help them (if you've been disignated 
	 * a guard) 
	 * @param isGuard, whether soldier has been assigned as a guard 
	 * @return
	 */
	public static boolean guardFriendlyArchon(boolean isGuard) throws GameActionException {
		if (!isGuard)
			return false; 
		if (!Comms.checkArchonsInDanger().equals(new MapLocation(0,0))
				&& Comms.checkArchonsInDanger()!=null ){
			Nav.goTo(Comms.checkArchonsInDanger());   
			return true; 
		}
		return false; 
	}
	
}// end SoldierBot class 

