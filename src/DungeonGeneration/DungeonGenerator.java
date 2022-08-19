
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates a 2d array of room objects representing a dungeon. This is achieved by rounds of expansion from a starting room.
 * Each room contains information regarding the connections with its neighbors, its location, and its contents, as well as its visibility,
 * if it's been visited, and its contents. A room is denoted by "Room" and a prospective room is denoted by "Prospective". For each round
 * of expansion, the 2d array is iterated over. All rooms containing "Prospective" are considered eligible for generation. These rooms are
 * then turned into a "Room" and between 0 and 3 the adjacent rooms become "Prospective"s. Precautions are taken to insure rooms are not generated
 * atop one another nor is there ever zero rooms generated during a round of expansion. This does however sometimes result in dungeons too small
 * to place key components like keys and trap doors.
 *
 * @author Nathan Mahnke
 */
public class DungeonGenerator {
    private int floor;
    private static Room[][] initialDungeon;
    private static Room[][] finalDungeon;
    private int count;

    /**
     * This is the DungeonGenerator constructor. It instantiates the initial dungeon as well as the final dungeon.
     * The initial dungeon will hold only room objects with the contents "Room" or "Prospective" representing their current state.
     * This initial dungeon will be 7 by 7 and the final dungeon will be 2 times the size of the initial dungeon in
     * both dimensions. After the initialization of the arrays, various methods are called to generate and refine the dungeon.
     */
    public DungeonGenerator() {
        floor = -1;
        count = 0;
        generateNewDungeon();
    }

    /**
     * generateNewDungeon simply generates a dungeon floor, provided the floor is below 3. If the floor is 3 or higher, the
     * dungeon instead pivots to create a 1 by 1 2d array which contains a custom final boss.
     */
    public void generateNewDungeon() {
        floor++;
        System.out.println("Current floor: " + floor);
        if (floor < 3) {
            count = 0;
            initialDungeon = new Room[7][7];
            finalDungeon = new Room[initialDungeon.length * 2][initialDungeon[0].length * 2];
            fillDungeon(initialDungeon);
            startGame();
            cleanUpDungeon();
            finalizeDungeon();
            placeEntities();
        } else {
            finalDungeon = new Room[1][1];
            Ogre finalBoss = new Ogre();
            finalBoss.setSprite(new ImageIcon(getClass().getResource("DungeonGeneration/Assets/finalBoss.png")));
            finalBoss.setOffSetI(12);
            finalBoss.setOffSetI(70);
            finalBoss.setHeight(32);
            finalBoss.setWidth(56);
            finalBoss.setAtkSpd(3);
            finalBoss.setHit(0.8);
            finalBoss.setHP(250);
            finalBoss.setName("GIANT SCARY RAT");
            finalBoss.setDMGRange(25, 45);
            finalDungeon[0][0] = new Room("Room", new Point(0, 0));
            finalDungeon[0][0].setMonster(finalBoss);
        }
    }

