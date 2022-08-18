import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * The DungeonGeneration. UI class is used for all things GUI. This class extends JFrame and implements KeyListener.
 * The extension of JFrame allows the class to act as its own JFrame and the KeyListener implementation
 * allows for the user to input key commands (WASD for movement, etc.).
 * @Author Nathan Mahnke
 */
public class UI extends JFrame implements KeyListener{
    DungeonAdventure da;
    private boolean started, cheatsActivated;
    private int currentAction, screenWidth, screenHeight, inventorySize;
    private JLayeredPane titleScreen, layeredPane, combatButtons, interactButtons;
    private Font titleFont, normalFont;
    private Color bgColor, txtColor;
    private JButton startButton, knightButton, priestessButton, thiefButton, attackButton, specialButton, acceptButton, declineButton;
    private JTextArea description;
    private final gameStartHandler gameStart;
    private final combatHandler combat;
    private final interactionHandler interaction;
    private JLabel[][] roomsAndHallways, entities;
    private JLabel player;
    private JLabel[][] inventory;
    private Key[] keys;



    /**
     * This is the DungeonGeneration. UI constructor. It receives a DungeonGeneration.DungeonAdventure object that allows it to reference
     * the 2d array within that represents the dungeon. This is vital for polling room contents when
     * displaying a room, as well as, referencing the player character and its location.
     *
     * @param theDA The DungeonGeneration.DungeonAdventure object
     */
    public UI(final DungeonAdventure theDA) {
        this.da = theDA;
        roomsAndHallways = new JLabel[22][22];
        entities = new JLabel[22][22];
        inventory = new JLabel[3][10];
        keys = new Key[10];
        gameStart = new gameStartHandler();
        combat = new combatHandler();
        interaction = new interactionHandler();
        currentAction = 0;
        started = false;
        createMainField();
    }


    /**
     * The createMainField method instantiates and sets the parameters of the JFrame which encompasses the DungeonGeneration. UI class.
     * It also instantiates the JLayeredPane to be used in the placement of visual assets.
     */
    public void createMainField() {
        screenWidth = 1600;
        screenHeight = 1200;
        this.setTitle("Dungeon Adventure");
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bgColor = new Color(115, 62, 57);
        this.getContentPane().setBackground(bgColor);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.pack();
        mainMenu();
    }


