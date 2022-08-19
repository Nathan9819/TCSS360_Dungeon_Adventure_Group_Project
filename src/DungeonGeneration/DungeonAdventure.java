import javax.swing.*;
import java.awt.*;

/**
 * This class handles interaction between DungeonGenerator and UI. It passes information between
 * the two classes, calling methods to both setup and display the dungeon in a GUI format. This is
 * done by iterating over the 2d array, "finalDungeon" within DungeonGenerator. This 2d array contains
 * rooms and their connections. This iterating is done within the spawnDungeon method and is used to
 * place all assets at the correct coordinates. A separate method is used to add entities to the rooms.
 *
 * @author Nathan Mahnke
 */
public class DungeonAdventure{
    private int playerClass;
    private DungeonGenerator dg;
    private Player p;
    private Room[][] dungeon;
    private UI ui;

    /**
     * This is the DungeonAdventure constructor. It is used to instantiate dg, which is a DungeonGenerator
     * object. By instantiating dg, there now exists a 2d array with a generated dungeon that can be assigned
     * to the "dungeon" 2d Room array. This will later be referenced while the GUI is being built. From there,
     * multiple methods are called to display rooms/hallways by interacting with the UI class. startGame is not
     * called by the constructor as the GUI JFrame has not yet been instantiated, so there is nothing to display
     * the game assets upon. Thus, after the creation of the JFrame within the UI class, startGame is called by UI.
     */
    public DungeonAdventure() {
        dg = new DungeonGenerator();
        ui = new UI(this);
        playerClass = -1;
    }

    /**
     * generateNewDungeon is used upon changing floors. It is called once upon creation of a DungeonAdventure object and is then called
     * later within UI upon floor transition. It simply calls a method in DungeonGenerator to create a new dungeon which will be shown
     * as the next floor then calls startGame to continue the process of displaying the dungeon.
     */
    public void generateNewDungeon() {
        dg.generateNewDungeon();
        startGame();
    }

    /**
     * startGame is called whenever a new dungeon floor is generated. It handles deciding how the screen should be updated. If the player
     * is between floors 0 and 2, then a new dungeon floor is generated. If the player is past floor 2, meaning they have just descended
     * from floor 2, then, instead of spawning a regular floor, a final boss floor is spawned instead.
     */
    public void startGame() {
        dungeon = dg.getDungeon();
        if (getFloor() < 3) {
            spawnDungeon();
            spawnCharacter();
        } else {
            finalBoss();
        }
    }

    /**
     * finalBoss is responsible for spawning the dungeon floor which contains the final boss. When floor three
     * is generated by DungeonGenerator, is consists of only one room. This room is populated by a final boss
     * enemy. The method creates a normal roomTile, then edits its size and sprite to fit the custom final
     * boss room. This room is three times the size of a normal room. The boss and boss-room are then spawned
     * and the player is moved to the correct position on-screen to make them appear in the opposing corner of
     * the room relative to the boss. From there, the final battle may play out.
     */
    public void finalBoss() {
        RoomTile myBossRoom = new RoomTile(true);
        myBossRoom.setSprite(new ImageIcon(getClass().getResource("Assets/finalBossRoom.png")));
        myBossRoom.setHeight(156);
        myBossRoom.setWidth(156);
        ui.spawnRoomOrHallway(myBossRoom, 100, 100, 0, 0);
        ui.spawnMonster(dg.getDungeon()[0][0].getMonster(), 2, 4);

        p = new Player(0, 0, 0);
        p.setCoords(new Point(0, 0));
        p.setRoom(getDungeon()[0][0]);
        ui.spawnPlayer(p, 125, 185);
    }



    /**
     * The spawnDungeon method references the 2d array of Room objects to place the roomTile for each Room to the proper
     * coordinates on-screen. These coordinates are calculated in such a way that accounts for the differing heights and
     * widths of rooms and hallways. This is achieved by implementing the Math.ceil operation and the innate Math.floor
     * operation of integer division. This results in a visual representation of the dungeon being drawn to the screen.
     * As a final note, All rooms are spawned as their dark variant, so their assets may be changed to the light version
     * as the player explores the dungeon.
     */
    public void spawnDungeon() {
        HallwayVertical myHV = new HallwayVertical(false);
        HallwayHorizontal myHH = new HallwayHorizontal(false);
        RoomTile myDarkRoom = new RoomTile(false);
        for (int i = 0; i < getDungeon().length; i++) {
            for (int j = 0; j < getDungeon()[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && getDungeon()[i][j].getRoomContents().equals("Room")) {
                    myDarkRoom.setRoomImage(getDungeon()[i][j].getRoomCode());
                    ui.spawnRoomOrHallway(myDarkRoom, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)),
                                                      (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), i, j);
                }
                if (getDungeon()[i][j].getRoomContents().equals("|")) {
                    ui.spawnRoomOrHallway(myHV, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)) + 14,
                                                (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) - 6, i, j);
                }
                if (getDungeon()[i][j].getRoomContents().equals("-")) {
                    ui.spawnRoomOrHallway(myHH, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)),
                                           (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) + 11, i, j);
                }
            }
        }
    }

    /**
     * The spawnCharacter method places the character in the starting room, aligning the center of the players base with the center
     * of the room which they occupy. The proper offsets are stored within the character class. More information regarding this can
     * be found there. Upon character spawning, various methods become enabled within the UI class
     */
    private void spawnCharacter() {
        int myI = 1;
        int myJ = 7;
        if (getFloor() == 0) {
            p = new Player(myI, myJ, playerClass);
        }
        p.setCoords(new Point(1, 7));
        getPlayer().setRoom(getDungeon()[myI][myJ]);
        ui.spawnPlayer(p, (((int) Math.ceil((double) getPlayer().getCoords().y/ 2) * 26) + ((getPlayer().getCoords().y / 2) * 51) + getPlayer().getOffSetJ()),
                          (((int) Math.ceil((double) getPlayer().getCoords().x/2) * 20) + ((getPlayer().getCoords().x / 2) * 51) + getPlayer().getOffSetI()));
    }

    /**
     * The getRoomCode method receives two integers and uses them as coordinates to reference in room object within the dungeon array.
     * From this reference, the roomCode of the given room is returned.
     *
     * @param theI The y coordinate of the given Room
     * @param theJ The x coordinate of the given Room
     * @return     The roomCode of the Room at the given coordinates
     */
    public String getRoomCode(int theI, int theJ) {
        return getDungeon()[theI][theJ].getRoomCode();
    }

    /**
     * setPlayerClass is called by buttons on the main menu of the game which
     * allow the player to choose their class.
     *
     * @param theClass An integer representing the player's class
     */
    public void setPlayerClass(final int theClass) {
        playerClass = theClass;
    }

    /**
     * getPlayerClass returns the current class of the player.
     *
     * @return An integer representing the player's class
     */
    public int getPlayerClass() {
        return playerClass;
    }

    /**
     * getDungeon is used to reference the array of Room objects
     * which make up the dungeon.
     *
     * @return A 2d array of room objects
     */
    public Room[][] getDungeon() {
        return dungeon;
    }

    /**
     * getPlayer simply returns the player. This is used to reference
     * player stats and/or location.
     *
     * @return a Player object
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * getFloor is used to find what floor of the dungeon the player is
     * currently on.
     *
     * @return An integer representing the current floor
     */
    public int getFloor() {
        return dg.getFloor();
    }
}
