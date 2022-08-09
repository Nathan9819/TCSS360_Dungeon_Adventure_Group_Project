
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates a 2d array of room objects representing a dungeon. This is achieved by rounds of expansion from a starting room.
 * Each room contains information regarding the connections with its neighbors, its location, and its contents, as well as its visibility
 * and if it's been visited. A room is denoted by "S" and a prospective room is denoted by "P". For each round of expansion, the 2d array is
 * iterated over. All rooms containing "P" are considered eligible for generation. These rooms are then turned into an "S" and between 0
 * and 3 the adjacent rooms become P's. Precautions are taken to insure rooms are not generated atop one another nor is there ever zero
 * rooms generated during a round of expansion.
 * @Author Nathan Mahnke
 */
public class DungeonGenerator {
    private int floor = 1;
    private static Room[][] initialDungeon;
    public static Room[][] finalDungeon;
    private int count = 0;

    /**
     * This is the DungeonGeneration.DungeonGenerator constructor. It instantiates the initial dungeon as well as the final dungeon.
     * The initial dungeon will hold only room objects with the contents "S" or "P" representing their current state.
     * This initial dungeon will be 12 by 12 and the final dungeon will be 2 times the size of the initial dungeon in
     * both dimensions. After the initialization of the arrays, various methods are called to generate and refine the dungeon.
     */
    public DungeonGenerator() {
        initialDungeon = new Room[7][7];
        finalDungeon = new Room[initialDungeon.length * 2][initialDungeon[0].length * 2];
        fillDungeon(initialDungeon);
        startGame();
        cleanUpDungeon();
        placeEntities();
        finalizeDungeon();
        displayDungeon(initialDungeon);
        displayDungeon(finalDungeon);
    }

