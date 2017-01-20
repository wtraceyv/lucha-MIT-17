package bc10Night;

import battlecode.common.*; 
/**
 * setup some preliminary methods for broadcasting archon locations!
 * @author Wallisan
 *
 */

public class Comms extends RootBot {
	
	// declare some channels for various purposes 
	static int FIRST_FOUND_ARCHON_CHANNEL = 478;
	static int SECOND_FOUND_ARCHON_CHANNEL = 9847; 
	static int THIRD_FOUND_ARCHON_CHANNEL = 245; 
	
	/**
	 * broadcast the location and ID of a found archon for other bots, 
	 * particularly idle scouts with nothing else to do
	 * 
	 * @param location of found archon 
	 * @param index to start storing location at in global comms 
	 * @throws GameActionException
	 */
	public static void broadcastArchonLocation(MapLocation found, int index, RobotInfo bot) throws GameActionException{
		// increase accuracy later? I don't really care right now
		int xCoord = (int) found.x; 
		int yCoord = (int) found.y; 
		rc.broadcast(index, xCoord); 
		rc.broadcast(index+1, yCoord); 
		rc.broadcast(index+2, bot.getID());
	}
	
	/**
	 * return a list of grabbed potential archon locations from radar, 
	 * even if they are still 0,0
	 * @return set of MapLocations indicating various archons with DIFFERENT IDs
	 * @throws GameActionException
	 */
	public static MapLocation[] readArchonLocations() throws GameActionException{
		MapLocation[] end = new MapLocation[3]; 
		int[] indices = new int[] {FIRST_FOUND_ARCHON_CHANNEL, SECOND_FOUND_ARCHON_CHANNEL, THIRD_FOUND_ARCHON_CHANNEL};
		for (int i = 0; i++<=2;){
			// fill each MapLocation with coords from the channels, even if they are still 0, 0
			end[i] = new MapLocation(rc.readBroadcast(indices[i]),rc.readBroadcast(indices[i])+1); 
		}
		return end; 
	}
	
}// end Comms class 
