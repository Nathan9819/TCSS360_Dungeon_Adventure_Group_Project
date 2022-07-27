import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents the player. It contains information regarding the player character's
 * height/width, coordinates, image, and layer.
 * @Author Nathan Mahnke
 */

public class Player extends Entity {
    public Point myCoords;
    public Room myRoom;
    int offSetI;
    int offSetJ;

    /**
     * Player is the constructor of the player object. It accepts integers i and j so that the player object
     * may store its location within a 2d array. The integer c is used to decide the class of the player;
     * knight, priestess, or thief.
     *
     * This constructor sets the proper player image for the given class as well as ensuring the image will line
     * up properly with the rooms of the dungeon. It also sets the player's layer as, during its addition to the
     * LayeredPane, it will be used to decide what z height the image is showed on.
     *
     * @param i The vertical coordinate
     * @param j The horizontal coordinate
     * @param c The class of the player
     */
    public Player(int i, int j, int c) {
        URL url = null;
        switch (c) {
            case 0 :
                url = getClass().getResource("Assets/Knight.png");
                height = 45;
                width = 31;
                offSetI = -9;
                offSetJ = 8;
                break;
            case 1 :
                url = getClass().getResource("Assets/Priestess.png");
                height = 46;
                width = 31;
                offSetI = -9;
                offSetJ = 12;
                break;
            case 2 :
                url = getClass().getResource("Assets/Thief.png");
                height = 45;
                width = 27;
                offSetI = -9;
                offSetJ = 12;
                break;
        }
        sprite = new ImageIcon(url);
        layer = 200;
        myCoords = new Point(i, j);
    }

}