
import java.awt.*;

/**
 * The DungeonGeneration.Room class is used to store information regarding the rooms within the dungeon. They hold
 * references to their neighbors (if a connection exists), the room contents, the rooms coordinates,
 * the room's code, and if the room has been visited. This information is used throughout other methods
 * and helps achieve the proper display of rooms as well as other basic functions of the program.
 * @Author Nathan Mahnke
 */
public class Room {
    private Monster monster;
    public String roomContents;
    public Room north, east, south, west = null;
    public Point coords;
    public String roomCode;
    public boolean visited;

    /**
     * This is the DungeonGeneration.Room constructor. It receives a string for the room's contents and a point for the
     * room's coordinates. It also instantiates the boolean "visited" as false.
     *
     * @param theContents The contents of the room
     * @param theCoords   The coordinates of the room
     */
    public Room(String theContents, Point theCoords) {
        roomContents = theContents;
        coords = theCoords;
        visited = false;
    }

    public void setMonster(Monster theMonster) {
        monster = theMonster;
    }
    public String getDungeonCharacterName() {
        return monster.getName();
    }

    public Monster getMonster() {
        return monster;
    }

    public void killMonster() {
        monster = null;
    }
}