    /**
     * The fillMaze function accepts an array of room objects and generates a room for all positions within the array. These
     * rooms have the contents " ".
     *
     * @param theRooms The array of rooms to be populated with empty rooms.
     */
    public void fillDungeon(Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                theRooms[i][j] = new Room(" ", new Point(i, j));
            }
        }
    }

    /**
     * The method startGame places the starting room and a prospective room to its east, the calls generate rooms
     * to begin the expansion process.
     */
    public void startGame() {
        initialDungeon[0][initialDungeon[0].length / 2].roomContents = "S";
        initialDungeon[0][initialDungeon[0].length / 2].south = initialDungeon[1][initialDungeon[0].length / 2];
        initialDungeon[0][initialDungeon[0].length / 2].coords = new Point(0, initialDungeon[0].length / 2);
        initialDungeon[1][initialDungeon[0].length / 2].roomContents = "P";
        initialDungeon[1][initialDungeon[0].length / 2].north = initialDungeon[0][initialDungeon[0].length / 2];
        initialDungeon[1][initialDungeon[0].length / 2].coords = new Point(1, initialDungeon[0].length / 2);

        int myMaxExpansions = 8;
        while (hasProspects() && count < myMaxExpansions) {
            generateRooms();
        }
        floor++;
    }

    /**
     * This method iterates over initialDungeon. It returns true if and when it finds a room with the contents "P".
     *
     * @return true if a room within initialDungeon contains "P" otherwise, false
     */
    public boolean hasProspects() {
        for (Room[] rooms : initialDungeon) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (rooms[col].roomContents.equals("P")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * The generateRoom method is responsible for the generation of rooms throughout the dungeon.
     * It iterates over all rooms with the contents "P" within initialDungeon. For each of these
     * rooms, it randomly decides how many exits the room should have (between 0 and 3), the attempts to
     * generate these rooms beginning at a randomly decided direction. For each direction it polls the
     * validMove method for the validity of the rooms generation and continues if allowed. All of these
     * new rooms have their contents set to "P" and the room from which the were generated has its contents
     * set to "S". This prepares the board for the next round of generations. As a small precaution, if less
     * than 3 rooms have been generated for a given expansion, while this remains true, every room will attempt
     * to generate at least 2 exits.
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
            initialDungeon[p.x][p.y].roomContents = "S";
//            spawnMonster(p.x, p.y);
//            initialDungeon[p.x][p.y].setDungeonCharacter(new Gremlin());
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

    public void placeEntities() {
        int myEnemyCount = 0, myEmptyCount = 0, myItemOCount = 0, myNextSpawn = 0;
        Random myRand = new Random();
        ArrayList<Point> myEmptyRooms = new ArrayList<>();
        ArrayList<Point> myRooms = getRooms();
        Point myStartRoom = new Point(0, initialDungeon[0].length / 2);
        for (Point p : myRooms) {
            if (p != myStartRoom) {
                myNextSpawn = myRand.nextInt(2);
                switch (myNextSpawn) {
                    case 0:
                        if (myEnemyCount < myRooms.size() / 3) {
                            int myMonster = myRand.nextInt(6);
                            switch (myMonster) {
                                case 0, 1, 2 -> initialDungeon[p.x][p.y].setMonster(new Gremlin());
                                case 3, 4 -> initialDungeon[p.x][p.y].setMonster(new Skeleton());
                                case 5 -> initialDungeon[p.x][p.y].setMonster(new Ogre());
                            }
                            myEnemyCount++;
                        }
                    case 1:
                        System.out.println("item would be spawned");
                        myItemOCount++;
                    case 2:
                        myEmptyCount++;
                        myEmptyRooms.add(p);
                }
            }
        }
        spawnKey(myEmptyRooms);
    }

    private void spawnKey(ArrayList<Point> theEmptyRooms) {
        Random myRand = new Random();
        final int myKey = myRand.nextInt(theEmptyRooms.size());
        int myCount = 0;
        for (Point p : theEmptyRooms) {
            if (myCount == myKey) {
                String myColor = "";
                switch (floor) {
                    case 0 -> myColor = "Blue";
                    case 1 -> myColor = "Red";
                    case 2 -> myColor = "Yellow";
                    case 3 -> myColor = "Green";
                }
                initialDungeon[p.x][p.y].setItem(new Key(myColor, floor));
                break;
            } else {
                myCount++;
            }
        }
    }

    private ArrayList<Point> getRooms() {
        ArrayList<Point> myArrayList = new ArrayList<>();
        for (int row = 0; row < initialDungeon.length; row++) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (initialDungeon[row][col].roomContents.equals("S")) {
                    myArrayList.add(new Point(row, col));
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
     * has its contents set to "S" and the room in the proper direction given the integer has its
     * contents set to "P". As well, these two rooms become linked via references to each other
     * stored within the room object. This is used for later traversal of the dungeon.
     *
     * @param thePoint         The coordinates of the room from which the new room is to be generated
     * @param theDirection The direction from the original room which the new room is to be generated
     */
    public void createRoom(Point thePoint, int theDirection) {
        if (theDirection == 0) {
            initialDungeon[thePoint.x][thePoint.y].north = initialDungeon[thePoint.x - 1][thePoint.y];
            initialDungeon[thePoint.x - 1][thePoint.y].roomContents = "P";
            initialDungeon[thePoint.x - 1][thePoint.y].south = initialDungeon[thePoint.x][thePoint.y];
        } else if (theDirection == 1) {
            initialDungeon[thePoint.x][thePoint.y].east = initialDungeon[thePoint.x][thePoint.y + 1];
            initialDungeon[thePoint.x][thePoint.y + 1].roomContents = "P";
            initialDungeon[thePoint.x][thePoint.y + 1].west = initialDungeon[thePoint.x][thePoint.y];
        } else if (theDirection == 2) {
            initialDungeon[thePoint.x][thePoint.y].south = initialDungeon[thePoint.x + 1][thePoint.y];
            initialDungeon[thePoint.x + 1][thePoint.y].roomContents = "P";
            initialDungeon[thePoint.x + 1][thePoint.y].north = initialDungeon[thePoint.x][thePoint.y];
        } else if (theDirection == 3) {
            initialDungeon[thePoint.x][thePoint.y].west = initialDungeon[thePoint.x][thePoint.y - 1];
            initialDungeon[thePoint.x][thePoint.y - 1].roomContents = "P";
            initialDungeon[thePoint.x][thePoint.y - 1].east = initialDungeon[thePoint.x][thePoint.y];
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
    public boolean validMove(Room theRoom, int theDirection) {
        // If the move is north
        if (theDirection == 0 && theRoom.coords.x - 1 >= 0) {
            return initialDungeon[theRoom.coords.x - 1][theRoom.coords.y].roomContents.equals(" ");
        // If the move is east
        } else if (theDirection == 1 && theRoom.coords.y + 1 < initialDungeon[0].length) {
            return initialDungeon[theRoom.coords.x][theRoom.coords.y + 1].roomContents.equals(" ");
        // If the move is south
        } else if (theDirection == 2 && theRoom.coords.x + 1 < initialDungeon.length) {
            return initialDungeon[theRoom.coords.x + 1][theRoom.coords.y].roomContents.equals(" ");
        // If the move is west
        } else if (theDirection == 3 && theRoom.coords.y - 1 >= 0) {
            return initialDungeon[theRoom.coords.x][theRoom.coords.y - 1].roomContents.equals(" ");
        // DungeonGeneration.Room was on edge of board and thus could not move off it
        } else {
            return false;
        }
    }

    /**
     * The getProspects method iterates over the board and returns an arrayList of points which
     * represent the locations of all rooms which have the contents "P".
     *
     * @return An arrayList of all points which correspond to rooms which have the contents "P"
     */
    public List<Point> getProspects() {
        ArrayList<Point> myPoints = new ArrayList<>();
        for (int row = 0; row < initialDungeon.length; row++) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (initialDungeon[row][col].roomContents.equals("P")) {
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
                    initialDungeon[myDunRow][myDunCol].coords = new Point(i, j);
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


    /**
     * The displayDungeon method acts as debugging tool to print out a DungeonGeneration.Room[][] to the console.
     *
     * @param theRooms The DungeonGeneration.Room[][] to be printed
     */
    public void displayDungeon(Room[][] theRooms) {
        System.out.print("\t ");
        for(int i = 0; i < theRooms.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        for (int i = 0; i < theRooms.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < theRooms[0].length; j++) {
                System.out.print(theRooms[i][j].roomContents);
                System.out.print("   ");
            }
            System.out.print("\n");
        }
    }

    /**
     * The generateConnections method accepts a 2d array of DungeonGeneration.Room objects. It is designed to add
     * symbols in positions that are between two Rooms which are connected via references to
     * each other. It does this by iterating over the DungeonGeneration.Room[][] and adding symbols where applicable.
     *
     * @param theRooms The array to which connections between rooms should be added
     * @return         The provided array but with connections between all applicable Rooms
     */
    public Room[][] generateConnections(Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    if (i < theRooms.length - 1) {
                        if (theRooms[i][j].south == theRooms[i + 2][j]) {
                            theRooms[i + 1][j].roomContents = "|";
                        }
                    }
                    if (j < theRooms[0].length - 1) {
                        if (theRooms[i][j].east == theRooms[i][j + 2]) {
                            theRooms[i][j + 1].roomContents = "-";
                        }
                    }
                }
            }
        }
        return theRooms;
    }

    /**
     * The cleanUpDungeon method snips any connections between Rooms with the contents "P" and any other rooms. Rooms
     * marked "P" are only used in the generation process to denote rooms which could be generated and, at this point,
     * no longer serve purpose and are thus removed. This is done by iterating over the initial dungeon and setting
     * appropriate connections between rooms to null and setting the Rooms containing "P" to contain " ".
     */
    public void cleanUpDungeon() {
        for (int i = 0; i < initialDungeon.length; i++) {
            for (int j = 0; j < initialDungeon[0].length; j++) {
                if(initialDungeon[i][j].roomContents.equals("P")) {
                    if (initialDungeon[i][j].north != null) {
                        initialDungeon[i - 1][j].south = null;
                    } else if (initialDungeon[i][j].east != null) {
                        initialDungeon[i][j + 1].west = null;
                    } else if (initialDungeon[i][j].south != null) {
                        initialDungeon[i + 1][j].north = null;
                    } else if (initialDungeon[i][j].west != null) {
                        initialDungeon[i][j - 1].east = null;
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
    public void addRoomCodes(Room[][] theRooms) {
        StringBuilder myRoomExits = new StringBuilder();
        for (Room[] theRoom : theRooms) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (theRoom[j].roomContents.equals("S")) {
                    myRoomExits.append(theRoom[j].north == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].east == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].south == null ? 0 : 1);
                    myRoomExits.append(theRoom[j].west == null ? 0 : 1);
                    theRoom[j].roomCode = myRoomExits.toString();
                    myRoomExits.setLength(0);
                }
            }
        }
    }

//    public void placeMonsters(Room[][] theDungeon) {
//        for (int i = 0; i < theDungeon.length; i++) {
//            for (int j = 0; j < theDungeon[0].length; j++) {
//                if (theDungeon[i][j].roomContents.equals("S"))
//            }
//        }
//    }

    public Room[][] getDungeon() {
        return finalDungeon;
    }
}
