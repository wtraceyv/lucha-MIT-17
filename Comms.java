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
	static int FIRST_FOUND_ARCHON_CHANNEL = 42;
	static int SECOND_FOUND_ARCHON_CHANNEL = 75; 
	static int THIRD_FOUND_ARCHON_CHANNEL = 85; 
	static int ARCHON_IN_DANGER_ONE = 145; 
	static int ARCHON_IN_DANGER_TWO = 168; 
	static int ARCHON_IN_DANGER_THREE = 112; 
	static int ENEMY_MOB_SMALL = 1649; 
	static int ENEMY_MOB_LARGE = 1947; 
	
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
		rc.broadcast((index+1), yCoord); 
		rc.broadcast((index+2), bot.getID());
	}
	
	public static void broadcastArchonDanger(MapLocation spot, int index) throws GameActionException {
		int xCoord = (int) spot.x; 
		int yCoord = (int) spot.y; 
		rc.broadcast(index, xCoord); 
		rc.broadcast(index+1, yCoord);
		rc.broadcast(index+2, rc.getID());
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
		for (int i = 0; i<=2; i++){
			// fill each MapLocation with coords from the channels, even if they are still 0, 0
			end[i] = new MapLocation(rc.readBroadcast(indices[i]),
					                 rc.readBroadcast(indices[i]+1)); 
		}
		return end; 
	}
	
	public static MapLocation checkArchonsInDanger() throws GameActionException {
		int[] IDs = {ARCHON_IN_DANGER_ONE+2,ARCHON_IN_DANGER_TWO+2,ARCHON_IN_DANGER_THREE+2}; 
		for (int ID : IDs){
			if (ID!=0){
				return new MapLocation(rc.readBroadcast(ID-2),rc.readBroadcast(ID-1)); 
			}
		}
		return null; 
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
										   rc.readBroadcast(startIndex+1)); 
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
		int ID = bot.getID(); 
		if (ID==rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL+2))
			return true; 
		if (ID==rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL+2))
			return true;
		if (ID==rc.readBroadcast(THIRD_FOUND_ARCHON_CHANNEL+2))
			return true; 
		return false;  
	}
	
	public static boolean alreadyCalledBackup() throws GameActionException {
		int ID = rc.getID(); 
		if (ID==rc.readBroadcast(ARCHON_IN_DANGER_ONE+2))
			return true; 
		if (ID==rc.readBroadcast(ARCHON_IN_DANGER_TWO+2))
			return true;
		if (ID==rc.readBroadcast(ARCHON_IN_DANGER_THREE+2))
			return true; 
		return false;  
	}
	
	/**
	 * @return earliest free Index to start broadcasting info about new archon 
	 * @throws GameActionException
	 */
	public static int nextArchonIndexFree() throws GameActionException {
		if (MapMath.floatEquals(rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL), 0))
			return FIRST_FOUND_ARCHON_CHANNEL; 
		else if (MapMath.floatEquals(rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL), 0))
			return SECOND_FOUND_ARCHON_CHANNEL; 
		else 
			return THIRD_FOUND_ARCHON_CHANNEL; 
	}
	
	public static int nextDangerIndexFree() throws GameActionException {
		if (rc.readBroadcast(ARCHON_IN_DANGER_ONE)==0)
			return ARCHON_IN_DANGER_ONE; 
		else if (rc.readBroadcast(ARCHON_IN_DANGER_TWO)==0)
			return ARCHON_IN_DANGER_TWO; 
		return ARCHON_IN_DANGER_THREE; 
	}
	
	/**
	 * store location of an arhon that has not before been spotted 
	 * @param found
	 * @param bot
	 * @throws GameActionException
	 */
	public static void broadcastNewArchonLocation(MapLocation found, RobotInfo bot) throws GameActionException {
		broadcastArchonLocation(found, nextArchonIndexFree(), bot);
	}
	
	public static void broadcastNewDangerCall(MapLocation danger) throws GameActionException {
		broadcastArchonDanger(here, nextDangerIndexFree());
	}
	
	/**
	 * update location of an already-seen archon; 
	 * only difference from broadcastArchonLocation is it 
	 * has to find where the archon is already stored first 
	 * @param found newfound loc of archon
	 * @param bot archon found so we can use its ID in decision making
	 */
	public static void updateArchonLocation(MapLocation found, RobotInfo bot) throws GameActionException {
		int[] IDs = new int[] {(FIRST_FOUND_ARCHON_CHANNEL+2),
                			   (SECOND_FOUND_ARCHON_CHANNEL+2),
                               (THIRD_FOUND_ARCHON_CHANNEL+2)};
		for (int possibleIndex : IDs){
			if (rc.readBroadcast(possibleIndex)==bot.getID()){
				broadcastArchonLocation(found, (possibleIndex-2), bot);
				return; 
			}
		}
	}
	
	public static void updateArchonDanger(MapLocation found) throws GameActionException {
		int[] IDs = new int[] {(ARCHON_IN_DANGER_ONE+2),
                			   (ARCHON_IN_DANGER_TWO+2),
                               (ARCHON_IN_DANGER_THREE+2)};
		for (int possibleIndex : IDs){
			if (rc.readBroadcast(possibleIndex)==rc.getID()){
				broadcastArchonDanger(found, (possibleIndex-2));
				return; 
			}
		}
	}
	
	/**
	 * I found an archon! --> 
	 * -- decide whether I've seen this archon already; 
	 * if yes, update its location 
	 * if no, broadcast its info in next available index (mostly Comms work)
	 */
	public static void screamArchon(RobotInfo found, MapLocation whereFound) throws GameActionException {
		if (archonAlreadySeen(found)){
			updateArchonLocation(whereFound, found);
			return; 
		} else {
			broadcastNewArchonLocation(whereFound, found);
		}
	}// end screamArchon()
	
	public static void callBackup(MapLocation danger) throws GameActionException {
		if (Comms.alreadyCalledBackup()){
			updateArchonDanger(danger); 
			return; 
		} else {
			broadcastNewDangerCall(danger);
		}
	}// end callBackup() 
	
	public static void clear(int safeBotID) throws GameActionException {
		int indexToClear = -1; 
		int[] possibleIDs = {ARCHON_IN_DANGER_ONE+2, 
							 ARCHON_IN_DANGER_TWO+2, 
							 ARCHON_IN_DANGER_THREE+2}; 
		for (int safeChannel : possibleIDs){
			if (rc.readBroadcast(safeChannel)==rc.getID()){
				indexToClear = safeChannel-2; 
			}
		}
		rc.broadcast(indexToClear, 0);
		rc.broadcast(indexToClear+1, 0);
		rc.broadcast(indexToClear+2, 0);
	}// end clear() 
	
//	public static boolean archonInDanger(){
//		int[] 
//		return false; 
//	}
	
	// These were for debugging purposes 
	public static void printArchonLocs() throws GameActionException {
		System.out.println("First store: " + rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL) + " " + rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL+1));
		System.out.println("Second store: " + rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL) + " " + rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL+1));
		System.out.println("Third store: " + rc.readBroadcast(THIRD_FOUND_ARCHON_CHANNEL) + " " + rc.readBroadcast(THIRD_FOUND_ARCHON_CHANNEL+1));
	}
	
	public static void printArchonIDs() throws GameActionException {
		System.out.println("First ID: " + rc.readBroadcast(FIRST_FOUND_ARCHON_CHANNEL+2));
		System.out.println("Second ID: " + rc.readBroadcast(SECOND_FOUND_ARCHON_CHANNEL+2));
		System.out.println("Third ID: " + rc.readBroadcast(THIRD_FOUND_ARCHON_CHANNEL+2));
	}
	
}// end Comms class 
