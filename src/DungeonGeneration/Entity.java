
import javax.swing.*;

/**
 * This is the parent class for all game-objects. It contains the width and height
 * of the game object as well as its image and layer.
 * @Author Nathan Mahnke
 */

public class Entity {
    private int width, height;
    private ImageIcon sprite;
    private Integer layer;
    private int offSetI, offSetJ;
    private String itemName;

    public Entity(final String theItemName, final int theWidth, final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        itemName = theItemName;
        width = theWidth;
        height = theHeight;
        sprite = theSprite;
        layer = theLayer;
        offSetI = theOffSetI;
        offSetJ = theOffSetJ;
    }

    public Entity(int theWidth, int theHeight, Integer theLayer) {
        width = theWidth;
        height = theHeight;
        layer = theLayer;
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

    public void setSprite(ImageIcon theSprite) {
        this.sprite = theSprite;
    }

    public int getOffSetI() {
        return offSetI;
    }

    public void setOffSetI(int theOffSetI) {
        offSetI = theOffSetI;
    }

    public int getOffSetJ() {
        return offSetJ;
    }

    public void setOffSetJ(int theOffsetJ) {
        offSetJ = theOffsetJ;
    }

    public String getName() {
        if (itemName != null) {
            return itemName;
        } else {
            return "Room or Hallway Object";
        }
    }
}
