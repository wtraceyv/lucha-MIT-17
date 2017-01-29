package bc10Night;

import battlecode.common.*;

public class TankBot extends RootBot {
	
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
		if (tryAttack()){
			return; 
		}
		if (Nav.goTo(centerInitEnemyArchons)){
			return; 
		}
		if (Nav.goToArchon()){
			return; 
		}
	}// end execute() 
	
	public static boolean tryAttack() throws GameActionException {
		RobotInfo target; 
		if (findBot(RobotType.ARCHON)!=null && rc.canFireSingleShot()){
			target = findBot(RobotType.ARCHON); 
			chase(target); 
			return true; 
		}
		return false; 
	}// end tryAttack() 
	
	public static void chase(RobotInfo bot) throws GameActionException {
		Direction targetDir = here.directionTo(bot.getLocation()); 
		if (rc.canFireTriadShot() && here.distanceTo(bot.getLocation())<GameConstants.LUMBERJACK_STRIKE_RADIUS*3){
			rc.fireTriadShot(targetDir);
		} else if (rc.canFireSingleShot()){
			rc.fireSingleShot(targetDir);
		}
		Nav.goTo(bot.getLocation(), me.sensorRadius * .8f); 
	}// end chase() 
	
}// end TankBot class 
