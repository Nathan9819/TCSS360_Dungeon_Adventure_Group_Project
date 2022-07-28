/**
 * This class handles interaction between DungeonGenerator and UI. It passes information between
 * the two classes, calling methods to both setup and display the dungeon in a GUI format. This is
 * done by iterating over the 2d array, "finalDungeon" within DungeonGenerator. This 2d array contains
 * rooms and their connections. This iterating is done within the spawnDungeon method and is used to
 * place all assets at the correct coordinates.
 * @Author Nathan Mahnke
 */
public class DungeonAdventure{
    DungeonGenerator dg;
    protected Player p;
    Room[][] dungeon;
    UI ui = new UI(this);

    /**
     * This is the DungeonAdventure constructor. It is used to instantiate dg, which is a DungeonGenerator
     * object. By instantiating dg, there now exists a 2d array with a generated dungeon that can be assigned
     * to the "dungeon" 2d Room array. This will later be referenced while the GUI is being built. From there,
     * multiple methods are called to display rooms/hallways by interacting with the UI class. Finally, the
     * GUI is set to visible, thus being displayed to the user.
     */
    public DungeonAdventure() {
        dg = new DungeonGenerator();
        dungeon = dg.getDungeon();
    }

    public void startGame() {
        spawnDungeon();
        spawnCharacter();
    }

    /**
     * The spawnDungeon method references the 2d array "dungeon" to places the item found within each cell to the proper
     * coordinates on-screen. These coordinates are calculated in such a way that accounts for the differing heights and
     * widths of rooms and hallways. This is achieved by implementing the Math.ceil operation and the innate Math.floor
     * operation of integer division. This results in a visual representation of the dungeon being drawn to the screen.
     */
    public void spawnDungeon() {
        HallwayVertical myHV = new HallwayVertical(false);
        HallwayHorizontal myHH = new HallwayHorizontal(false);
        RoomTile myDarkRoom = new RoomTile(false);
        for (int i = 0; i < dungeon.length; i++) {
            for (int j = 0; j < dungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dungeon[i][j].roomContents.equals("S")) {
                    myDarkRoom.setRoomImage(dungeon[i][j].roomCode);
                    ui.spawnItem(myDarkRoom, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), i, j);
                }
                if (dungeon[i][j].roomContents.equals("|")) {
                    ui.spawnItem(myHV, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)) + 14, (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) - 6, i, j);
                }
                if (dungeon[i][j].roomContents.equals("-")) {
                    ui.spawnItem(myHH, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) + 11, i, j);
                }
            }
        }
    }

    /**
     * The spawnCharacter method places the character in the starting room, aligning the center of the players base with the center
     * of the room which they occupy. The proper offsets are stored within the character class. My information regarding this can
     * be found there. Upon character spawning, various methods become enabled within the UI class
     */
    public void spawnCharacter() {
        int myI = 1;
        int myJ = 7;
        p = new Player(myI, myJ, 0);
        p.room = dungeon[myI][myJ];
        ui.spawnPlayer(p, (((int) Math.ceil((double)p.coords.y/ 2) * 26) + ((p.coords.y / 2) * 51) + p.offSetJ), (((int) Math.ceil((double)p.coords.x/2) * 20) + ((p.coords.x / 2) * 51) + p.offSetI));
    }

    /**
     * The getRoomCode method receives two integers and uses them as coordinates to reference in room object within the dungeon array.
     * From this reference, the roomCode of the given room is returned.
     *
     * @param theI The y coordinate of the given Room
     * @param theJ The x coordinate of the given Room
     * @return     The roomCode of the Room at the given coordinates.
     */
    public String getRoomCode(int theI, int theJ) {
        return dungeon[theI][theJ].roomCode;
    }
}