    /**
     * The mainMenu method is called upon the creation of a UI object. It creates the title screen and places button/visuals
     * in their appropriate locations.
     */
    public void mainMenu() {
        try {
            InputStream myIS = getClass().getResourceAsStream("DungeonGeneration/Assets/MaruMonica.ttf");
            titleFont = Font.createFont(Font.TRUETYPE_FONT, myIS).deriveFont(Font.BOLD, 70f);
            normalFont = titleFont.deriveFont(40f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Font error!");
        }
        titleScreen = new JLayeredPane();
        titleScreen.setBounds(0, 0, screenWidth, screenHeight);
        this.add(titleScreen);
        JLabel myTitleLabel = new JLabel("DUNGEON ADVENTURE", SwingConstants.CENTER);
        txtColor = new Color(62,39,49);
        myTitleLabel.setForeground(txtColor);
        myTitleLabel.setFont(titleFont);
        myTitleLabel.setBounds(screenWidth/4, (int) ((double) screenHeight/6), (int) ((double) screenWidth/2.5), screenHeight/8);
        titleScreen.add(myTitleLabel, 0);
        startButton = new JButton("START");
        startButton.setFont(normalFont);
        startButton.setForeground(txtColor);
        startButton.setBackground(null);
        startButton.addActionListener(gameStart);
        startButton.setBounds((int) ((double) screenWidth/2.2) - screenWidth/16, (int) ((double) screenHeight/1.5), screenWidth/8, screenHeight/12);
        startButton.setAlignmentX(JLayeredPane.CENTER_ALIGNMENT);
        titleScreen.add(startButton, 0);

        Player myKnight = new Player(0, 0, 0);
        knightButton = new JButton("KNIGHT", new ImageIcon(myKnight.getSprite().getImage().getScaledInstance(myKnight.getWidth()*2, myKnight.getHeight()*2, Image.SCALE_SMOOTH)));
        knightButton.setFont(normalFont);
        knightButton.setForeground(txtColor);
        knightButton.setBackground(null);
        knightButton.setBounds(0, (int) ((double) screenHeight/2.3), screenWidth/3, screenHeight/12);
        knightButton.addActionListener(gameStart);
        titleScreen.add(knightButton, 0);

        Player myPriestess = new Player(0, 0, 1);
        priestessButton = new JButton("Priestess", new ImageIcon(myPriestess.getSprite().getImage().getScaledInstance(myPriestess.getWidth()*2, myPriestess.getHeight()*2, Image.SCALE_SMOOTH)));
        priestessButton.setFont(normalFont);
        priestessButton.setForeground(txtColor);
        priestessButton.setBackground(null);
        priestessButton.setBounds(screenWidth/3, (int) ((double) screenHeight/2.3), screenWidth/3, screenHeight/12);
        priestessButton.addActionListener(gameStart);
        titleScreen.add(priestessButton, 0);

        Player myThief = new Player(0, 0, 2);
        thiefButton = new JButton("THIEF", new ImageIcon(myThief.getSprite().getImage().getScaledInstance(myThief.getWidth()*2, myThief.getHeight()*2, Image.SCALE_SMOOTH)));
        thiefButton.setFont(normalFont);
        thiefButton.setForeground(txtColor);
        thiefButton.setBackground(null);
        thiefButton.setBounds((screenWidth/3) * 2, (int) ((double) screenHeight/2.3), screenWidth/3, screenHeight/12);
        thiefButton.addActionListener(gameStart);
        titleScreen.add(thiefButton, 0);

        this.setVisible(true);
    }


    /**
     * The startGame method is called once the user selects a class and clicks start. The method handles removing the title menu UI and
     * adding the game board (JlayeredPane) as well as text area.
     */
    public void startGame() {
        titleScreen.setVisible(false);
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(10, 10, screenWidth - 10, screenHeight - (screenHeight/3));
        layeredPane.setDoubleBuffered(true);
        layeredPane.setBackground(bgColor);
        this.add(layeredPane);
        description = new JTextArea("YOU FIND YOURSELF LOST AND ALONE. THE ONLY WAY OUT . . . IS THROUGH!");
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(normalFont);
        description.setBounds(10, screenHeight - (screenHeight/3), screenWidth, screenHeight/6);
        description.setBackground(bgColor);
        description.setForeground(txtColor);
        description.setEditable(false);
        this.add(description);

        combatButtons = new JLayeredPane();
        combatButtons.setBounds(10, screenHeight - (screenHeight/8), screenWidth - 10, screenHeight - (screenHeight/12));
        combatButtons.setDoubleBuffered(true);
        combatButtons.setBackground(bgColor);

        attackButton = new JButton("ATTACK");
        attackButton.setFont(normalFont);
        attackButton.setForeground(txtColor);
        attackButton.setBackground(bgColor);
        attackButton.setBounds(10, 0, screenWidth/5, screenHeight/12);
        attackButton.addActionListener(combat);
        combatButtons.add(attackButton);

        specialButton = new JButton("SPECIAL");
        specialButton.setFont(normalFont);
        specialButton.setForeground(txtColor);
        specialButton.setBackground(bgColor);
        specialButton.setBounds(20 + screenWidth/5, 0, screenWidth/5, screenHeight/12);
        specialButton.addActionListener(combat);
        combatButtons.add(specialButton);

        this.add(combatButtons);
        combatButtons.setVisible(false);

        interactButtons = new JLayeredPane();
        interactButtons.setBounds(10, screenHeight - (screenHeight/8), screenWidth - 10, screenHeight - (screenHeight/12));
        interactButtons.setDoubleBuffered(true);
        interactButtons.setBackground(bgColor);

        acceptButton = new JButton("YES");
        acceptButton.setFont(normalFont);
        acceptButton.setForeground(txtColor);
        acceptButton.setBackground(bgColor);
        acceptButton.setBounds(10, 0, screenWidth/5, screenHeight/12);
        acceptButton.addActionListener(interaction);
        interactButtons.add(acceptButton);

        declineButton = new JButton("NO");
        declineButton.setFont(normalFont);
        declineButton.setForeground(txtColor);
        declineButton.setBackground(bgColor);
        declineButton.setBounds(20 + screenWidth/5, 0, screenWidth/5, screenHeight/12);
        declineButton.addActionListener(interaction);
        interactButtons.add(declineButton);

        this.add(interactButtons);
        interactButtons.setVisible(false);

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
    public void spawnRoomOrHallway(final Entity theEntity, final int theX, final int theY, final int theI, final int theJ) {
        roomsAndHallways[theI][theJ] = new JLabel();
        roomsAndHallways[theI][theJ].setBounds(theX,theY,theEntity.getWidth(),theEntity.getHeight());
        roomsAndHallways[theI][theJ].setIcon(theEntity.getSprite());
        layeredPane.add(roomsAndHallways[theI][theJ], theEntity.getLayer());
    }

    public void spawnEntity(final Entity theEntity, final int theI, final int theJ) {
        entities[theI][theJ] = new JLabel();
        entities[theI][theJ].setBounds((((int) Math.ceil((double) theJ / 2) * 26) + ((theJ / 2) * 51) + theEntity.getOffSetJ()), (((int) Math.ceil((double) theI / 2) * 20) + ((theI / 2) * 51) + theEntity.getOffSetI()), theEntity.getWidth(), theEntity.getHeight());
        entities[theI][theJ].setIcon(theEntity.getSprite());
        layeredPane.add(entities[theI][theJ], theEntity.getLayer());
    }

    /**
     * The spawnMonster method is used when the player move to/spawns in a room that is adjacent to a room containing a monster.
     * The contents of a room is only to be revealed when the player comes close enough, so this method is called by the updateRooms
     * method after the player moves.
     *
     * @param theMonster
     * @param theI
     * @param theJ
     */
    public void spawnMonster(final Monster theMonster, final int theI, final int theJ) {
        entities[theI][theJ] = new JLabel();
        entities[theI][theJ].setBounds((((int) Math.ceil((double) theJ / 2) * 26) + ((theJ / 2) * 51) + theMonster.getOffSetJ()), (((int) Math.ceil((double) theI / 2) * 20) + ((theI / 2) * 51) + theMonster.getOffSetI()), theMonster.getWidth(), theMonster.getHeight());
        entities[theI][theJ].setIcon(theMonster.getSprite());
        layeredPane.add(entities[theI][theJ], theMonster.getLayer());
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
    public void spawnPlayer(final Player thePlayer, final int theX, final int theY) {
        player = new JLabel();
        player.setBounds(theX,theY,thePlayer.getWidth(),thePlayer.getHeight());
        player.setIcon(thePlayer.getSprite());
        layeredPane.add(player, thePlayer.getLayer());
        RoomTile myLightRoom = new RoomTile(true);
        myLightRoom.setRoomImage(da.getRoomCode(thePlayer.getCoords().x, thePlayer.getCoords().y));
        roomsAndHallways[thePlayer.getCoords().x][thePlayer.getCoords().y].setIcon(myLightRoom.getSprite());
        updateRooms(thePlayer.getCoords().x, thePlayer.getCoords().y, thePlayer.getRoom());
    }

    public void updateRooms(final int theI, final int theJ, final Room theRoom) {
        RoomTile myLightRoom = new RoomTile(true);
        myLightRoom.setRoomImage(da.getRoomCode(theI, theJ));
        HallwayHorizontal myLightHH = new HallwayHorizontal(true);
        HallwayVertical myLightHV = new HallwayVertical(true);
        if (theRoom.getNorth() != null) {
            if (!theRoom.getNorth().isVisited()) {
                roomsAndHallways[theI - 1][theJ].setIcon(myLightHV.getSprite());
                myLightRoom.setRoomImage(da.getRoomCode(theI - 2, theJ));
                roomsAndHallways[theI - 2][theJ].setIcon(myLightRoom.getSprite());
                if (da.getDungeon()[theI - 2][theJ].getMonster() != null) {
                    spawnMonster(da.getDungeon()[theI - 2][theJ].getMonster(), theI - 2, theJ);
                }
                if (da.getDungeon()[theI - 2][theJ].hasItem()) {
                    spawnEntity(da.getDungeon()[theI - 2][theJ].getItem(), theI - 2, theJ);
                }
                if (da.getDungeon()[theI - 2][theJ].hasItem()) {
                    spawnEntity(da.getDungeon()[theI - 2][theJ].getItem(), theI - 2, theJ);
                }
                if (da.getDungeon()[theI - 2][theJ].getPortal() != null) {
                    spawnEntity(da.getDungeon()[theI - 2][theJ].getPortal(), theI - 2, theJ);
                }
                if (da.getDungeon()[theI - 2][theJ].getTrapDoor() != null) {
                    spawnEntity(da.getDungeon()[theI - 2][theJ].getTrapDoor(), theI - 2, theJ);
                }
            }
        }
        if (theRoom.getEast() != null) {
            if (!theRoom.getEast().isVisited()) {
                roomsAndHallways[theI][theJ + 1].setIcon(myLightHH.getSprite());
                myLightRoom.setRoomImage(da.getRoomCode(theI, theJ + 2));
                roomsAndHallways[theI][theJ + 2].setIcon(myLightRoom.getSprite());
                if (da.getDungeon()[theI][theJ + 2].getMonster() != null) {
                    spawnMonster(da.getDungeon()[theI][theJ + 2].getMonster(), theI, theJ + 2);
                }
                if (da.getDungeon()[theI][theJ + 2].hasItem()) {
                    spawnEntity(da.getDungeon()[theI][theJ + 2].getItem(), theI, theJ + 2);
                }
                if (da.getDungeon()[theI][theJ + 2].getPortal() != null) {
                    spawnEntity(da.getDungeon()[theI][theJ + 2].getPortal(), theI, theJ + 2);
                }
                if (da.getDungeon()[theI][theJ + 2].getTrapDoor() != null) {
                    spawnEntity(da.getDungeon()[theI][theJ + 2].getTrapDoor(), theI, theJ + 2);
                }
            }
        }
        if (theRoom.getWest() != null) {
            if (!theRoom.getWest().isVisited()) {
                roomsAndHallways[theI][theJ - 1].setIcon(myLightHH.getSprite());
                myLightRoom.setRoomImage(da.getRoomCode(theI, theJ - 2));
                roomsAndHallways[theI][theJ - 2].setIcon(myLightRoom.getSprite());
                if (da.getDungeon()[theI][theJ - 2].getMonster() != null) {
                    spawnMonster(da.getDungeon()[theI][theJ - 2].getMonster(), theI, theJ - 2);
                }
                if (da.getDungeon()[theI][theJ - 2].hasItem()) {
                    spawnEntity(da.getDungeon()[theI][theJ - 2].getItem(), theI, theJ - 2);
                }
                if (da.getDungeon()[theI][theJ - 2].getPortal() != null) {
                    spawnEntity(da.getDungeon()[theI][theJ - 2].getPortal(), theI, theJ - 2);
                }
                if (da.getDungeon()[theI][theJ - 2].getTrapDoor() != null) {
                    spawnEntity(da.getDungeon()[theI][theJ - 2].getTrapDoor(), theI, theJ - 2);
                }
            }
        }
        if (theRoom.getSouth() != null) {
            if (!theRoom.getSouth().isVisited()) {
                roomsAndHallways[theI + 1][theJ].setIcon(myLightHV.getSprite());
                myLightRoom.setRoomImage(da.getRoomCode(theI + 2, theJ));
                roomsAndHallways[theI + 2][theJ].setIcon(myLightRoom.getSprite());
                if (da.getDungeon()[theI + 2][theJ].getMonster() != null) {
                    spawnMonster(da.getDungeon()[theI + 2][theJ].getMonster(), theI + 2, theJ);
                }
                if (da.getDungeon()[theI + 2][theJ].hasItem()) {
                    spawnEntity(da.getDungeon()[theI + 2][theJ].getItem(), theI + 2, theJ);
                }
                if (da.getDungeon()[theI + 2][theJ].getPortal() != null) {
                    spawnEntity(da.getDungeon()[theI + 2][theJ].getPortal(), theI + 2, theJ);
                }
                if (da.getDungeon()[theI + 2][theJ].getTrapDoor() != null) {
                    spawnEntity(da.getDungeon()[theI + 2][theJ].getTrapDoor(), theI + 2, theJ);
                }
            }
        }
        da.getPlayer().getRoom().setVisited();
        layeredPane.update(layeredPane.getGraphics());
    }

    /**
     * This method is unused, but required due to the implementation of KeyListener by DungeonGeneration.
     *
     * @param theE The key being typed
     */
    @Override
    public void keyTyped(KeyEvent theE) {
        // Not being used
    }

    /**
     * The keyPressed method overrides the keyPressed method of KeyListener. This allows UI to be
     * updated upon user input. It listens for presses of the W, A, S, and D keys. These keys
     * correspond to the cardinal directions and allow the move the player character throughout the
     * dungeon. If a movement is valid, the method calls movePlayer, passing it the given direction.
     *
     * @param theE The key being pressed
     */
    @Override
    public void keyPressed(KeyEvent theE) {
        if (started) {
            if (theE.getKeyChar() == '`') {
                da.getPlayer().getHeroType().cheatsActivated();
                description.setText("Cheats Activated");
                description.update(description.getGraphics());
                cheatsActivated = true;
            }
            if (!cheatsActivated) {
                if (!combatButtons.isVisible() && !interactButtons.isVisible()) {
                    switch (theE.getKeyChar()) {
                        case 'w':
                            if (da.getPlayer().getRoom().getNorth() != null) {
                                movePlayer(0);
                            }
                            break;
                        case 'd':
                            if (da.getPlayer().getRoom().getEast() != null) {
                                movePlayer(1);
                            }
                            break;
                        case 's':
                            if (da.getPlayer().getRoom().getSouth() != null) {
                                movePlayer(2);
                            }
                            break;
                        case 'a':
                            if (da.getPlayer().getRoom().getWest() != null) {
                                movePlayer(3);
                            }
                            break;
                    }
                }
            } else {
                switch (theE.getKeyChar()) {
                    case 'w':
                        if (da.getPlayer().getRoom().getNorth() != null) {
                            movePlayer(0);
                        }
                        break;
                    case 'd':
                        if (da.getPlayer().getRoom().getEast() != null) {
                            movePlayer(1);
                        }
                        break;
                    case 's':
                        if (da.getPlayer().getRoom().getSouth() != null) {
                            movePlayer(2);
                        }
                        break;
                    case 'a':
                        if (da.getPlayer().getRoom().getWest() != null) {
                            movePlayer(3);
                        }
                        break;
                }
            }
        }
    }

    /**
     * This method is unused, but required due to the implementation of KeyListener by DungeonGeneration.
     *
     * @param theE The key being released
     */
    @Override
    public void keyReleased(KeyEvent theE) {
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
    private void movePlayer (final int theDirection){
        description.setText("");
        int myOffsetJ = 0;
        int myOffsetI = 0;
        switch (theDirection) {
            case 0 -> myOffsetI = -2;
            case 1 -> myOffsetJ = 2;
            case 2 -> myOffsetI = 2;
            case 3 -> myOffsetJ = -2;
        }

        player.setBounds((((int) Math.ceil(((double) da.getPlayer().getCoords().y + myOffsetJ)/ 2) * 26) + (((da.getPlayer().getCoords().y + myOffsetJ)/ 2) * 51) + da.getPlayer().getOffSetJ()),
                            (((int) Math.ceil(((double) da.getPlayer().getCoords().x + myOffsetI)/2) * 20) + ((((da.getPlayer().getCoords().x + myOffsetI) / 2)) * 51) + da.getPlayer().getOffSetI()), da.getPlayer().getWidth(), da.getPlayer().getHeight());
        da.getPlayer().setCoords(new Point(da.getPlayer().getCoords().x + myOffsetI, da.getPlayer().getCoords().y + myOffsetJ));
//        this.update(this.getGraphics());
        layeredPane.update(layeredPane.getGraphics());
        switch (theDirection) {
            case 0 -> da.getPlayer().setRoom(da.getPlayer().getRoom().getNorth());
            case 1 -> da.getPlayer().setRoom(da.getPlayer().getRoom().getEast());
            case 2 -> da.getPlayer().setRoom(da.getPlayer().getRoom().getSouth());
            case 3 -> da.getPlayer().setRoom(da.getPlayer().getRoom().getWest());
        }
        System.out.println(da.getPlayer().getCoords().x + " : " + da.getPlayer().getCoords().y);
        // If the room contains a Key
        Key myKey = da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getKey();
        Potion myPotion = da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getPotion();
        Monster myMonster = da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster();
        TrapDoor myTrapDoor = da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getTrapDoor();
        Portal myPortal = da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getPortal();

        if (myTrapDoor != null) {
            description.setText("AT YOUR FEET, YOU DISCOVER A TRAP DOOR!\n");
            if (myTrapDoor.isOpened()) {
                description.append("DESCEND?");
            } else {
                description.append("IT'S LOCKED TIGHT. IT LOOKS LIKE A GREY KEY COULD FIT THE LOCK");
            }
            boolean myTest = false;
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] != null){
                    if (keys[i].getColor().equals("Grey")) {
                        description.append("\nYOU HAVE THE KEY TO OPEN THIS DOOR.\nUSE KEY?");
                        interactButtons.setVisible(true);
                        myTest = true;
                        break;
                    }
                }
            }
            if (!myTest) {
                System.out.println("No grey key in inventory");
            }
        }

        if (!da.getPlayer().getRoom().isVisited()) {
            // If the room contains a key
            if (myKey != null) {
                description.setText(myKey.getName().toUpperCase() + " COLLECTED!");
                for (int i = 0; i < keys.length; i++) {
                    if (keys[i] == null) {
                        keys[i] = myKey;
                        break;
                    }
                }
                addToInventory(da.getPlayer().getCoords().x, da.getPlayer().getCoords().y, 0);
                da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].removeKey();
                // If the room contains a potion
            } else if (myPotion != null) {
                description.setText(myPotion.getName().toUpperCase() + " COLLECTED!");
                da.getPlayer().addPotion(myPotion);
                if (inventory[1][9] == null) {
                    addToInventory(da.getPlayer().getCoords().x, da.getPlayer().getCoords().y, 1);
                } else {
                    description.setText("YOU TRY TO FIT THE POTION INTO YOUR OVERFLOWING BAG,\nBREAKING IT IN THE PROCESS");
                }
                entities[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y] = null;
                da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].removePotion();
            // If the room contains a monster
            } else if (myMonster != null) {
                description.setText("YOU ENCOUNTER A MONSTER!");
                Player myPlayer = da.getPlayer();
                player.setBounds((((int) Math.ceil(((double) myPlayer.getCoords().y) / 2) * 26) + (((myPlayer.getCoords().y) / 2) * 51) + myPlayer.getOffSetJ() - 16),
                        (((int) Math.ceil(((double) myPlayer.getCoords().x) / 2) * 20) + ((((myPlayer.getCoords().x) / 2)) * 51) + myPlayer.getOffSetI()), myPlayer.getWidth(), myPlayer.getHeight());
                entities[myPlayer.getCoords().x][myPlayer.getCoords().y].setBounds((((int) Math.ceil(((double) myPlayer.getCoords().y) / 2) * 26) + (((myPlayer.getCoords().y) / 2) * 51) + myMonster.getOffSetJ() + 16),
                        (((int) Math.ceil(((double) myPlayer.getCoords().x) / 2) * 20) + ((((myPlayer.getCoords().x) / 2)) * 51) + myMonster.getOffSetI()), myMonster.getWidth(), myMonster.getHeight());
                layeredPane.update(layeredPane.getGraphics());
                combatButtons.setVisible(true);
                setCurrentAction(myPlayer.getHeroType(), myMonster);
            }
            updateRooms(da.getPlayer().getCoords().x, da.getPlayer().getCoords().y, da.getPlayer().getRoom());
        }
        layeredPane.update(layeredPane.getGraphics());
    }

    private void setCurrentAction(final Hero thePlayer, final Monster theMonster) {
        int atk = thePlayer.getAtkSpd() % theMonster.getAtkSpd();
        if (atk <= 0) {
            atk = 1;
        }
        currentAction = atk;
    }

    private void battle(final int attackType) throws InterruptedException {
        if (attackType > 1 || attackType < 0) {
            return;
        }
        if (attackType == 0) {
            description.setText(da.getPlayer().getHeroType().attack(da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster()).toUpperCase());
        } else {
            description.setText(da.getPlayer().getHeroType().special(da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster()).toUpperCase());
        }
        description.update(description.getGraphics());
        currentAction--;
        Thread.sleep(2000);
        if (da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getHP() <= 0) {
            description.setText("THE " + da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getName().toUpperCase() + " WAS DEFEATED!");
            entities[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].setVisible(false);
            da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].killMonster();
            player.setBounds((((int) Math.ceil(((double) da.getPlayer().getCoords().y)/ 2) * 26) + (((da.getPlayer().getCoords().y)/ 2) * 51) + da.getPlayer().getOffSetJ()),
                    (((int) Math.ceil(((double) da.getPlayer().getCoords().x)/2) * 20) + ((((da.getPlayer().getCoords().x) / 2)) * 51) + da.getPlayer().getOffSetI()), da.getPlayer().getWidth(), da.getPlayer().getHeight());
            description.update(description.getGraphics());
            currentAction = 0;
            combatButtons.setVisible(false);
        } else {
            if (currentAction <= 0) {
                description.setText(da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().attack(da.getPlayer().getHeroType()).toUpperCase());
                description.update(description.getGraphics());
                if (da.getPlayer().getHeroType().getHP() <= 0) {
                    Thread.sleep(2000);
                    description.setText("THE " + da.getPlayer().getHeroType().getName().toUpperCase() + " WAS DEFEATED!");
                    player.setVisible(false);
                    entities[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].setBounds((((int) Math.ceil(((double) da.getPlayer().getCoords().y)/ 2) * 26) + (((da.getPlayer().getCoords().y)/ 2) * 51) + da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getOffSetJ()),
                            (((int) Math.ceil(((double) da.getPlayer().getCoords().x)/2) * 20) + ((((da.getPlayer().getCoords().x) / 2)) * 51) + da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getOffSetI()),
                            da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getWidth(), da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getHeight());
                    description.update(description.getGraphics());
                    // setGameOverScreen();
                    currentAction = 0;
                    combatButtons.setVisible(false);
                }
            } else {
                description.setText("THE " + da.getPlayer().getHeroType().getName().toUpperCase() + " WAS FASTER THAN "
                        + da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster().getName().toUpperCase() + " AND GETS IN AN EXTRA ACTION");
                description.update(description.getGraphics());
            }
        }
        Thread.sleep(2000);
        if (currentAction == 0 && da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster() != null) {
            setCurrentAction(da.getPlayer().getHeroType(), da.getDungeon()[da.getPlayer().getCoords().x][da.getPlayer().getCoords().y].getMonster());
        }
    }

    public void addToInventory(final int theX, final int theY, int theRow) {
        entities[theX][theY].setIcon(new ImageIcon(((ImageIcon) entities[theX][theY].getIcon()).getImage().getScaledInstance(entities[theX][theY].getWidth()*2, entities[theX][theY].getHeight()*2, Image.SCALE_SMOOTH)));
        entities[theX][theY].setSize(entities[theX][theY].getWidth()*2, entities[theX][theY].getHeight()*2);
        for (int i = 0; i < inventory[theRow].length; i++) {
            if (inventory[theRow][i] == null) {
                inventory[theRow][i] = entities[theX][theY];
                inventory[theRow][i].setLocation(layeredPane.getWidth() - 80 - (i * 52), 40 + (58 * theRow));
                i = inventory[theRow].length;
            }
        }
        entities[theX][theY] = null;
        layeredPane.update(layeredPane.getGraphics());
    }

    public void removeFromInventory(final int theCol, final int theRow) {
        int myCount = 0;
        keys[theCol] = null;
        inventory[theRow][theCol] = null;
        if (theCol < keys.length - 1) {
            for (int i = theCol; i < keys.length; i++) {
                if (keys[i + 1] != null ) {
                    keys[i] = keys[i + 1];
                    inventory[0][i] = inventory[0][i + 1];
                    if (inventory[0][i] != null) {
                        inventory[0][i].setLocation(layeredPane.getWidth() - 80 - (i * 52), 40 + (58 * theRow));
                    }
                } else {
                    keys[i] = null;
                    inventory[0][i] = null;
                    break;
                }
            }
        }
    }

    private void descend(final int theKey) {
        removeFromInventory(theKey, 0);
        roomsAndHallways = new JLabel[22][22];
        entities = new JLabel[22][22];
        layeredPane.removeAll();
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[0].length; j++) {
                if (inventory[i][j] != null) {
                    layeredPane.add(inventory[i][j]);
                }
            }
        }
        layeredPane.update(layeredPane.getGraphics());
        da.generateNewDungeon();
        this.add(layeredPane);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                System.out.println(keys[i].getColor());
            }
        }
    }

    private class gameStartHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent theE) {
            Object source = theE.getSource();
            if (!started) {
                if (knightButton.equals(source)) {
                    da.setPlayerClass(0);
                } else if (priestessButton.equals(source)) {
                    da.setPlayerClass(1);
                } else if (thiefButton.equals(source)) {
                    da.setPlayerClass(2);
                } else if (startButton.equals(source)) {
                    if (da.getPlayerClass() != -1) {
                        startGame();
                        started = true;
                    }
                }
            }
        }
    }

    private class interactionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent theE) {
            Object source = theE.getSource();
            if (acceptButton.equals(source)) {
                boolean myHasKey = false;
                int myCount = 0;
                for (int i = 0; i < keys.length; i++) {
                    if (keys[i] != null) {
                        if (keys[i].getColor().equals("Grey")) {
                            myHasKey = true;
                            myCount = i;
                            break;
                        }
                    }
                }
                if (myHasKey) {
                    descend(myCount);
                    interactButtons.setVisible(false);
                    description.setText("");
                } else {
                    description.setText("YOU HAVE NOT FOUND A GREY KEY YET");
                }
            } else {
                interactButtons.setVisible(false);
                description.setText("");
            }
        }
    }

    private class combatHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            try {
                if (attackButton.equals(source)) {
                    battle(0);
                } else if (specialButton.equals(source)) {
                    battle(1);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
