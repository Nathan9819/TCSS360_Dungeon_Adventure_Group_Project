
import javax.swing.*;
import java.net.URL;

/**
 * This class represents the portal found at the end of the dungeon. It is used to reach
 * the final boss of the game.
 *
 * @author Nathan Mahnke
 */
public class Portal extends Entity{

    /**
     * This is the Portal constructor. It calls the Entity constructor and passes relevant information before assigning
     * the proper ImageIcon to itself based on the boolean it receives which represents whether the portal is opened or
     * closed.
     *
     * @param theBoolean A boolean representing whether the portal is opened or closed
     */
    public Portal(final boolean theBoolean) {
        super(52, 52, 200);
        super.setSprite(new ImageIcon(getClass().getResource(theBoolean ? "Assets/PortalOpen.png" : "Assets/PortalClosed.png")));
    }
}