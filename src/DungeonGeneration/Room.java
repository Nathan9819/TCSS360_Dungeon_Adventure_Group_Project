
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
    private Key key;
    private Portal portal;
    private TrapDoor trapDoor;
    private Potion potion;
    private String roomContents;
    private Room north, east, south, west;
    private Point coords;
    private String roomCode;
    private boolean visited, occupied, stairs;

    /**
     * This is the DungeonGeneration.Room constructor. It receives a string for the room's contents and a point for the
     * room's coordinates. It also instantiates the boolean "visited" as false.
     *
     * @param theContents The contents of the room
     * @param theCoords   The coordinates of the room
     */
    public Room(final String theContents, final Point theCoords) {
        roomContents = theContents;
        coords = theCoords;
        visited = false;
    }

    public void setMonster(final Monster theMonster) {
        if (!occupied) {
            monster = theMonster;
            occupied = true;
        } else {
            System.out.println("Room already contains: " + getItem().getName() + ", cannot add "+ monster.getName());
        }
    }

    public Monster getMonster() {
        return monster;
    }

    public void killMonster() {
        monster = null;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(final String theRoomCode) {
        roomCode = theRoomCode;
    }

    public String getRoomContents() {
        return roomContents;
    }

    public void setRoomContents(final String theContents) {
        roomContents = theContents;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(final Key theKey) {
        if (!occupied) {
            key = theKey;
            occupied = true;
        } else {
            if (hasItem()) {
                System.out.println("Room already contains: " + getItem().getName());
            } else {
                System.out.println("Room already contains: " + monster.getName());
            }
            System.out.println("This was generated by setKey");
        }
    }

    public void removeKey() {
        if (key != null) {
            key = null;
        }
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(final Potion thePotion) {
        if (!occupied) {
            potion = thePotion;
            occupied = true;
        } else {
            System.out.println("Room already contains: " + getItem().getName());
            System.out.println("This was generated by setPotion");
        }
    }

    public void removePotion() {
        if (potion != null) {
            potion = null;
        }
    }

    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getSouth() {
        return south;
    }

    public Room getWest() {
        return west;
    }

    public void setNorth(final Room theNorth) {
        north = theNorth;
    }

    public void setEast(final Room theEast) {
        east = theEast;
    }

    public void setSouth(final Room theSouth) {
        south = theSouth;
    }

    public void setWest(final Room theWest) {
        west = theWest;
    }

    public void setVisited() {
        visited = true;
    }

    public Entity getItem() {
        if (occupied) {
            if (key != null) {
                return key;
            } else if (potion != null) {
                return potion;
            }
        }
        return null;
    }

    public boolean hasItem() {
        if (occupied && monster == null) {
            return true;
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(final Point coords) {
        this.coords = coords;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(final Portal thePortal) {
        portal = thePortal;
    }

    public TrapDoor getTrapDoor() {
        return trapDoor;
    }

    public void setTrapDoor(final TrapDoor theTrapDoor) {
        trapDoor = theTrapDoor;
    }

}
