import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents the player. It contains information regarding the player character's
 * height/width, coordinates, image, and layer.
 * @author Nathan Mahnke
 */

public class Player {
    private int width;
    private int height;
    private final ImageIcon sprite;
    private final Integer layer;
    private Point coords;
    private Room room;
    private Hero heroType;
    private int offSetI;
    private int offSetJ;

    /**
     * Player is the constructor of the player object. It accepts integers i and j so that the player object
     * may store its location within a 2d array. The integer c is used to decide the class of the player;
     * knight, priestess, or thief.
     *
     * This constructor sets the proper player image for the given class as well as ensuring the image will line
     * up properly with the rooms of the dungeon. It also sets the player's layer as, during its addition to the
     * LayeredPane, it will be used to decide what z height the image is showed on.
     *
     * @param theI     The vertical coordinate
     * @param theJ     The horizontal coordinate
     * @param theClass The class of the player
     */
    public Player(int theI, int theJ, int theClass) {
        URL myUrl = null;
        switch (theClass) {
            case 0 -> {
                myUrl = getClass().getResource("Assets/Knight.png");
                heroType = new Warrior();
                height = 45;
                width = 31;
                offSetI = -9;
                offSetJ = 8;
            }
            case 1 -> {
                myUrl = getClass().getResource("Assets/Priestess.png");
                heroType = new Priestess();
                height = 46;
                width = 31;
                offSetI = -9;
                offSetJ = 12;
            }
            case 2 -> {
                myUrl = getClass().getResource("Assets/Thief.png");
                heroType = new Thief();
                height = 45;
                width = 27;
                offSetI = -9;
                offSetJ = 12;
            }
            case 3 -> {
                myUrl = getClass().getResource("Assets/Gremlin.png");
                heroType = new Thief();
                height = 42;
                width = 40;
                offSetI = -6;
                offSetJ = 7;
            }
        }
        assert myUrl != null;
        sprite = new ImageIcon(myUrl);
        layer = 200;
        setCoords(new Point(theI, theJ));
    }

    /**
     * getWidth returns the width of the Player.
     *
     * @return An int representing the Player's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * getHeight returns the height of the Player.
     *
     * @return An int representing the Player's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * getSprite returns the Player's sprite (ImageIcon).
     *
     * @return The ImageIcon used to represent the Player
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * getLayer returns the Player's layer.
     *
     * @return An Integer representing the Player's layer
     */
    public Integer getLayer() {
        return layer;
    }

    /**
     * getCoords returns the Player's coordinates.
     *
     * @return A Point representing the Player's coordinates
     */
    public Point getCoords() {
        return coords;
    }

    /**
     * getRoom returns the Room which the Player is in.
     *
     * @return The Room object the Player is currently 'in'
     */
    public Room getRoom() {
        return room;
    }

    /**
     * getHeroType returns the Hero Object stored in heroType. This
     * is the Hero that represents the Player and is used when battling.
     *
     * @return The Hero which represents the Player
     */
    public Hero getHeroType() {
        return heroType;
    }

    /**
     * getOffSetI returns the Player's I-offset.
     *
     * @return An int representing the Player's I-offset
     */
    public int getOffSetI() {
        return offSetI;
    }

    /**
     * getOffSetJ returns the Player's current J-offset.
     *
     * @return An int representing the Player's J-offset
     */
    public int getOffSetJ() {
        return offSetJ;
    }

    /**
     * setCoords receives a new Point and assigns it to
     * the variable coords which represents the Player's
     * current location.
     *
     * @param theCoords A Point representing the Player's new location
     */
    public void setCoords(final Point theCoords) {
        coords = theCoords;
    }

    /**
     * setRoom receives a new Room Object and assigns it to
     * the variable room. This represents the Room which the
     * Player is currently in.
     *
     * @param theRoom The Player's new Room
     */
    public void setRoom(final Room theRoom) {
        room = theRoom;
    }
}