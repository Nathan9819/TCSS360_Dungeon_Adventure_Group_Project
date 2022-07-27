package dungeon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {
    private static Room[][] initialDungeon;
    public static Room[][] finalDungeon;
    private final int MAX_EXPANSION_COUNT = 8;
    private int count = 0;
    private Random rand = new Random();

    public DungeonGenerator() {
        initialDungeon = new Room[11][11];
        finalDungeon = new Room[initialDungeon.length * 2][initialDungeon[0].length * 2];

        fillMaze();
        startGame();
        finalizeDungeon();
        displayDungeon(initialDungeon);
        displayDungeon(finalDungeon);
    }

    public void fillMaze() {
        for (int i = 0; i < initialDungeon.length; i++) {
            for (int j = 0; j < initialDungeon[0].length; j++) {
                initialDungeon[i][j] = new Room(" ", new Point(i, j));
            }
        }
    }

    public void startGame() {
        initialDungeon[initialDungeon[0].length / 2][0].myRoomContents = "S";
        initialDungeon[initialDungeon[0].length / 2][0].myNeighbors[1] = initialDungeon[initialDungeon[0].length / 2][1];
        initialDungeon[initialDungeon[0].length / 2][0].myCoords = new Point(initialDungeon[0].length / 2, 0);
        initialDungeon[initialDungeon[0].length / 2][1].myRoomContents = "P";
        initialDungeon[initialDungeon[0].length / 2][1].myNeighbors[3] = initialDungeon[initialDungeon[0].length / 2][0];
        initialDungeon[initialDungeon[0].length / 2][1].myCoords = new Point(initialDungeon[0].length / 2, 1);

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
        //Random rand = new Random();
        int roomsGenerated = 0;
        for (Point p : getProspects()) {
            //int numOfExits;
            int numOfExits = rand.nextInt(3);
            if (roomsGenerated < 3) {
                numOfExits = 2;
            }
            initialDungeon[p.x][p.y].myRoomContents = "S";
            //int randDirection;
            int randDirection = rand.nextInt(3);
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
    	initialDungeon[p.x][p.y].myRoomContents = "S";
    	if (rand.nextDouble() < 0.2) {
    		//initialDungeon[p.x][p.y].myRoomContents = "M";
    		initialDungeon[p.x][p.y] = new MonsterRoom(initialDungeon[p.x][p.y]);
    		for (int i = 0; i < 4; i++) {
    			if (initialDungeon[p.x][p.y].myNeighbors[i] != null) {
    				initialDungeon[p.x][p.y].myNeighbors[i].myNeighbors[(i+2)%4] = initialDungeon[p.x][p.y];
    			}
    		}
    	}
        if (direction == 0) {
            //initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myNeighbors[0] = initialDungeon[p.x - 1][p.y];
            initialDungeon[p.x - 1][p.y].myRoomContents = "P";
            initialDungeon[p.x - 1][p.y].myNeighbors[2] = initialDungeon[p.x][p.y];
        } else if (direction == 1) {
            //initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myNeighbors[1] = initialDungeon[p.x][p.y + 1];
            initialDungeon[p.x][p.y + 1].myRoomContents = "P";
            initialDungeon[p.x][p.y + 1].myNeighbors[3] = initialDungeon[p.x][p.y];
        } else if (direction == 2) {
            //initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myNeighbors[2] = initialDungeon[p.x + 1][p.y];
            initialDungeon[p.x + 1][p.y].myRoomContents = "P";
            initialDungeon[p.x + 1][p.y].myNeighbors[0] = initialDungeon[p.x][p.y];
        } else if (direction == 3) {
            //initialDungeon[p.x][p.y].myRoomContents = "S";
            initialDungeon[p.x][p.y].myNeighbors[3] = initialDungeon[p.x][p.y - 1];
            initialDungeon[p.x][p.y - 1].myRoomContents = "P";
            initialDungeon[p.x][p.y - 1].myNeighbors[1] = initialDungeon[p.x][p.y];
        }
    }

    public boolean validMove(Room theRoom, int theDirection) {
        // If the move is north
        if (theDirection == 0 && theRoom.myCoords.x - 1 >= 0) {
            return initialDungeon[theRoom.myCoords.x - 1][theRoom.myCoords.y].myRoomContents.equals(" ");
        // If move is east
        } else if (theDirection == 1 && theRoom.myCoords.y + 1 < initialDungeon[0].length) {
            return initialDungeon[theRoom.myCoords.x][theRoom.myCoords.y + 1].myRoomContents.equals(" ");
        // If move is south
        } else if (theDirection == 2 && theRoom.myCoords.x + 1 < initialDungeon.length) {
            return initialDungeon[theRoom.myCoords.x + 1][theRoom.myCoords.y].myRoomContents.equals(" ");
        // If move is west
        } else if (theDirection == 3 && theRoom.myCoords.y - 1 >= 0) {
            return initialDungeon[theRoom.myCoords.x][theRoom.myCoords.y - 1].myRoomContents.equals(" ");
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

    public Room[][] generateConnections(Room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    if (i < theRooms.length - 1) {
                        if (theRooms[i][j].myNeighbors[2] == theRooms[i + 2][j]) {
                            theRooms[i + 1][j].myRoomContents = "|";
                        }
                    }
                    if (j < theRooms[0].length - 1) {
                        if (theRooms[i][j].myNeighbors[1] == theRooms[i][j + 2]) {
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
                    if (initialDungeon[i][j].myNeighbors[0] != null) {
                        initialDungeon[i - 1][j].myNeighbors[2] = null;
                        initialDungeon[i][j].myNeighbors[0] = null;
                    } else if (initialDungeon[i][j].myNeighbors[1] != null) {
                        initialDungeon[i][j + 1].myNeighbors[3] = null;
                        initialDungeon[i][j].myNeighbors[1] = null;
                    } else if (initialDungeon[i][j].myNeighbors[2] != null) {
                        initialDungeon[i + 1][j].myNeighbors[0] = null;
                        initialDungeon[i][j].myNeighbors[2] = null;
                    } else if (initialDungeon[i][j].myNeighbors[3] != null) {
                        initialDungeon[i][j - 1].myNeighbors[1] = null;
                        initialDungeon[i][j].myNeighbors[3] = null;
                    }
                    initialDungeon[i][j].myRoomContents = " ";
                }
            }
        }
    }

    public static void updatePlayerCoords(final Player p) {
        p.setRoom(finalDungeon[p.myCoords.x][p.myCoords.y]);
    }

//    public class room {
//        public String myRoomContents;
//        public room myNorth, myEast, mySouth, myWest = null;
//        public Point myCoords;
//
//        private room(String theContents, Point theCoords) {
//            myRoomContents = theContents;
//            myCoords = theCoords;
//        }
//    }
}
