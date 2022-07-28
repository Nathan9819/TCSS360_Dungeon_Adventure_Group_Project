
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents the player. It contains information regarding the player character's
 * height/width, coordinates, image, and layer.
 * @Author Nathan Mahnke
 */

public class Player extends Entity {
    public Point coords;
    public Room room;
    public Hero heroType;
    int offSetI;
    int offSetJ;

    /**
     * DungeonGeneration.Player is the constructor of the player object. It accepts integers i and j so that the player object
     * may store its location within a 2d array. The integer c is used to decide the class of the player;
     * knight, priestess, or thief.
     *
     * This constructor sets the proper player image for the given class as well as ensuring the image will line
     * up properly with the rooms of the dungeon. It also sets the player's layer as, during its addition to the
     * LayeredPane, it will be used to decide what z height the image is showed on.
     *
     * @param theI The vertical coordinate
     * @param theJ The horizontal coordinate
     * @param theClass The class of the player
     */
    public Player(int theI, int theJ, int theClass) {
        URL myUrl = null;
        switch (theClass) {
            case 0 -> {
                myUrl = getClass().getResource("DungeonGeneration/Assets/Knight.png");
                heroType = new Warrior();
                height = 45;
                width = 31;
                offSetI = -9;
                offSetJ = 8;
            }
            case 1 -> {
                myUrl = getClass().getResource("DungeonGeneration/Assets/Priestess.png");
                heroType = new Priestess();
                height = 46;
                width = 31;
                offSetI = -9;
                offSetJ = 12;
            }
            case 2 -> {
                myUrl = getClass().getResource("DungeonGeneration/Assets/Thief.png");
                heroType = new Thief();
                height = 45;
                width = 27;
                offSetI = -9;
                offSetJ = 12;
            }
        }
        assert myUrl != null;
        sprite = new ImageIcon(myUrl);
        layer = 200;
        coords = new Point(theI, theJ);
    }

}