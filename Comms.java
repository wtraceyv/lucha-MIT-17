package bc10Night;

import battlecode.common.*; 
/**
 * setup some preliminary methods for broadcasting archon locations!
 * 
 * in testing right now
 * @author Wallisan
 *
 */

public class Comms extends RootBot {
	
	// declare some channels for various purposes 
	// let's just assume there can only be 3 archons/team max 
	static int FIRST_FOUND_ARCHON_CHANNEL = 478;
	static int SECOND_FOUND_ARCHON_CHANNEL = 657; 
	static int THIRD_FOUND_ARCHON_CHANNEL = 245; 
	static int ARCHON_IN_DANGER_ONE = 223; 
	static int ARCHON_IN_DANGER_TWO = 212; 
	static int ARCHON_IN_DANGER_THREE = 226; 
	
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
		int[] indices = new int[] {FIRST_FOUND_ARCHON_CHANNEL, 
				           SECOND_FOUND_ARCHON_CHANNEL, 
				           THIRD_FOUND_ARCHON_CHANNEL};
		for (int i = 0; i++<=2;){
			// fill each MapLocation with coords from the channels, even if they are still 0, 0
			end[i] = new MapLocation(rc.readBroadcast(indices[i]),
					         rc.readBroadcast(indices[i])+1); 
		}
		return end; 
	}
	
	/**
	 * returns supposed stored MapLocation; should return (0,0) if nothing
	 * has been stored yet
	 * --> might need to convert to more accurate form; good enough now to test 
	 * @param startIndex first Index of where location should be stored 
	 * @param needID --> whether an ID is associated with the location (like a specific archon, etc.)
	 * @return read stored MapLocation
	 * @throws GameActionException
	 */
	public static MapLocation readLocation(int startIndex) throws GameActionException {
		MapLocation read = new MapLocation(rc.readBroadcast(startIndex),
				                   rc.readBroadcast(startIndex)+1); 
		return read; 
	}
	
	/**
	 * checks whether we've already recorded a certain archon's location 
	 * (maybe optimize this for any bot later? Am doubting it) 
	 * @param bot to be checked
	 * @return true if archon-being-checked's whereabouts have already been recorded
	 * @throws GameActionException
	 */
	public static boolean archonAlreadySeen(RobotInfo bot) throws GameActionException {
		if (bot.getID()==rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL+2)
	          ||bot.getID()==rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL+2)
	          ||bot.getID()==rc.readBroadcast(THIRD_FOUND_ARCHON_CHANNEL+2)) {
			return true; 
		}
		return false; 
	}
	
	/**
	 * @return earliest free Index to start broadcasting info about new archon 
	 * @throws GameActionException
	 */
	public static int nextArchonIndexFree() throws GameActionException {
		if (FIRST_FOUND_ARCHON_CHANNEL==0)
			return FIRST_FOUND_ARCHON_CHANNEL; 
		else if (SECOND_FOUND_ARCHON_CHANNEL==0)
			return SECOND_FOUND_ARCHON_CHANNEL; 
		else 
			return THIRD_FOUND_ARCHON_CHANNEL; 
	}
	
	/**
	 * store location of an arhon that has not before been spotted 
	 * @param found
	 * @param bot
	 * @throws GameActionException
	 */
	public static void storeNewArchonLocation(MapLocation found, RobotInfo bot) throws GameActionException {
		broadcastArchonLocation(found, nextArchonIndexFree(), bot);
	}
	
	/**
	 * update location of an already-seen archon; 
	 * only difference from broadcastArchonLocation is it 
	 * has to find where the archon is already stored first 
	 * @param found newfound loc of archon
	 * @param bot archon found so we can use its ID in decision making
	 */
	public static void updateArchonLocation(MapLocation found, RobotInfo bot) throws GameActionException {
		for (int possibleIndex : new int[] {FIRST_FOUND_ARCHON_CHANNEL+2,
				                    SECOND_FOUND_ARCHON_CHANNEL+2,
				                    THIRD_FOUND_ARCHON_CHANNEL+2}){
			if (rc.readBroadcast(possibleIndex)==bot.getID()){
				broadcastArchonLocation(found, possibleIndex, bot);
			} else {
				return; 
			}
		}
	}
	
}// end Comms class 
