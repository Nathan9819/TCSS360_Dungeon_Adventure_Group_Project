import javax.swing.*;
import java.net.URL;

/**
 * This class represents a horizontal hallway tile. It holds information regarding the hallway's
 * size, layer, visibility, and image.
 * @Author Nathan Mahnke
 */
public class HallwayHorizontal extends Entity{
    boolean light;

    /**
     * This is the HallwayHorizontal constructor. It sets the height and width of the hallway, its layer, and
     * its visibility.
     *
     * @param isLight Whether the tile should be lit up or darkened
     */
    public HallwayHorizontal(boolean isLight) {
        height = 32;
        width = 27;
        layer = 100;
        light = isLight;
        URL url = getClass().getResource(light ? "Assets/HallwayHorizontal.png" : "Assets/DarkHallwayHorizontal.png");
        sprite = new ImageIcon(url);
    }
}