package bc10Night;

import battlecode.common.*;

public class ScoutBot extends RootBot{
	
	public static void go() throws GameActionException{
		update(); 
		while (true){
			try{
				execute();  
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go() 
	
	public static void execute() throws GameActionException {
		if(Nav.goTo(centerInitEnemyArchons))
			return; 
		Nav.goTo(centerInitAllyArchons);
	}
}
