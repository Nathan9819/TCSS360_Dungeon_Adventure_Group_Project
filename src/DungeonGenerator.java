import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {
    public static room[][] dungeon = new room[11][11];
//    public static ArrayList<Point> prospects;
    private final int MAX_EXPANSION_COUNT = 6;
    private int count = 0;

    public DungeonGenerator() {
        fillMaze();
        startGame();
        displayDungeon();
    }

    public void fillMaze() {
        for (int i = 0; i < dungeon.length; i++) {
            for (int j = 0; j < dungeon[0].length; j++) {
                dungeon[i][j] = new room(" ", new Point(i, j));
            }
        }
    }

    public void startGame() {
        dungeon[dungeon[0].length / 2][0].myRoomContents = "S";
        dungeon[dungeon[0].length / 2][0].myEast = dungeon[dungeon[0].length / 2][1];
        dungeon[dungeon[0].length / 2][0].myCoords = new Point(dungeon[0].length / 2, 0);
        dungeon[dungeon[0].length / 2][1].myRoomContents = "P";
        dungeon[dungeon[0].length / 2][1].myWest = dungeon[dungeon[0].length / 2][0];
        dungeon[dungeon[0].length / 2][1].myCoords = new Point(dungeon[0].length / 2, 1);
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
        for (int row = 0; row < dungeon.length; row++) {
            for (int col = 0; col < dungeon[0].length; col++) {
                if (dungeon[row][col].myRoomContents.equals("P")) {
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
            displayDungeon();
            int numOfExits;
            numOfExits = rand.nextInt(3);
            if (roomsGenerated == 0) {
                numOfExits = 2;
            }
            dungeon[p.x][p.y].myRoomContents = "S";
            int randDirection;
            randDirection = rand.nextInt(3);
            while (numOfExits > 0) {
                if (validMove(dungeon[p.x][p.y], randDirection)) {
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
            dungeon[p.x][p.y].myRoomContents = "S";
            dungeon[p.x][p.y].myNorth = dungeon[p.x - 1][p.y];
            dungeon[p.x - 1][p.y].myRoomContents = "P";
            dungeon[p.x - 1][p.y].mySouth = dungeon[p.x][p.y];
        } else if (direction == 1) {
            dungeon[p.x][p.y].myRoomContents = "S";
            dungeon[p.x][p.y].myEast = dungeon[p.x][p.y + 1];
            dungeon[p.x][p.y + 1].myRoomContents = "P";
            dungeon[p.x][p.y + 1].myWest = dungeon[p.x][p.y];
        } else if (direction == 2) {
            dungeon[p.x][p.y].myRoomContents = "S";
            dungeon[p.x][p.y].mySouth = dungeon[p.x + 1][p.y];
            dungeon[p.x + 1][p.y].myRoomContents = "P";
            dungeon[p.x + 1][p.y].myNorth = dungeon[p.x][p.y];
        } else if (direction == 3) {
            dungeon[p.x][p.y].myRoomContents = "S";
            dungeon[p.x][p.y].myWest = dungeon[p.x][p.y - 1];
            dungeon[p.x][p.y - 1].myRoomContents = "P";
            dungeon[p.x][p.y - 1].myEast = dungeon[p.x][p.y];
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
            return dungeon[room.myCoords.x - 1][room.myCoords.y].myRoomContents.equals(" ");
        // If move is east
        } else if (direction == 1 && room.myCoords.y + 1 < dungeon[0].length) {
            return dungeon[room.myCoords.x][room.myCoords.y + 1].myRoomContents.equals(" ");
        // If move is south
        } else if (direction == 2 && room.myCoords.x + 1 < dungeon.length) {
            return dungeon[room.myCoords.x + 1][room.myCoords.y].myRoomContents.equals(" ");
        // If move is west
        } else if (direction == 3 && room.myCoords.y - 1 >= 0) {
            return dungeon[room.myCoords.x + 1][room.myCoords.y].myRoomContents.equals(" ");
        // Room was on edge of board and thus could not move off it
        } else {
            return false;
        }
    }

    public List<Point> getProspects() {
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < dungeon.length; row++) {
            for (int col = 0; col < dungeon[0].length; col++) {
                if (dungeon[row][col].myRoomContents.equals("P")) {
                    points.add(new Point(row, col));
                }
            }
        }
        return points;
    }

    public void displayDungeon() {
        System.out.print("\t ");
        for(int i = 0; i < dungeon.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        System.out.print("\n");
        room[][] bigDungeon = new room[dungeon.length * 2][dungeon[0].length * 2];
        int dunRow = 0;
        int dunCol = 0;
        for (int i = 0; i < bigDungeon.length; i++) {
            for (int j = 0; j < bigDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1) {
                    bigDungeon[i][j] = dungeon[dunRow][dunCol];
                    dunCol++;
                    if (dunCol == dungeon[0].length) {
                        dunCol = 0;
                        dunRow++;
                    }
                } else {
                    bigDungeon[i][j] = new room(" ", new Point(i, j));
                }
            }
        }
        bigDungeon = generateConnections(bigDungeon);
        for (int i = 0; i < bigDungeon.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < bigDungeon[0].length; j++) {
                System.out.print(bigDungeon[i][j].myRoomContents);
                System.out.print("   ");
            }
            System.out.print("\n");
        }
    }

    public room[][] generateConnections(room[][] theRooms) {
        for (int i = 0; i < theRooms.length; i++) {
            for (int j = 0; j < theRooms[0].length; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    if (i < theRooms.length - 3) {
                        if (theRooms[i][j].mySouth == theRooms[i + 2][j]) {
                            theRooms[i + 1][j].myRoomContents = "|";
                        }
                    }
                    if (j < theRooms.length - 3) {
                        if (theRooms[i][j].myEast == theRooms[i][j + 2]) {
                            theRooms[i][j + 1].myRoomContents = "-";
                        }
                    }
                }
            }
        }
        return theRooms;
    }

    public static void displayDungeonFancy() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dungeon Adventure");
        frame.pack();
        frame.setVisible(true);
    }

    private class room {
        public String myRoomContents;
        public room myNorth, myEast, mySouth, myWest = null;
        public Point myCoords;

        private room(String theContents, Point theCoords) {
            myRoomContents = theContents;
            myCoords = theCoords;
        }
    }

}
