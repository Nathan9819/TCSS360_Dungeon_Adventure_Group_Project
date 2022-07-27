import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The UI class is used for all things GUI. This class extends JFrame and implements KeyListener.
 * The extension of JFrame allows the class to act as its own JFrame and the KeyListener implementation
 * allows for the user to input key commands (WASD for movement, etc.).
 * @Author Nathan Mahnke
 */
public class UI extends JFrame implements KeyListener{
    DungeonAdventure da;
    JLayeredPane layeredPane;
    public JLabel[][] labels = new JLabel[22][22];
    public JLabel player;

    /**
     * This is the UI constructor. It receives a DungeonAdventure object that allows it to reference
     * the 2d array within that represents the dungeon. This is vital for polling room contents when
     * displaying a room, as well as, referencing the player character and its location.
     *
     * @param theDA The DungeonAdventure object
     */
    public UI(DungeonAdventure theDA) {
        this.da = theDA;
        createMainField();
    }


    /**
     * The createMainField method instantiates and sets the parameters of the JFrame which encompasses the UI class.
     * It also instantiates the JLayeredPane to be used in the placement of visual assets.
     */
    public void createMainField() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 2560, 1440);

        this.setTitle("Dungeon Adventure");
        this.add(layeredPane);
        this.setPreferredSize(new Dimension(2560, 1440));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color myBgColor = new Color(115, 62, 57);
        this.getContentPane().setBackground(myBgColor);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.pack();
    }


    /**
     * The spawnItem method is used in the placement of virtually all visual assets. The only exception
     * to this is the placement of the player character's visual asset. This method receives and Entity,
     * two integers (x and y) for screen coordinates, and two more integers (i and j) for array coordinates.
     * Using the given information, a new JLabel is added to "labels" at the given array coordinates. Then,
     * the Entity is referenced for its height and width data as well as the visual asset used to represent
     * it. It is then placed at the given x and y coordinated on the JLayeredPane.
     *
     * @param theEntity The entity to be drawn to the screen
     * @param theX      The x-coordinate for the entity to be drawn onscreen
     * @param theY      The y-coordinate for the entity to be drawn onscreen
     * @param theI      The i-coordinate for the JLabel to be added to the JLabel array
     * @param theJ      The j-coordinate for the JLabel to be added to the JLabel array
     */
    public void spawnItem(Entity theEntity, int theX, int theY, int theI, int theJ) {
        labels[theI][theJ] = new JLabel();
        labels[theI][theJ].setBounds(theX,theY,theEntity.width,theEntity.height);
        labels[theI][theJ].setIcon(theEntity.sprite);
        layeredPane.add(labels[theI][theJ], theEntity.layer);
    }

    /**
     * The spawnPlayer method is used whenever a new player character needs to be drawn to the screen. The method
     * accepts a player object as well sa two integers (x and y) to be used for the players onscreen coordinates.
     * The method then places the player character onscreen at the given coordinates and calls the setLight method
     * to update the lighting of the rooms adjacent to the player. This effect restricts the player's view of the board
     * allowing them to only see rooms previously visited or rooms adjacent to rooms previously visited.
     *
     * @param thePlayer The player object to be drawn onscreen
     * @param theX      The x-coordinate for the player to be drawn onscreen
     * @param theY      The y-coordinate for the player to be drawn onscreen
     */
    public void spawnPlayer(Player thePlayer, int theX, int theY) {
        player = new JLabel();
        player.setBounds(theX,theY,thePlayer.width,thePlayer.height);
        player.setIcon(thePlayer.sprite);
        layeredPane.add(player, thePlayer.layer);
        setLight(thePlayer.coords.x, thePlayer.coords.y, thePlayer.room);
    }

    public void setLight(int theI, int theJ, Room theRoom) {
        RoomTile myLightRoom = new RoomTile(true);
        myLightRoom.setRoomImage(da.getRoomCode(theI, theJ));
        HallwayHorizontal myLightHH = new HallwayHorizontal(true);
        HallwayVertical myLightHV = new HallwayVertical(true);
        labels[theI][theJ].setIcon(myLightRoom.sprite);
        if (theRoom.north != null) {
            labels[theI - 1][theJ].setIcon(myLightHV.sprite);
            myLightRoom.setRoomImage(da.getRoomCode(theI - 2, theJ));
            labels[theI - 2][theJ].setIcon(myLightRoom.sprite);
        }
        if (theRoom.east != null) {
            labels[theI][theJ + 1].setIcon(myLightHH.sprite);
            myLightRoom.setRoomImage(da.getRoomCode(theI, theJ + 2));
            labels[theI][theJ + 2].setIcon(myLightRoom.sprite);
        }
        if (theRoom.west != null) {
            labels[theI][theJ - 1].setIcon(myLightHH.sprite);
            myLightRoom.setRoomImage(da.getRoomCode(theI, theJ - 2));
            labels[theI][theJ - 2].setIcon(myLightRoom.sprite);
        }
        if (theRoom.south != null) {
            labels[theI + 1][theJ].setIcon(myLightHV.sprite);
            myLightRoom.setRoomImage(da.getRoomCode(theI + 2, theJ));
            labels[theI + 2][theJ].setIcon(myLightRoom.sprite);
        }
        da.p.room.visited = true;
        refresh();
    }


    /**
     * The refresh method simply repaints and revalidates the UI JFrame. This is used to update the screen
     * when any changes are made to visual aspects of the GUI.
     */
    public void refresh() {
        this.repaint();
        this.revalidate();
    }

    /**
     * This method is unused, but required due to the implementation of KeyListener by UI
     *
     * @param e The key being typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not being used
    }

    /**
     * The keyPressed method overrides the keyPressed method of KeyListener. This allows UI to be
     * updated upon user input. It listens for presses of the W, A, S, and D keys. These keys
     * correspond to the cardinal directions and allow the move the player character throughout the
     * dungeon. If a movement is valid, the method calls movePlayer, passing it the given direction.
     *
     * @param e The key being pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'w' :
                if (da.p.room.north != null) {
                    movePlayer(0);
                    System.out.println("w pressed");
                }
                break;
            case 'd' :
                if (da.p.room.east != null) {
                    movePlayer(1);
                    System.out.println("d pressed");
                }
                break;
            case 's' :
                if (da.p.room.south != null) {
                    movePlayer(2);
                    System.out.println("s pressed");
                }
                break;
            case 'a' :
                if (da.p.room.west != null) {
                    movePlayer(3);
                    System.out.println("a pressed");
                }
                break;
        }
    }

    /**
     * This method is unused, but required due to the implementation of KeyListener by UI
     *
     * @param e The key being released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not being used
    }

    /**
     * The movePlayer method accepts and integer representing the direction to move the player.
     * For this method to be called, the direction has already been tested and is considered valid,
     * so the method simply moves the player in the given direction by setting the proper offsets,
     * update the players room, coordinates, and the lighting of the rooms.
     *
     * @param theDirection The direction for the player to be moved
     */
    public void movePlayer (int theDirection) {
        int myOffsetJ = 0;
        int myOffsetI = 0;
        switch (theDirection) {
            case 0 -> myOffsetI = -2;
            case 1 -> myOffsetJ = 2;
            case 2 -> myOffsetI = 2;
            case 3 -> myOffsetJ = -2;
        }

        player.setBounds((((int) Math.ceil(((double)da.p.coords.y + myOffsetJ)/ 2) * 26) + (((da.p.coords.y + myOffsetJ)/ 2) * 51) + da.p.offSetJ),
                            (((int) Math.ceil(((double)da.p.coords.x + myOffsetI)/2) * 20) + ((((da.p.coords.x + myOffsetI) / 2)) * 51) + da.p.offSetI), da.p.width, da.p.height);
        da.p.coords = new Point(da.p.coords.x + myOffsetI, da.p.coords.y + myOffsetJ);
        if (theDirection < 2) {
            da.p.room = theDirection == 0 ? da.p.room.north : da.p.room.east;
        } else {
            da.p.room = theDirection == 2 ? da.p.room.south : da.p.room.west;
        }
        if (!da.p.room.visited) {
            setLight(da.p.coords.x, da.p.coords.y, da.p.room);
        }
        refresh();
    }


}
