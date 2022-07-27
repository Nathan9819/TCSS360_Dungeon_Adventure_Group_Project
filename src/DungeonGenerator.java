import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates a 2d array of room objects representing a dungeon. This is achieved by rounds of expansion from a starting room.
 * Each room contains information regarding the connections with its neighbors, its location, and its contents, as well as its visibility
 * and if its been visited. A room is denoted by "S" and a prospective room is denoted by "P". For each round of expansion, the 2d array is
 * iterated over. All rooms containing "P" are considered eligible for generation. These rooms are then turned into an "S" and between 0
 * and 3 the adjacent rooms become P's. Precautions are taken to insure rooms are not generated atop one another nor is there ever zero
 * rooms generated during a round of expansion.
 * @Author Nathan Mahnke
 */
public class DungeonGenerator {
    private static Room[][] initialDungeon;
    public static Room[][] finalDungeon;
    private final int MAX_EXPANSION_COUNT = 8;
    private int count = 0;

    /**
     * This is the DungeonGenerator constructor. It instantiates the initial dungeon as well as the final dungeon.
     * The initial dungeon will hold only room objects with the contents "S" or "P" representing their current state.
     * This initial dungeon will be 12 by 12 and the final dungeon will be 2 times the size of the initial dungeon in
     * both dimensions. After the initialization of the arrays, various methods are called to generate and refine the dungeon.
     */
    public DungeonGenerator() {
        initialDungeon = new Room[11][11];
        finalDungeon = new Room[initialDungeon.length * 2][initialDungeon[0].length * 2];

        fillMaze(initialDungeon);
        startGame();
        finalizeDungeon();
        addRoomCodes(finalDungeon);
        displayDungeon(initialDungeon);
        displayDungeon(finalDungeon);
    }