    /**
     * The fillMaze function accepts an array of room objects and generates a room for all positions within the array. These
     * rooms have the contents " ".
     *
     * @param theRooms The array of rooms to be populated with empty rooms.
     */
    public void fillDungeon(final Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                theRooms[i][j] = new Room(" ", new Point(i, j));
            }
        }
    }

    /**
     * The method startGame places the starting room and a prospective room to its South, the calls generate rooms
     * to begin the expansion process.
     */
    public void startGame() {
        initialDungeon[0][initialDungeon[0].length / 2].setRoomContents("Room");
        initialDungeon[0][initialDungeon[0].length / 2].setSouth(initialDungeon[1][initialDungeon[0].length / 2]);
        initialDungeon[0][initialDungeon[0].length / 2].setCoords(new Point(0, initialDungeon[0].length / 2));
        initialDungeon[1][initialDungeon[0].length / 2].setRoomContents("Prospective");
        initialDungeon[1][initialDungeon[0].length / 2].setNorth(initialDungeon[0][initialDungeon[0].length / 2]);
        initialDungeon[1][initialDungeon[0].length / 2].setCoords(new Point(1, initialDungeon[0].length / 2));

        int myMaxExpansions = 8;
        while (hasProspects() && count < myMaxExpansions) {
            generateRooms();
        }
    }

    /**
     * This method iterates over initialDungeon. It returns true if and when it finds a room with the contents "Prospective".
     *
     * @return true if a room within initialDungeon contains "Prospective" otherwise, false
     */
    public boolean hasProspects() {
        for (Room[] rooms : initialDungeon) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (rooms[col].getRoomContents().equals("Prospective")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * The generateRoom method is responsible for the generation of rooms throughout the dungeon.
     * It iterates over all rooms with the contents "Prospective" within initialDungeon. For each of these
     * rooms, it randomly decides how many exits the room should have (between 0 and 3), then attempts to
     * generate these rooms beginning at a randomly decided direction. For each direction it polls the
     * validMove method for the validity of the rooms generation and continues if allowed. All of these
     * new rooms have their contents set to "Prospective" and the room from which they were generated has its contents
     * set to "Room". This prepares the board for the next round of generations. As a small precaution, if less
     * than 3 rooms have been generated for a given expansion, while this remains true, every room will attempt
     * to generate at least 2 exits. This is not a robust enough system to ensure a minimum size for the dungeon
     * and will need to be further developed.
     */
    public void generateRooms() {
        count++;
        Random myRand = new Random();
        int myRoomsGenerated = 0;
        for (Point p : getProspects()) {
            int myNumOfExits;
            myNumOfExits = myRand.nextInt(3);
            if (myRoomsGenerated < 3 && myNumOfExits < 2) {
                myNumOfExits = 2;
            }
            initialDungeon[p.x][p.y].setRoomContents("Room");
            int myRandDirection;
            myRandDirection = myRand.nextInt(3);
            while (myNumOfExits > 0) {
                if (validMove(initialDungeon[p.x][p.y], myRandDirection)) {
                    createRoom(p, myRandDirection);
                    myRoomsGenerated++;
                }
                myRandDirection++;
                if (myRandDirection > 3) {
                    myRandDirection = 0;
                }
                myNumOfExits--;
            }
        }
    }

    /**
     * placeEntities is responsible for populating the object stored within the Rooms of the dungeon.
     * It iterates over all rooms and randomly adds either a monster,
     */
    public void placeEntities() {
        ArrayList<Room> myRooms = spawnKeysAndStairs(getRooms());
        int myEnemyCount = 0, myItemOCount = 0, myNextSpawn = 0;
        Random myRand = new Random();
        for (Room r : myRooms) {
            if (r != finalDungeon[0][7]) {
                myNextSpawn = myRand.nextInt(3);
                switch (myNextSpawn) {
                    case 0:
                        if (myEnemyCount < myRooms.size() / 3) {
                            int myMonster = myRand.nextInt(6);
                            switch (myMonster) {
                                case 0, 1, 2 -> finalDungeon[r.getCoords().x][r.getCoords().y].setMonster(new Gremlin());
                                case 3, 4 -> finalDungeon[r.getCoords().x][r.getCoords().y].setMonster(new Skeleton());
                                case 5 -> finalDungeon[r.getCoords().x][r.getCoords().y].setMonster(new Ogre());
                            }
                            myEnemyCount++;
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        if (myItemOCount < myRooms.size() / 3) {
                            finalDungeon[r.getCoords().x][r.getCoords().y].setPotion(new Potion());
                            myItemOCount++;
                        }
                        break;
                }
            }
        }
    }

    private ArrayList<Room> spawnKeysAndStairs(final ArrayList<Room> theRooms) {
        Random myRand = new Random();
        int myKey = myRand.nextInt(theRooms.size());
        int myCount = 0;
        for (Room r : theRooms) {
            if (myCount == myKey) {
                String myColor = "";
                switch (floor) {
                    case 0 -> myColor = "Blue";
                    case 1 -> myColor = "Yellow";
                    case 2 -> myColor = "Green";
                }
                finalDungeon[r.getCoords().x][r.getCoords().y].setKey(new Key(myColor));
                theRooms.remove(r);
                break;
            } else {
                myCount++;
            }
        }

        myCount = 0;
        myKey = myRand.nextInt(theRooms.size());
        for (Room r : theRooms) {
            if (myCount == myKey) {
                finalDungeon[r.getCoords().x][r.getCoords().y].setKey(new Key("Grey"));
                theRooms.remove(r);
                break;
            } else {
                myCount++;
            }
        }
        myCount = 0;
        myKey = myRand.nextInt(theRooms.size());
        for (Room r : theRooms) {
            if (myCount == myKey) {
                finalDungeon[r.getCoords().x][r.getCoords().y].setTrapDoor(new TrapDoor(false));
                theRooms.remove(r);
                break;
            } else {
                myCount++;
            }
        }
        if (floor == 2) {
            myCount = 0;
            myKey = myRand.nextInt(theRooms.size());
            for (Room r : theRooms) {
                if (myCount == myKey) {
                    finalDungeon[r.getCoords().x][r.getCoords().y].setPortal(new Portal(false));
                    System.out.println("Portal spawned");
                    break;
                } else {
                    myCount++;
                }
            }
        }
        return theRooms;
    }

    private ArrayList<Room> getRooms() {
        ArrayList<Room> myArrayList = new ArrayList<>();
        for (int row = 0; row < finalDungeon.length; row++) {
            for (int col = 0; col < finalDungeon[0].length; col++) {
                if (finalDungeon[row][col].getRoomContents().equals("Room")) {
                    myArrayList.add(finalDungeon[row][col]);
                }
            }
        }
        return myArrayList;
    }

    /**
     * The createRoom method is used for the placement of a new room. It accepts a point to be
     * used as the coordinates and an integer to be used as the direction of generation. The
     * point represents the room from which the new room has been generated and the int is the
     * direction from the original room for this new room to be generated. The room at the point
     * has its contents set to "Room" and the room in the proper direction given the integer has its
     * contents set to "Prospective". As well, these two rooms become linked via references to each other
     * stored within the room object. This is used for later traversal of the dungeon.
     *
     * @param thePoint         The coordinates of the room from which the new room is to be generated
     * @param theDirection The direction from the original room which the new room is to be generated
     */
    public void createRoom(final Point thePoint, final int theDirection) {
        if (theDirection == 0) {
            initialDungeon[thePoint.x][thePoint.y].setNorth(initialDungeon[thePoint.x - 1][thePoint.y]);
            initialDungeon[thePoint.x - 1][thePoint.y].setRoomContents("Prospective");
            initialDungeon[thePoint.x - 1][thePoint.y].setSouth(initialDungeon[thePoint.x][thePoint.y]);
        } else if (theDirection == 1) {
            initialDungeon[thePoint.x][thePoint.y].setEast(initialDungeon[thePoint.x][thePoint.y + 1]);
            initialDungeon[thePoint.x][thePoint.y + 1].setRoomContents("Prospective");
            initialDungeon[thePoint.x][thePoint.y + 1].setWest(initialDungeon[thePoint.x][thePoint.y]);
        } else if (theDirection == 2) {
            initialDungeon[thePoint.x][thePoint.y].setSouth(initialDungeon[thePoint.x + 1][thePoint.y]);
            initialDungeon[thePoint.x + 1][thePoint.y].setRoomContents("Prospective");
            initialDungeon[thePoint.x + 1][thePoint.y].setNorth(initialDungeon[thePoint.x][thePoint.y]);
        } else if (theDirection == 3) {
            initialDungeon[thePoint.x][thePoint.y].setWest(initialDungeon[thePoint.x][thePoint.y - 1]);
            initialDungeon[thePoint.x][thePoint.y - 1].setRoomContents("Prospective");
            initialDungeon[thePoint.x][thePoint.y - 1].setEast(initialDungeon[thePoint.x][thePoint.y]);
        }
    }

    /**
     * The validMove method accepts a room and a direction. The method checks the position
     * in the direction provided and returns true if the room is empty with no connections,
     * otherwise it returns false.
     *
     * @param theRoom      The DungeonGeneration.Room from which to check
     * @param theDirection The direction in which to check
     * @return             True if the room in the given direction is empty, false otherwise
     */
    public boolean validMove(final Room theRoom, final int theDirection) {
        // If the move is north
        if (theDirection == 0 && theRoom.getCoords().x - 1 >= 0) {
            return initialDungeon[theRoom.getCoords().x - 1][theRoom.getCoords().y].getRoomContents().equals(" ");
        // If the move is east
        } else if (theDirection == 1 && theRoom.getCoords().y + 1 < initialDungeon[0].length) {
            return initialDungeon[theRoom.getCoords().x][theRoom.getCoords().y + 1].getRoomContents().equals(" ");
        // If the move is south
        } else if (theDirection == 2 && theRoom.getCoords().x + 1 < initialDungeon.length) {
            return initialDungeon[theRoom.getCoords().x + 1][theRoom.getCoords().y].getRoomContents().equals(" ");
        // If the move is west
        } else if (theDirection == 3 && theRoom.getCoords().y - 1 >= 0) {
            return initialDungeon[theRoom.getCoords().x][theRoom.getCoords().y - 1].getRoomContents().equals(" ");
        // DungeonGeneration.Room was on edge of board and thus could not move off it
        } else {
            return false;
        }
    }

    /**
     * The getProspects method iterates over the board and returns an arrayList of points which
     * represent the locations of all rooms which have the contents "Prospective".
     *
     * @return An arrayList of all points which correspond to rooms which have the contents "Prospective"
     */
    public List<Point> getProspects() {
        ArrayList<Point> myPoints = new ArrayList<>();
        for (int row = 0; row < initialDungeon.length; row++) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (initialDungeon[row][col].getRoomContents().equals("Prospective")) {
                    myPoints.add(new Point(row, col));
                }
            }
        }
        return myPoints;
    }

    /**
     * The method finalizeDungeon calls the method cleanUpDungeon then proceeds to copy the contents of
     * initialDungeon into finalDungeon, but doing so in a manor that leaves spaces, both vertically and
     * horizontally, between all elements of initialDungeon. This sets the finalDungeon array up to have
     * the connections between rooms added which happens when the method calls generateConnections.
     */
    public void finalizeDungeon() {
        addRoomCodes(initialDungeon);
        initialDungeon[0][initialDungeon[0].length / 2].killMonster();
        int myDunRow = 0;
        int myDunCol = 0;
        for (int i = 0; i < finalDungeon.length; i++) {
            for (int j = 0; j < finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1) {
                    finalDungeon[i][j] = initialDungeon[myDunRow][myDunCol];
                    initialDungeon[myDunRow][myDunCol].setCoords(new Point(i, j));
                    myDunCol++;
                    if (myDunCol == initialDungeon[0].length) {
                        myDunCol = 0;
                        myDunRow++;
                    }
                } else {
                    finalDungeon[i][j] = new Room(" ", new Point(i, j));
                }
            }
        }
        finalDungeon = generateConnections(finalDungeon);
    }


