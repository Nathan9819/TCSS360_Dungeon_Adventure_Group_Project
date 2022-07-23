import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {
    private static room[][] initialDungeon;
    public static room[][] finalDungeon;
//    public static ArrayList<Point> prospects;
    private final int MAX_EXPANSION_COUNT = 6;
    private int count = 0;

    public DungeonGenerator() {
        initialDungeon = new room[11][11];
        finalDungeon = new room[initialDungeon.length * 2][initialDungeon[0].length * 2];

        fillMaze();
        startGame();
//        cleanUpDungeon();
        finalizeDungeon();
        displayDungeon();
    }

    public void fillMaze() {
        for (int i = 0; i < initialDungeon.length; i++) {
            for (int j = 0; j < initialDungeon[0].length; j++) {
                initialDungeon[i][j] = new room(" ", new Point(i, j));
            }
        }
    }

    public void startGame() {
        initialDungeon[initialDungeon[0].length / 2][0].myRoomContents = "S";
        initialDungeon[initialDungeon[0].length / 2][0].myEast = initialDungeon[initialDungeon[0].length / 2][1];
        initialDungeon[initialDungeon[0].length / 2][0].myCoords = new Point(initialDungeon[0].length / 2, 0);
        initialDungeon[initialDungeon[0].length / 2][1].myRoomContents = "P";
        initialDungeon[initialDungeon[0].length / 2][1].myWest = initialDungeon[initialDungeon[0].length / 2][0];
        initialDungeon[initialDungeon[0].length / 2][1].myCoords = new Point(initialDungeon[0].length / 2, 1);
//        dungeon[0][0].roomContents = "S";
//        dungeon[0][0].east = dungeon[0][1];
//        dungeon[0][0].coords = new Point(0, 0);
//        dungeon[0][1].roomContents = "P";
//        dungeon[0][1].west = dungeon[0][0];
//        dungeon[0][1].coords = new Point(0, 1);

        while (hasProspects() && count < MAX_EXPANSION_COUNT) {
            generateRooms();
        }
    }

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

    public void generateRooms() {
        count++;
        Random rand = new Random();
        int roomsGenerated = 0;
        for (Point p : getProspects()) {
            int numOfExits;
            numOfExits = rand.nextInt(3);
            if (roomsGenerated == 0) {
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

//    public void bugTest() {
//        dungeon[10][10].roomContents = "C";
//        dungeon[10 - 1][10].roomContents = "N";
//        dungeon[10][10 + 1].roomContents = "E";
//        dungeon[10 + 1][10].roomContents = "S";
//        dungeon[10][10 - 1].roomContents = "W";
//        displayDungeon();
//    }

    public boolean validMove(room room, int direction) {
        // If the move is north
        if (direction == 0 && room.myCoords.x - 1 >= 0) {
            return initialDungeon[room.myCoords.x - 1][room.myCoords.y].myRoomContents.equals(" ");
        // If move is east
        } else if (direction == 1 && room.myCoords.y + 1 < initialDungeon[0].length) {
            return initialDungeon[room.myCoords.x][room.myCoords.y + 1].myRoomContents.equals(" ");
        // If move is south
        } else if (direction == 2 && room.myCoords.x + 1 < initialDungeon.length) {
            return initialDungeon[room.myCoords.x + 1][room.myCoords.y].myRoomContents.equals(" ");
        // If move is west
        } else if (direction == 3 && room.myCoords.y - 1 >= 0) {
            return initialDungeon[room.myCoords.x + 1][room.myCoords.y].myRoomContents.equals(" ");
        // Room was on edge of board and thus could not move off it
        } else {
            return false;
        }
    }

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

    public void finalizeDungeon() {
        cleanUpDungeon();
        int dunRow = 0;
        int dunCol = 0;
        for (int i = 0; i < finalDungeon.length; i++) {
            for (int j = 0; j < finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1) {
                    finalDungeon[i][j] = initialDungeon[dunRow][dunCol];
                    dunCol++;
                    if (dunCol == initialDungeon[0].length) {
                        dunCol = 0;
                        dunRow++;
                    }
                } else {
                    finalDungeon[i][j] = new room(" ", new Point(i, j));
                }
            }
        }
        finalDungeon = generateConnections(finalDungeon);
    }

    public void displayDungeon() {
        System.out.print("\t ");
        for(int i = 0; i < initialDungeon.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        for (int i = 0; i < finalDungeon.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < finalDungeon[0].length; j++) {
                System.out.print(finalDungeon[i][j].myRoomContents);
                System.out.print("   ");
            }
            System.out.print("\n");
        }
    }

    public room[][] generateConnections(room[][] theRooms) {
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
                        initialDungeon[i][j].myNorth = null;
                    } else if (initialDungeon[i][j].myEast != null) {
                        initialDungeon[i][j + 1].myWest = null;
                        initialDungeon[i][j].myEast = null;
                    } else if (initialDungeon[i][j].mySouth != null) {
                        initialDungeon[i + 1][j].myNorth = null;
                        initialDungeon[i][j].mySouth = null;
                    } else if (initialDungeon[i][j].myWest != null) {
                        initialDungeon[i][j - 1].myEast = null;
                        initialDungeon[i][j].myWest = null;
                    }
                    initialDungeon[i][j].myRoomContents = " ";
                }
            }
        }
    }

    public class room {
        public String myRoomContents;
        public room myNorth, myEast, mySouth, myWest = null;
        public Point myCoords;

        private room(String theContents, Point theCoords) {
            myRoomContents = theContents;
            myCoords = theCoords;
        }
    }

}
