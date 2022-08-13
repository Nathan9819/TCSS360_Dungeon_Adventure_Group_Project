
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class represents the player. It contains information regarding the player character's
 * height/width, coordinates, image, and layer.
 * @Author Nathan Mahnke
 */

public class Player {
    private ArrayList<Key> keys = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
//    private ArrayList<Bomb> bombs = new ArrayList<>();
    private int width;
    private int height;
    private ImageIcon sprite;
    private Integer layer;
    private Point coords;
    private Room room;
    private Hero heroType;
    private int offSetI;
    private int offSetJ;

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
            case 3 -> {
                myUrl = getClass().getResource("DungeonGeneration/Assets/Gremlin.png");
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    public Integer getLayer() {
        return layer;
    }

    public Point getCoords() {
        return coords;
    }

    public Room getRoom() {
        return room;
    }

    public Hero getHeroType() {
        return heroType;
    }

    public int getOffSetI() {
        return offSetI;
    }

    public int getOffSetJ() {
        return offSetJ;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addKey(Key theKey) {
        if (theKey != null) {
            keys.add(theKey);
        } else {
            System.out.println("Null key");
        }
    }

    public void addPotion(Potion thePotion) {
        if (thePotion != null) {
            potions.add(thePotion);
        } else {
            System.out.println("Null potion");
        }
    }

    public void usePotion() {
        if (!potions.isEmpty()) {
            potions.remove(potions.size() - 1);
//            heroType.usePotion();
        }
    }
}