package dungeon;

import java.awt.Point;
import java.util.Random;

/**
 * The original "room" inner class from DungeonGenerator.
 * Has been moved outside of DungeonGenerator for easier use of Inheritance.
 * 
 * @author Colton Wickens
 * @version July 25, 2022
 */
public class Room {
    public String myRoomContents;
    /**
     * Contains all the neighbors of the room.
     * Index: Direction
     * 0: North
     * 1: East
     * 2: South
     * 3: West
     */
    public Room[] myNeighbors;
    public Point myCoords;
    private boolean myIsVisited;
    private boolean myHasHealthPotion;

    public Room(final String theContents, final Point theCoords) {
        myRoomContents = theContents;
        myCoords = theCoords;
        myNeighbors = new Room[4];
        myIsVisited = false;
        Random r = new Random();
        myHasHealthPotion = r.nextDouble() < 0.1;
    }
    public Room(final Room theOther) {
    	myRoomContents = theOther.myRoomContents;
    	myCoords = theOther.myCoords;
    	myNeighbors = theOther.myNeighbors.clone();
    	myIsVisited = false;
    	Random r = new Random();
    	myHasHealthPotion = r.nextDouble() < 0.1;
    }
    public void visit(final Player p) {
    	if (!myIsVisited) {
    		if (myHasHealthPotion) {
    			p.gainHealthPotion();
    		}
    		myIsVisited = true;
    	}
    }
    public boolean getVisited() {
    	return myIsVisited;
    }
    
}
