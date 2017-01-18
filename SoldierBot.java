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
	
	public static void go() throws GameActionException {
		while(true){
			update(); 
			try{
				while (!here.isWithinDistance(centerInitEnemyArchons, (float)(me.sensorRadius*.75)))
					Nav.goTo(centerInitEnemyArchons); 
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go()
	
	public static void execute() throws GameActionException {
		if (tryAttack())
			return; 
		else if (closeEnemies.length>0){
			if (tryAttack())
				return; 
		}
		else {
			Nav.goTo(centerInitAllyArchons); 
			return; 
		}
	}
	
	/**
	 * working on this right now -- helper methods will improve micro
	 * @return true if attack is overall successful
	 * @throws GameActionException
	 */
	public static boolean tryAttack() throws GameActionException {
		if (closeEnemies.length>2){
			for (RobotInfo bot : closeEnemies){
				while (bot.getHealth()>0){
					Nav.maintainDistance(bot, (float) (me.sensorRadius*.75)); 
					Direction target = here.directionTo(bot.getLocation()); 
					rc.fireSingleShot(target);
				}
			}
		}
		return false; 
	}
}// end SoldierBot class 
