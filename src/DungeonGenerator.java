import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {
    public static room[][] dungeon = new room[21][21];
    public static ArrayList<Point> prospects;
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
        dungeon[dungeon[0].length / 2][0].roomContents = "S";
        dungeon[dungeon[0].length / 2][0].east = dungeon[dungeon[0].length / 2][1];
        dungeon[dungeon[0].length / 2][0].coords = new Point(dungeon[0].length / 2, 0);
        dungeon[dungeon[0].length / 2][1].roomContents = "P";
        dungeon[dungeon[0].length / 2][1].west = dungeon[dungeon[0].length / 2][0];
        dungeon[dungeon[0].length / 2][1].coords = new Point(dungeon[0].length / 2, 1);
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
                if (dungeon[row][col].roomContents.equals("P")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void generateRooms() {
        count++;
        for (Point p : getProspects()) {
            displayDungeon();
            Random rand = new Random();
            int numOfExits;
            numOfExits = rand.nextInt(2) + 1;
            dungeon[p.x][p.y].roomContents = "S";
            int randDirection;
            randDirection = rand.nextInt(3);
            while (numOfExits > 0) {
                if (validMove(dungeon[p.x][p.y], randDirection)) {
                    createRoom(p, randDirection);
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
            dungeon[p.x][p.y].roomContents = "S";
            dungeon[p.x][p.y].north = dungeon[p.x - 1][p.y];
            dungeon[p.x - 1][p.y].roomContents = "P";
            dungeon[p.x - 1][p.y].coords = new Point(p.x, p.y);
            dungeon[p.x - 1][p.y].south = dungeon[p.x][p.y];
        } else if (direction == 1) {
            dungeon[p.x][p.y].roomContents = "S";
            dungeon[p.x][p.y].east = dungeon[p.x][p.y + 1];
            dungeon[p.x][p.y + 1].roomContents = "P";
            dungeon[p.x][p.y + 1].coords = new Point(p.x, p.y + 1);
            dungeon[p.x][p.y + 1].west = dungeon[p.x][p.y];
        } else if (direction == 2) {
            dungeon[p.x][p.y].roomContents = "S";
            dungeon[p.x][p.y].north = dungeon[p.x + 1][p.y];
            dungeon[p.x + 1][p.y].roomContents = "P";
            dungeon[p.x + 1][p.y].coords = new Point(p.x, p.y);
            dungeon[p.x + 1][p.y].south = dungeon[p.x][p.y];
        } else if (direction == 3) {
            dungeon[p.x][p.y].roomContents = "S";
            dungeon[p.x][p.y].east = dungeon[p.x][p.y - 1];
            dungeon[p.x][p.y - 1].roomContents = "P";
            dungeon[p.x][p.y - 1].coords = new Point(p.x, p.y - 1);
            dungeon[p.x][p.y - 1].west = dungeon[p.x][p.y];
        }
    }

    public boolean validMove(room room, int direction) {
        // If the move is north
        if (direction == 0 && room.coords.x - 1 >= 0) {
            return dungeon[room.coords.x - 1][room.coords.y].roomContents.equals(" ");
        // If move is east
        } else if (direction == 1 && room.coords.y + 1 < dungeon[0].length) {
            return dungeon[room.coords.x][room.coords.y + 1].roomContents.equals(" ");
        // If move is south
        } else if (direction == 2 && room.coords.x + 1 < dungeon.length) {
            return dungeon[room.coords.x + 1][room.coords.y].roomContents.equals(" ");
        // If move is west
        } else if (direction == 3 && room.coords.y - 1 >= 0) {
            return dungeon[room.coords.x + 1][room.coords.y].roomContents.equals(" ");
        // Room was on edge of board and thus could not move off it
        } else {
            return false;
        }
    }

    public List<Point> getProspects() {
        ArrayList points = new ArrayList();
        for (int row = 0; row < dungeon.length; row++) {
            for (int col = 0; col < dungeon[0].length; col++) {
                if (dungeon[row][col].roomContents.equals("P")) {
                    points.add(new Point(row, col));
                }
            }
        }
        return points;
    }

    public static void displayDungeon() {
        System.out.print("\t ");
        for(int i = 0; i < dungeon.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        System.out.print("\n");
        for (int i = 0; i < dungeon.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < dungeon[0].length; j++) {
                System.out.print(dungeon[i][j].roomContents);
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    private class room {
        public String roomContents;
        public room north, east, south, west = null;
        public Point coords;

        private room(String contents, Point xy) {
            roomContents = contents;
            coords = xy;
        }
    }
}
