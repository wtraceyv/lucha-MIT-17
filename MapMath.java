package bc10Night;

import battlecode.common.*;

public class MapMath {
	
	/**
	 * take a vector average of a bunch of MapLocations and 
	 * come up with a center to return 
	 * 
	 * @param unitsList holds all MapLocation representing whatever to 
	 * find center of 
	 * @return MapLocation center of all MapLocations given 
	 */
	public static MapLocation centerOfUnits(MapLocation[] unitsList) {
		MapLocation center =  new MapLocation(0,0); 
		for (MapLocation unit : unitsList){
			center = new MapLocation(center.x+unit.x, center.y+unit.y);  
		}
		float f = 1.0f / (float)unitsList.length; 
		center = new MapLocation((int)Math.round(f * center.x),
				(int)Math.round(f * center.y)); 
		return center; 
	}
}
