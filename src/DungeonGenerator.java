import java.util.Random;

public class DungeonGenerator {
    public static room[][] maze = new room[21][21];
    private final int MAX_ROOM_COUNT = 40;
    private int roomCount = 0;

    public DungeonGenerator() {
        fillMaze();
        generateRoom(20, 10, 0);
        generateMaze(19, 10, 0);
        displayMaze();
    }

    public void fillMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new room(0);
            }
        }
    }

    public void generateMaze(int row, int col, int lastDirection) {
        if (roomCount < MAX_ROOM_COUNT) {
            boolean valid = false;
            int nextDirection = -1;
            while (!valid) {
                Random rand = new Random();
                int nextStep = rand.nextInt(6);
                if (nextStep > 3) {
                    nextDirection = lastDirection;
                } else {
                    nextDirection = (lastDirection + nextStep) % 4;
                }
                valid = testMove(row, col, nextDirection);
                if (Math.abs(nextDirection - lastDirection) == 2) {
                    valid = false;
                }
            }
            generateRoom(row, col, nextDirection);

            roomCount++;
        }
    }

    public static boolean testMove(int row, int col, int direction) {
        if (direction == 0) {
            return row - 1 >= 0;
        } else if (direction == 1) {
            return col + 1 < 21;
        } else if (direction == 2) {
            return row + 1 < 21;
        } else if (direction == 3) {
            return col - 1 >= 0;
        }
        return false;
    }

    public void generateRoom(int x, int y, int direction) {
//        Random rand = new Random();
//        int contents = rand.nextInt(3) + 1;
        maze[x][y].roomContents = 1;
        if (x + 1 < 21) {
            maze[x + 1][y].west = true;
        }
    }

    public static void displayMaze() {
        System.out.print("\t ");
        for(int i = 0; i < maze.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(i + "  ");
            }
        }
        System.out.print("\n");
        for (int i = 0; i < maze.length; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j].roomContents);
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    private class room {
        public int roomContents;
        public boolean north, east, south, west = false;

        private room(int contents) {
            roomContents = contents;
        }
    }
}
