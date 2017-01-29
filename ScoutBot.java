package bc10Night;

import battlecode.common.*;

/**
 * scouts now go to initEnemyArchons and shoot at nearby Gardeners!
 * make better micro to CHASE gardeners, then patrol and grab/shake
 * neutral trees...
 * @author Wallisan
 *
 */

public class ScoutBot extends RootBot{ 
	
	static Direction previous = Nav.randomDirection(); 

	public static void go() throws GameActionException{
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
	 * actually runs whatever scout is supposed to do with other methods,
	 * keeps try/catch in go() uncluttered for debugging/organization
	 * purposes.
	 * @throws GameActionException
	 */
	public static void execute() throws GameActionException {
		if (findBot(RobotType.ARCHON)!=null){
			RobotInfo arc = findBot(RobotType.ARCHON); 
			Comms.screamArchon(arc, arc.getLocation());
			// shouldn't need to return yet 
		}
		if (idleAttacks())
			return; 
		if (Nav.avoidLumberjack())
			return; 
		if (Nav.goToArchon()){
			return;
		}
		if (Nav.goTo(centerInitEnemyArchons))
			return; 
//		if (Nav.screensaver())
//			return; 
	}// end execute() 

	/**
	 * no specific job is needed; find a bot to bother
	 * and bother it
	 * @throws GameActionException
	 */
	public static boolean idleAttacks() throws GameActionException{
		RobotInfo target;
		if (findBot(RobotType.GARDENER)!=null){
			target = findBot(RobotType.GARDENER);
			swarm(target);
			return true;
		}
//		else if (findBot(RobotType.LUMBERJACK)!=null){
//			target = findBot(RobotType.LUMBERJACK);
//			swarm(target);
//			return true;
//		}
		else if (findBot(RobotType.SOLDIER)!=null){
			target = findBot(RobotType.SOLDIER);
			swarm(target);
			return true;
		}
//		else if (findBot(RobotType.ARCHON)!=null && findBot(RobotType.SOLDIER)==null){
//			target = findBot(RobotType.ARCHON);
//			swarm(target);
//			return true;
//		}
		return false;
	}// end idleAttacks() 

	/**
	 * just sit and fire at specified enemy while you can,
	 * following if they get too far
	 * @param enemy targeted
	 * @throws GameActionException
	 */
	public static void swarm(RobotInfo enemy) throws GameActionException {
		Direction target = here.directionTo(enemy.getLocation());
		if (rc.canFireSingleShot()){
			rc.fireSingleShot(target);
		}
		Nav.goTo(enemy.getLocation(), me.sensorRadius*.7f); 
	}
	
}// end ScoutBot class
