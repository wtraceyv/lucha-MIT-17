package bc10Night;

import battlecode.common.*; 

/**
 * functions/micro are very primitive
 * these are pretty much just for testing purposes right now 
 * 
 * @author Wallisan
 */

public class SoldierBot extends RootBot{
	
	public static void go(){
		while(true){
			update(); 
			try{
				Nav.goTo(target)goTo(initEnemyArchons); 
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}// end go()
	
	public static boolean chargeBase(){
		return false; 
	}
	
	public static boolean tryAttack(){
		return false; 
	}
}// end SoldierBot class 
