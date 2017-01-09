// start Eclipse project, name package this folder name for now

package Hypno-glory-9

import Battlecode.common.*; 

public class RobotPlayer extends Globals { // find out why the Globals and where it comes from? 
    
    /** 
     * this run method required? 
     * apparently for when a robot is instantiated. 
     */ 
    @SupressWarnings("unused") // I think this is safe w/o understanding 
    public static void run(RobotController rc) throws Exception {
        
        switch (parameter) {
        
            /** 
             * choose an ally type to spawn? 
             * then connect it to its file and run whatever
             * it does? 
             */ 
             
            default: 
                throw new Exception("No good robot type: " + rc.getType()); 
               
         }// end robot type switch 
         
    }// end run() 
}// end RobotPlayer class          
