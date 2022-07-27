import javax.swing.*;
import java.net.URL;

/**
 * This class represents a horizontal hallway tile. It holds information regarding the hallway's
 * size, layer, visibility, and image.
 * @Author Nathan Mahnke
 */
public class HallwayHorizontal extends Entity{
    final boolean isLight;

    /**
     * This is the HallwayHorizontal constructor. It sets the height and width of the hallway, its layer, and
     * its visibility.
     *
     * @param theLight Whether the tile should be lit up or darkened
     */
    public HallwayHorizontal(boolean theLight) {
        height = 32;
        width = 27;
        layer = 100;
        isLight = theLight;
        URL myUrl = getClass().getResource(isLight ? "Assets/HallwayHorizontal.png" : "Assets/DarkHallwayHorizontal.png");
        assert myUrl != null;
        sprite = new ImageIcon(myUrl);
    }
}