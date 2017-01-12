package bc10Night;

import battlecode.common.*;

/**
 * -start working on methods for watering/caretaking, and 
 * simultaneously deciding when to make which bots and 
 * how many are needed
 * -work on handling bullets--when to shake trees, etc.
 * @author Wallisan
 *
 */

public class Gardener extends RootBot{
	public static void go(){
		while (true){
			try{
				Direction newDir = Nav.randomDirection();
				if (rc.canPlantTree(newDir)){
					rc.plantTree(newDir);
				}
				Nav.tryMove(Nav.randomDirection()); 
				Clock.yield();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go()
}
