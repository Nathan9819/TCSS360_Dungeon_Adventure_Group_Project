
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
    private boolean started = false;
    private int screenWidth, screenHeight;
    private JLayeredPane titleScreen;
    private JLayeredPane layeredPane;
    private Font titleFont, normalFont;
    private Color bgColor, txtColor;
    private JLabel titleLabel;
    private JButton startButton, knightButton, priestessButton, thiefButton;
    private JTextArea description;
    private gameStartHandler gameStart = new gameStartHandler();

    public JLabel[][] roomsAndHallways = new JLabel[22][22];
    public JLabel[][] monsters = new JLabel[22][22];
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

    public void mainMenu() {
        try {
            InputStream is = getClass().getResourceAsStream("DungeonGeneration/Assets/MaruMonica.ttf");
            titleFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 70f);
            normalFont = titleFont.deriveFont(40f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Font error!");
        }
        titleScreen = new JLayeredPane();
        titleScreen.setBounds(0, 0, screenWidth, screenHeight);
        this.add(titleScreen);
//        layeredPane = new JLayeredPane();
//        layeredPane.setBounds(0, 0, screenWidth, screenHeight - (screenHeight/3));
//        this.add(layeredPane);
        titleLabel = new JLabel("DUNGEON ADVENTURE", SwingConstants.CENTER);
        txtColor = new Color(62,39,49);
        titleLabel.setForeground(txtColor);
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(screenWidth/4, (int) ((double) screenHeight/6), (int) ((double) screenWidth/2.5), screenHeight/8);
        titleScreen.add(titleLabel, 0);


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

    public void startGame() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, screenWidth, screenHeight - (screenHeight/3));
        this.add(layeredPane);
        titleScreen.setVisible(false);
        description = new JTextArea("You find yourself lost and alone. The only way out . . . is through!");
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(normalFont);
        description.setBounds(10, screenHeight - (screenHeight/3), screenWidth, screenHeight/3);
        description.setBackground(bgColor);
        description.setForeground(txtColor);
