
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

    public Entity(int theWidth, int theHeight, ImageIcon theSprite, Integer theLayer) {
        width = theWidth;
        height = theHeight;
        sprite = theSprite;
        layer = theLayer;
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
}
