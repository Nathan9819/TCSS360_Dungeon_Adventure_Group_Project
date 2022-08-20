
import javax.swing.*;
import java.util.Objects;

/**
 * This is the parent class for all game-objects. It contains the width, height,
 * icon, layer, offsets, and name of the object.
 *
 * @author Nathan Mahnke
 */

public class Entity {
    private int width, height;
    private ImageIcon sprite;
    private final Integer layer;
    private int offSetI, offSetJ;
    private String itemName;

    /**
     * This is one of the Entity constructors. It assigns values to all key variables.
     *
     * @param theItemName A String representing the Object's name
     * @param theWidth    An int representing the Object's width
     * @param theHeight   An int representing the Object's height
     * @param theSprite   An ImageIcon representing the Object's sprite
     * @param theLayer    An Integer representing the Object's layer for placement on a JLayeredPane
     * @param theOffSetI  An int representing the Object's horizontal offset
     * @param theOffSetJ  An int representing the Object's vertical offset
     */
    public Entity(final String theItemName, final int theWidth, final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        itemName = theItemName;
        width = theWidth;
        height = theHeight;
        sprite = theSprite;
        layer = theLayer;
        offSetI = theOffSetI;
        offSetJ = theOffSetJ;
    }

    /**
     * This is the second of the Entity constructors. It assigns values only to the width,
     * height, and layer of the object. This is used when spawning RoomTiles, HallwayVerticals,
     * and HallwayHorizontals due to their need to assign their sprite after their creation.
     *
     * @param theWidth  An int representing the Object's width
     * @param theHeight An int representing the Object's height
     * @param theLayer  An Integer representing the Object's layer for placement on a JLayeredPane
     */
    public Entity(final int theWidth, final int theHeight, final Integer theLayer) {
        width = theWidth;
        height = theHeight;
        layer = theLayer;
    }

    /**
     * getWidth returns the width of the Entity.
     *
     * @return An integer representing the Entity's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * setWidth sets the width of the Entity to the given int, provided
     * the int is not negative.
     *
     * @param theWidth An int representing the width to be assigned to the Entity
     */
    public void setWidth(final int theWidth) {
        if (theWidth > 0) {
            width = theWidth;
        }
    }

    /**
     * getHeight returns the height of the Entity.
     *
     * @return An integer representing the Entity's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * setHeight sets the height of the Entity to the given int, provided
     * the int is not negative.
     *
     * @param theHeight An int representing the height to be assigned to the Entity
     */
    public void setHeight(final int theHeight) {
        if (theHeight > 0) {
            height = theHeight;
        }
    }

    /**
     * getSprite returns the Entity's sprite (ImageIcon).
     *
     * @return An ImageIcon used to visually represent the Entity
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * setSprite receives an ImageIcon and assigns it to the Entity.
     *
     * @param theSprite The new ImageIcon to be applied to the Entity
     */
    public void setSprite(final ImageIcon theSprite) {
        this.sprite = theSprite;
    }

    /**
     * getLayer returns the Integer representing the layer the Entity
     * is to be drawn on when being displayed on a JLayeredPane.
     *
     * @return An Integer representing the layer
     */
    public Integer getLayer() {
        return layer;
    }

    /**
     * getOffSetI returns the offSetI.
     *
     * @return An int representing the I-offset
     */
    public int getOffSetI() {
        return offSetI;
    }

    /**
     * getOffSetJ returns the offSetJ.
     *
     * @return An int representing the J-offset
     */
    public int getOffSetJ() {
        return offSetJ;
    }

    /**
     * getName returns a String representing the Entity's name.
     *
     * @return A String representing the Entity's name
     */
    public String getName() {
        return Objects.requireNonNullElse(itemName, "Room or Hallway Object");
    }
}
