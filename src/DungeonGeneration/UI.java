
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * The DungeonGeneration.UI class is used for all things GUI. This class extends JFrame and implements KeyListener.
 * The extension of JFrame allows the class to act as its own JFrame and the KeyListener implementation
 * allows for the user to input key commands (WASD for movement, etc.).
 * @Author Nathan Mahnke
 */
public class UI extends JFrame implements KeyListener{
    DungeonAdventure da;
    private JPanel titleScreen;
    private JLayeredPane layeredPane;
    private Font titleFont, normalFont;
    private Color bgColor, txtColor;
    private JLabel titleLabel;
    private JButton startButton;
    private JTextArea description;
    private gameStartHandler gameStart = new gameStartHandler();

    public JLabel[][] labels = new JLabel[22][22];
    public JLabel player;



    /**
     * This is the DungeonGeneration.UI constructor. It receives a DungeonGeneration.DungeonAdventure object that allows it to reference
     * the 2d array within that represents the dungeon. This is vital for polling room contents when
     * displaying a room, as well as, referencing the player character and its location.
     *
     * @param theDA The DungeonGeneration.DungeonAdventure object
     */
    public UI(DungeonAdventure theDA) {
        this.da = theDA;
        createMainField();
    }


    /**
     * The createMainField method instantiates and sets the parameters of the JFrame which encompasses the DungeonGeneration.UI class.
     * It also instantiates the JLayeredPane to be used in the placement of visual assets.
     */
    public void createMainField() {
        this.setTitle("Dungeon Adventure");
        this.setPreferredSize(new Dimension(1600, 1200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bgColor = new Color(115, 62, 57);
        this.getContentPane().setBackground(bgColor);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.pack();
        mainMenu();
    }

    public void mainMenu() {
        try {
            InputStream is = getClass().getResourceAsStream("DungeonGeneration/Assets/MaruMonica.ttf");
            titleFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 70f);
            normalFont = titleFont.deriveFont(40f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Font error!");
        }
        titleScreen = new JPanel(new GridLayout(0, 2));
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 800);
        this.add(layeredPane);
        titleLabel = new JLabel("DUNGEON ADVENTURE", SwingConstants.CENTER);
        txtColor = new Color(62,39,49);
        titleLabel.setForeground(txtColor);
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(500, 400, 600, 150);
        layeredPane.add(titleLabel, 1);


        startButton = new JButton("START");
        startButton.setFont(normalFont);
        startButton.setForeground(txtColor);
        startButton.setBackground(null);
        startButton.addActionListener(gameStart);
        startButton.setBounds(700, 700, 200, 100);
        startButton.setAlignmentX(JLayeredPane.CENTER_ALIGNMENT);
        layeredPane.add(startButton, 0);
        this.setVisible(true);
    }

    public void startGame() {
        titleLabel.setVisible(false);
        startButton.setVisible(false);
        description = new JTextArea("You find yourself lost and alone. The only way out . . . is through!");
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(normalFont);
        description.setBounds(10, 800, 1600, 400);
        description.setBackground(bgColor);
        description.setForeground(txtColor);
        this.add(description);
        da.startGame();
    }


    /**
     * The spawnItem method is used in the placement of virtually all visual assets. The only exception
     * to this is the placement of the player character's visual asset. This method receives and DungeonGeneration.Entity,
     * two integers (x and y) for screen coordinates, and two more integers (i and j) for array coordinates.
     * Using the given information, a new JLabel is added to "labels" at the given array coordinates. Then,
     * the DungeonGeneration.Entity is referenced for its height and width data as well as the visual asset used to represent
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
     * The refresh method simply repaints and revalidates the DungeonGeneration.UI JFrame. This is used to update the screen
     * when any changes are made to visual aspects of the GUI.
     */
    public void refresh() {
        this.repaint();
        this.revalidate();
    }

    /**
     * This method is unused, but required due to the implementation of KeyListener by DungeonGeneration.UI
     *
     * @param e The key being typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not being used
    }

    /**
     * The keyPressed method overrides the keyPressed method of KeyListener. This allows DungeonGeneration.UI to be
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
     * This method is unused, but required due to the implementation of KeyListener by DungeonGeneration.UI
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
//        description.setText("You find the strength to carry on and take your first step into the dungeon");
        if (da.dungeon[da.p.coords.x][da.p.coords.y].getDungeonCharacter() != null) {
            try {
                doBattle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        refresh();
    }

    public void doBattle() throws InterruptedException {
        boolean cont = true;
        while (cont) {
            description.setText(da.p.heroType.attack(da.dungeon[da.p.coords.x][da.p.coords.y].getDungeonCharacter()));
            refresh();
            Thread.sleep(1000);
            if (da.dungeon[da.p.coords.x][da.p.coords.y].getDungeonCharacter().getHP() <= 0) {
                cont = false;
            } else {
                description.setText(da.dungeon[da.p.coords.x][da.p.coords.y].getDungeonCharacter().attack(da.p.heroType));
                refresh();
                Thread.sleep(1000);
                if (da.dungeon[da.p.coords.x][da.p.coords.y].getDungeonCharacter().getHP() <= 0) {
                    cont = false;
                }
            }
        }
    }

    public class gameStartHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            startGame();
        }
    }
}