//    /**
//     * The displayDungeon method acts as debugging tool to print out a DungeonGeneration.Room[][] to the console.
//     *
//     * @param theRooms The DungeonGeneration.Room[][] to be printed
//     */
//    public void displayDungeon(Room[][] theRooms) {
//        System.out.print("\t ");
//        for(int i = 0; i < theRooms.length; i++) {
//            if (i < 10) {
//                System.out.print(" " + i + "  ");
//            } else {
//                System.out.print(i + "  ");
//            }
//        }
//        for (int i = 0; i < theRooms.length; i++) {
//            System.out.print(i + "\t| ");
//            for (int j = 0; j < theRooms[0].length; j++) {
//                System.out.print(theRooms[i][j].getRoomContents());
//                System.out.print("   ");
//            }
//            System.out.print("\n");
//        }
//    }

    /**
     * The generateConnections method accepts a 2d array of DungeonGeneration.Room objects. It is designed to add
     * symbols in positions that are between two Rooms which are connected via references to
     * each other. It does this by iterating over the DungeonGeneration.Room[][] and adding symbols where applicable.
     *
     * @param theRooms The array to which connections between rooms should be added
     * @return         The provided array but with connections between all applicable Rooms
     */
    public Room[][] generateConnections(final Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    if (i < theRooms.length - 1) {
                        if (theRooms[i][j].getSouth() == theRooms[i + 2][j]) {
                            theRooms[i + 1][j].setRoomContents("|");
                        }
                    }
                    if (j < theRooms[0].length - 1) {
                        if (theRooms[i][j].getEast() == theRooms[i][j + 2]) {
                            theRooms[i][j + 1].setRoomContents("-");
                        }
                    }
                }
            }
        }
        return theRooms;
    }

    /**
     * The cleanUpDungeon method snips any connections between Rooms with the contents "Prospective" and any other rooms. Rooms
     * marked "Prospective" are only used in the generation process to denote rooms which could be generated and, at this point,
     * no longer serve purpose and are thus removed. This is done by iterating over the initial dungeon and setting
     * appropriate connections between rooms to null and setting the Rooms containing "Prospective" to contain " ".
     */
    public void cleanUpDungeon() {
        for (int i = 0; i < initialDungeon.length; i++) {
            for (int j = 0; j < initialDungeon[0].length; j++) {
                if(initialDungeon[i][j].getRoomContents().equals("Prospective")) {
                    if (initialDungeon[i][j].getNorth() != null) {
                        initialDungeon[i - 1][j].setSouth(null);
                    } else if (initialDungeon[i][j].getEast() != null) {
                        initialDungeon[i][j + 1].setWest(null);
                    } else if (initialDungeon[i][j].getSouth() != null) {
                        initialDungeon[i + 1][j].setNorth(null);
                    } else if (initialDungeon[i][j].getWest() != null) {
                        initialDungeon[i][j - 1].setEast(null);
                    }
                    initialDungeon[i][j] = new Room(" ", new Point(i, j));
                }
            }
        }
    }

    /**
     * The addRoomCodes method is designed to encrypt the state of each room within the given 2d
     * array of room objects. Each room is evaluated regarding its connections to other rooms. Each
     * connection (or lack thereof) is stored in a 4-digit binary number beginning with the DungeonGeneration.Room's north
     * and proceeding clockwise. A 1 represents a connection to another DungeonGeneration.Room, while a 0 represents no
     * connection. This string is then saved to the DungeonGeneration.Room object for which it was generated for later
     * use by the GUI.
     *
     * @param theRooms The 2d array of rooms for which roomCodes should be generated.
     */
    public void addRoomCodes(final Room[][] theRooms) {
        StringBuilder myRoomExits = new StringBuilder();
        for (Room[] theRoom : theRooms) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (theRoom[j].getRoomContents().equals("Room")) {
                    myRoomExits.append(theRoom[j].getNorth() == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].getEast() == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].getSouth() == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].getWest() == null ? 0 : 1);
                    theRoom[j].setRoomCode(myRoomExits.toString());
                    myRoomExits.setLength(0);
                }
            }
        }
    }

    public Room[][] getDungeon() {
        return finalDungeon;
    }

    public int getFloor() {
        return floor;
    }
}
