
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
     * This is the DungeonGeneration.HallwayHorizontal constructor. It sets the height and width of the hallway, its layer, and
     * its visibility.
     *
     * @param theLight Whether the tile should be lit up or darkened
     */
    public HallwayHorizontal(final boolean theLight) {
        super(27, 32, 100);
        isLight = theLight;
        URL myUrl = getClass().getResource(isLight ? "DungeonGeneration/Assets/HallwayHorizontal.png" : "DungeonGeneration/Assets/DarkHallwayHorizontal.png");
        assert myUrl != null;
        super.setSprite(new ImageIcon(myUrl));
    }
}