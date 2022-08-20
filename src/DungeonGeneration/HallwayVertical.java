
import javax.swing.*;
import java.net.URL;

/**
 * This class represents a vertical hallway tile. It holds information regarding the hallway's
 * size, layer, visibility, and image.
 *
 * @author Nathan Mahnke
 */
public class HallwayVertical extends Entity{

    /**
     * This is the HallwayVertical constructor. It sets the height and width of the hallway, its layer, and
     * its visibility.
     *
     * @param theLight Whether the tile should be lit up or darkened
     */
    public HallwayVertical(boolean theLight) {
        super(24, 27, 100);
        super.setSprite(new ImageIcon(getClass().getResource(theLight ? "DungeonGeneration/Assets/HallwayVertical.png" : "DungeonGeneration/Assets/DarkHallwayVertical.png")));
    }

}