//        description.setEditable(false);
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
        roomsAndHallways[theI][theJ] = new JLabel();
        roomsAndHallways[theI][theJ].setBounds(theX,theY,theEntity.getWidth(),theEntity.getHeight());
        roomsAndHallways[theI][theJ].setIcon(theEntity.getSprite());
        layeredPane.add(roomsAndHallways[theI][theJ], theEntity.getLayer());
    }

    public void spawnMonster(Monster monster, int theX, int theY, int theI, int theJ) {
        monsters[theI][theJ] = new JLabel();
        monsters[theI][theJ].setBounds(theX,theY,monster.getWidth(),monster.getHeight());
        monsters[theI][theJ].setIcon(monster.getSprite());
        layeredPane.add(monsters[theI][theJ], monster.getLayer());
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
        player.setBounds(theX,theY,thePlayer.getWidth(),thePlayer.getHeight());
        player.setIcon(thePlayer.getSprite());
        layeredPane.add(player, thePlayer.getLayer());
        setLight(thePlayer.getCoords().x, thePlayer.getCoords().y, thePlayer.getRoom());
    }

    public void setLight(int theI, int theJ, Room theRoom) {
        RoomTile myLightRoom = new RoomTile(true);
        myLightRoom.setRoomImage(da.getRoomCode(theI, theJ));
        HallwayHorizontal myLightHH = new HallwayHorizontal(true);
        HallwayVertical myLightHV = new HallwayVertical(true);
        roomsAndHallways[theI][theJ].setIcon(myLightRoom.getSprite());
        if (theRoom.north != null) {
            roomsAndHallways[theI - 1][theJ].setIcon(myLightHV.getSprite());
            myLightRoom.setRoomImage(da.getRoomCode(theI - 2, theJ));
            roomsAndHallways[theI - 2][theJ].setIcon(myLightRoom.getSprite());
        }
        if (theRoom.east != null) {
            roomsAndHallways[theI][theJ + 1].setIcon(myLightHH.getSprite());
            myLightRoom.setRoomImage(da.getRoomCode(theI, theJ + 2));
            roomsAndHallways[theI][theJ + 2].setIcon(myLightRoom.getSprite());
        }
        if (theRoom.west != null) {
            roomsAndHallways[theI][theJ - 1].setIcon(myLightHH.getSprite());
            myLightRoom.setRoomImage(da.getRoomCode(theI, theJ - 2));
            roomsAndHallways[theI][theJ - 2].setIcon(myLightRoom.getSprite());
        }
        if (theRoom.south != null) {
            roomsAndHallways[theI + 1][theJ].setIcon(myLightHV.getSprite());
            myLightRoom.setRoomImage(da.getRoomCode(theI + 2, theJ));
            roomsAndHallways[theI + 2][theJ].setIcon(myLightRoom.getSprite());
        }
        da.p.getRoom().visited = true;
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
        if (started) {
            switch (e.getKeyChar()) {
                case 'w':
                    if (da.p.getRoom().north != null) {
                        movePlayer(0);
                        System.out.println("w pressed");
                    }
                    break;
                case 'd':
                    if (da.p.getRoom().east != null) {
                        movePlayer(1);
                        System.out.println("d pressed");
                    }
                    break;
                case 's':
                    if (da.p.getRoom().south != null) {
                        movePlayer(2);
                        System.out.println("s pressed");
                    }
                    break;
                case 'a':
                    if (da.p.getRoom().west != null) {
                        movePlayer(3);
                        System.out.println("a pressed");
                    }
                    break;
            }
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

        player.setBounds((((int) Math.ceil(((double) da.p.getCoords().y + myOffsetJ)/ 2) * 26) + (((da.p.getCoords().y + myOffsetJ)/ 2) * 51) + da.p.getOffSetJ()),
                            (((int) Math.ceil(((double) da.p.getCoords().x + myOffsetI)/2) * 20) + ((((da.p.getCoords().x + myOffsetI) / 2)) * 51) + da.p.getOffSetI()), da.p.getWidth(), da.p.getHeight());
        da.p.setCoords(new Point(da.p.getCoords().x + myOffsetI, da.p.getCoords().y + myOffsetJ));
        this.update(this.getGraphics());
        switch (theDirection) {
            case 0 -> da.p.setRoom(da.p.getRoom().north);
            case 1 -> da.p.setRoom(da.p.getRoom().east);
            case 2 -> da.p.setRoom(da.p.getRoom().south);
            case 3 -> da.p.setRoom(da.p.getRoom().west);
        }
        Monster monster = da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster();
        if (monster != null) {
            Player myPlayer = da.p;
            player.setBounds((((int) Math.ceil(((double) myPlayer.getCoords().y)/ 2) * 26) + (((myPlayer.getCoords().y)/ 2) * 51) + myPlayer.getOffSetJ() - 16),
                    (((int) Math.ceil(((double) myPlayer.getCoords().x)/2) * 20) + ((((myPlayer.getCoords().x) / 2)) * 51) + myPlayer.getOffSetI()), myPlayer.getWidth(), myPlayer.getHeight());
            monsters[myPlayer.getCoords().x][myPlayer.getCoords().y].setBounds((((int) Math.ceil(((double) myPlayer.getCoords().y)/ 2) * 26) + (((myPlayer.getCoords().y)/ 2) * 51) + monster.getOffSetJ() + 16),
                    (((int) Math.ceil(((double) myPlayer.getCoords().x)/2) * 20) + ((((myPlayer.getCoords().x) / 2)) * 51) + monster.getOffSetI()), monster.getWidth(), monster.getHeight());
            this.update(this.getGraphics());
            try {
                doBattle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!da.p.getRoom().visited) {
            setLight(da.p.getCoords().x, da.p.getCoords().y, da.p.getRoom());
        }
        refresh();
    }

    public void doBattle() throws InterruptedException {
        boolean cont = true;
        while (cont) {
            String myText = da.p.getHeroType().attack(da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster());
            System.out.println(myText);
            description.setText(myText);
            description.update(description.getGraphics());
            Thread.sleep(1000);
            if (da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().getHP() <= 0) {
                cont = false;
                description.setText("The beast is slane!");
                monsters[da.p.getCoords().x][da.p.getCoords().y].setVisible(false);
                player.setBounds((((int) Math.ceil(((double) da.p.getCoords().y)/ 2) * 26) + (((da.p.getCoords().y)/ 2) * 51) + da.p.getOffSetJ()),
                        (((int) Math.ceil(((double) da.p.getCoords().x)/2) * 20) + ((((da.p.getCoords().x) / 2)) * 51) + da.p.getOffSetI()), da.p.getWidth(), da.p.getHeight());
                this.update(this.getGraphics());
            } else {
                myText = da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().attack(da.p.getHeroType());
                System.out.println(myText);
                description.setText(myText);
                description.update(description.getGraphics());
                Thread.sleep(1000);
                if (da.p.getHeroType().getHP() <= 0) {
                    cont = false;
                    description.setText("The hero was felled!");
                    player.setVisible(false);
                    monsters[da.p.getCoords().x][da.p.getCoords().y].setBounds((((int) Math.ceil(((double) da.p.getCoords().y)/ 2) * 26) + (((da.p.getCoords().y)/ 2) * 51) + da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().getOffSetJ()),
                            (((int) Math.ceil(((double) da.p.getCoords().x)/2) * 20) + ((((da.p.getCoords().x) / 2)) * 51) + da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().getOffSetI()), da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().getWidth(), da.dungeon[da.p.getCoords().x][da.p.getCoords().y].getMonster().getHeight());
                    this.update(this.getGraphics());
                }
            }

        }
    }

    public class gameStartHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (knightButton.equals(source)) {
                da.setPlayerClass(0);
            } else if (priestessButton.equals(source)) {
                da.setPlayerClass(1);
            } else if (thiefButton.equals(source)) {
                da.setPlayerClass(2);
            } else if (startButton.equals(source)){
                if (da.getPlayerClass() != -1) {
                    startGame();
                    started = true;
                }
            }
        }
    }
}
