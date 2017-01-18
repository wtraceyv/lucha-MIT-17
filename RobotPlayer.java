package bc10Night;

import battlecode.common.*;

/**
 * this should all be fine for now, 
 * update switch when other bots become relevant. 
 * WORK ON OTHER STUFF! :)
 * @author Wallisan
 *
 */

public class RobotPlayer {
	static RobotController rc;

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        RobotPlayer.rc = rc;
        // runs corresponding file for robot type
        switch (rc.getType()) {
            case ARCHON:
                ArchonBot.go();  
                break;
            case GARDENER:
                Gardener.go();
                break;
            case SOLDIER:
            	SoldierBot.go();
                break;
            case SCOUT: 
            	ScoutBot.go();
            	break; 
            case LUMBERJACK:
                break;
            default: 
            	System.out.println("Bad robot type");
            	break; 
        }
	}
}// end RobotPlayer class

