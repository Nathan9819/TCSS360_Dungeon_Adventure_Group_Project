
import javax.swing.*;

/**
 * This class represents a horizontal hallway tile. It holds information regarding the hallway's
 * size, layer, visibility, and image.
 *
 * @author Nathan Mahnke
 */
public class HallwayHorizontal extends Entity{

    /**
     * This is the HallwayHorizontal constructor. It sets the height and width of the hallway, its layer, and
     * its visibility.
     *
     * @param theLight Whether the tile should be lit up or darkened
     */
    public HallwayHorizontal(final boolean theLight) {
        super(27, 32, 100);
        super.setSprite(new ImageIcon(getClass().getResource(theLight ? "DungeonGeneration/Assets/HallwayHorizontal.png" : "DungeonGeneration/Assets/DarkHallwayHorizontal.png")));
    }
}