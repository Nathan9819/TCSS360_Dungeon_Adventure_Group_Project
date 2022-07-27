import java.awt.*;

public class Room {
    public String myRoomContents;
    public Room myNorth, myEast, mySouth, myWest = null;
    public Point myCoords;
    public String roomCode;
    public boolean visited;

    public Room(String theContents, Point theCoords) {
        myRoomContents = theContents;
        myCoords = theCoords;
        visited = false;
    }
}
