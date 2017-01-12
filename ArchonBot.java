package bc10Night;

import battlecode.common.*; 

/**
 * -start organizing when to make which bots 
 * -setup a good pathing/escape system for archon to 
 * detect things and run away when needed
 * @author Wallisan
 *
 */

public class ArchonBot extends RootBot{
	
	public static void go() throws GameActionException {
		System.out.println("Hi?");
		while (true){
			Direction dir = Direction.getSouth();
			int gardenersHired = 0; 
			try{
				if (rc.canHireGardener(dir)&&gardenersHired<=2){
					rc.hireGardener(dir); 
					gardenersHired++; 
					System.out.println("Hired a gardener boi!");
				}
				else {
					Nav.tryMove(Nav.randomDirection()); 
				}
			}
			catch(Exception e){
				e.printStackTrace(); 
			}
			Clock.yield();
		}
	}// end go() 
}// end ArchonBot class 