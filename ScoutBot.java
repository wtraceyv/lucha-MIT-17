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

	static boolean firstStrikeMade = false;

	public static void go() throws GameActionException{
		update();
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
		if (!idleAttacks()){
			Nav.goTo(centerInitEnemyArchons);
		}
	}

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
		else if (findBot(RobotType.LUMBERJACK)!=null){
			target = findBot(RobotType.LUMBERJACK);
			swarm(target);
			return true;
		}
		else if (findBot(RobotType.SOLDIER)!=null){
			target = findBot(RobotType.SOLDIER);
			swarm(target);
			return true;
		}
		else if (findBot(RobotType.ARCHON)!=null && findBot(RobotType.SOLDIER)==null){
			target = findBot(RobotType.ARCHON);
			swarm(target);
			return true;
		}
		return false;
	}

	/**
	 * might move to RootBot or something?
	 *
	 * find and return specified type of robot so we can annoy it
	 * or run away accordingly
	 * @param type of robot needed to be found
	 * @return the last robot in the list of nearby bots of that type found
	 */
	public static RobotInfo findBot(RobotType type){
		for (int i = closeEnemies.length; i-->0;){
			if (closeEnemies[i].getType()==type)
				return closeEnemies[i];
		}
		return null;
	}

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
		Nav.goTo(enemy.getLocation());
	}

	/**
	 * fire at soldier, but maintain a certain distance so
	 * it's a little harder to swarm US with soldiers
	 * @param enemy
	 * @throws GameActionException
	 */
	public static void swarmSoldier(RobotInfo enemy) throws GameActionException {
		Direction target = here.directionTo(enemy.getLocation());
		if (rc.canFireSingleShot() && !Nav.avoidLumberjack()){
			rc.fireSingleShot(target);
		}
	}
}