    /**
     * The fillMaze function accepts an array of room objects and generates a room for all positions within the array. These
     * rooms have the contents " ".
     */
    public void fillMaze(Room[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = new Room(" ", new Point(i, j));
            }
        }
    }

    /**
     * The method startGame places the starting room and a prospective room to its east, the calls generate rooms
     * to begin the expansion process.
     */
    public void startGame() {
        initialDungeon[initialDungeon[0].length / 2][0].myRoomContents = "S";
        initialDungeon[initialDungeon[0].length / 2][0].myEast = initialDungeon[initialDungeon[0].length / 2][1];
        initialDungeon[initialDungeon[0].length / 2][0].myCoords = new Point(initialDungeon[0].length / 2, 0);
        initialDungeon[initialDungeon[0].length / 2][1].myRoomContents = "P";
        initialDungeon[initialDungeon[0].length / 2][1].myWest = initialDungeon[initialDungeon[0].length / 2][0];
        initialDungeon[initialDungeon[0].length / 2][1].myCoords = new Point(initialDungeon[0].length / 2, 1);

        while (hasProspects() && count < MAX_EXPANSION_COUNT) {
            generateRooms();
        }
    }

    /**
     * This method iterates over initialDungeon. It returns true if and when it finds a room with the contents "P".
     *
     * @return true if a room within initialDungeon contains "P" otherwise, false
     */
    public boolean hasProspects() {
        for (int row = 0; row < initialDungeon.length; row++) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (initialDungeon[row][col].myRoomContents.equals("P")) {
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
        Random rand = new Random();
        int roomsGenerated = 0;
        for (Point p : getProspects()) {
            int numOfExits;
            numOfExits = rand.nextInt(3);
            if (roomsGenerated < 3 && numOfExits < 2) {
                numOfExits = 2;
            }
            initialDungeon[p.x][p.y].myRoomContents = "S";
            int randDirection;
            randDirection = rand.nextInt(3);
            while (numOfExits > 0) {
                if (validMove(initialDungeon[p.x][p.y], randDirection)) {
                    createRoom(p, randDirection);
                    roomsGenerated++;
                }
                randDirection++;
                if (randDirection > 3) {
                    randDirection = 0;
                }
                numOfExits--;
            }
        }
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
     * @param p         The coordinates of the room from which the new room is to be generated
     * @param direction The direction from the original room which the new room is to be generated
     */
    public void createRoom(Point p, int direction) {
        if (direction == 0) {
            initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myNorth = initialDungeon[p.x - 1][p.y];
            initialDungeon[p.x - 1][p.y].myRoomContents = "P";
            initialDungeon[p.x - 1][p.y].mySouth = initialDungeon[p.x][p.y];
        } else if (direction == 1) {
            initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myEast = initialDungeon[p.x][p.y + 1];
            initialDungeon[p.x][p.y + 1].myRoomContents = "P";
            initialDungeon[p.x][p.y + 1].myWest = initialDungeon[p.x][p.y];
        } else if (direction == 2) {
            initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].mySouth = initialDungeon[p.x + 1][p.y];
            initialDungeon[p.x + 1][p.y].myRoomContents = "P";
            initialDungeon[p.x + 1][p.y].myNorth = initialDungeon[p.x][p.y];
        } else if (direction == 3) {
            initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myWest = initialDungeon[p.x][p.y - 1];
            initialDungeon[p.x][p.y - 1].myRoomContents = "P";
            initialDungeon[p.x][p.y - 1].myEast = initialDungeon[p.x][p.y];
        }
    }

    /**
     * The validMove method accepts a room and a direction. The method checks the position
     * in the direction provided and returns true if the room is empty with no connections,
     * otherwise it returns false.
     *
     * @param room      The Room from which to check
     * @param direction The direction in which to check
     * @return          True if the room in the given direction is empty, false otherwise
     */
    public boolean validMove(Room room, int direction) {
        // If the move is north
        if (direction == 0 && room.myCoords.x - 1 >= 0) {
            return initialDungeon[room.myCoords.x - 1][room.myCoords.y].myRoomContents.equals(" ");
        // If the move is east
        } else if (direction == 1 && room.myCoords.y + 1 < initialDungeon[0].length) {
            return initialDungeon[room.myCoords.x][room.myCoords.y + 1].myRoomContents.equals(" ");
        // If the move is south
        } else if (direction == 2 && room.myCoords.x + 1 < initialDungeon.length) {
            return initialDungeon[room.myCoords.x + 1][room.myCoords.y].myRoomContents.equals(" ");
        // If the move is west
        } else if (direction == 3 && room.myCoords.y - 1 >= 0) {
            return initialDungeon[room.myCoords.x][room.myCoords.y - 1].myRoomContents.equals(" ");
        // Room was on edge of board and thus could not move off it
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
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < initialDungeon.length; row++) {
            for (int col = 0; col < initialDungeon[0].length; col++) {
                if (initialDungeon[row][col].myRoomContents.equals("P")) {
                    points.add(new Point(row, col));
                }
            }
        }
        return points;
    }

    /**
     * The method finalizeDungeon calls the method cleanUpDungeon then proceeds to copy the contents of
     * initialDungeon into finalDungeon, but doing so in a manor that leaves spaces, both vertically and
     * horizontally, between all elements of initalDungeon. This sets the finalDungeon array up to have
     * the connections between rooms added which happens when the method calls generateConnections.
     */
    public void finalizeDungeon() {
        cleanUpDungeon();
        int dunRow = 0;
        int dunCol = 0;
        for (int i = 0; i < finalDungeon.length; i++) {
            for (int j = 0; j < finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1) {
                    finalDungeon[i][j] = initialDungeon[dunRow][dunCol];
                    initialDungeon[dunRow][dunCol].myCoords = new Point(i, j);
                    dunCol++;
                    if (dunCol == initialDungeon[0].length) {
                        dunCol = 0;
                        dunRow++;
                    }
                } else {
                    finalDungeon[i][j] = new Room(" ", new Point(i, j));
                }
            }
        }
        finalDungeon = generateConnections(finalDungeon);
    }


    /**
     * The displayDungeon method acts as debugging tool to print out a Room[][] to the console.
     *
     * @param d The Room[][] to be printed
     */
    public void displayDungeon(Room[][] d) {
        System.out.print("\t ");
        for(int i = 0; i < d.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        for (int i = 0; i < d.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < d[0].length; j++) {
                System.out.print(d[i][j].myRoomContents);
                System.out.print("   ");
            }
            System.out.print("\n");
        }
    }

    /**
     *
     * @param theRooms
     * @return
     */
    public Room[][] generateConnections(Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    if (i < theRooms.length - 1) {
                        if (theRooms[i][j].mySouth == theRooms[i + 2][j]) {
                            theRooms[i + 1][j].myRoomContents = "|";
                        }
                    }
                    if (j < theRooms[0].length - 1) {
                        if (theRooms[i][j].myEast == theRooms[i][j + 2]) {
                            theRooms[i][j + 1].myRoomContents = "-";
                        }
                    }
                }
            }
        }
        return theRooms;
    }

    public void cleanUpDungeon() {
        for (int i = 0; i < initialDungeon.length; i++) {
            for (int j = 0; j < initialDungeon[0].length; j++) {
                if(initialDungeon[i][j].myRoomContents.equals("P")) {
                    if (initialDungeon[i][j].myNorth != null) {
                        initialDungeon[i - 1][j].mySouth = null;
                    } else if (initialDungeon[i][j].myEast != null) {
                        initialDungeon[i][j + 1].myWest = null;
                    } else if (initialDungeon[i][j].mySouth != null) {
                        initialDungeon[i + 1][j].myNorth = null;
                    } else if (initialDungeon[i][j].myWest != null) {
                        initialDungeon[i][j - 1].myEast = null;
                    }
                    initialDungeon[i][j] = new Room(" ", new Point(i, j));
                }
            }
        }
    }

    public void addRoomCodes(Room[][] d) {
        StringBuilder roomExits = new StringBuilder();
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && d[i][j].myRoomContents.equals("S")) {
                    roomExits.append(d[i][j].myNorth == null ? 0 : 1);
                    roomExits.append(d[i][j].myEast == null ? 0 : 1);
                    roomExits.append(d[i][j].mySouth == null ? 0 : 1);
                    roomExits.append(d[i][j].myWest == null ? 0 : 1);
                    finalDungeon[i][j].roomCode = roomExits.toString();
                    roomExits.setLength(0);
                }
            }
        }
    }
}
