import javax.swing.*;

/**
 * This class represents the trap doors which can be found within the dungeon.
 *
 * @author Nathan Mahnke
 */
public class TrapDoor extends Entity{

    /**
     * This is the TrapDoor constructor. It calls the Entity Constructor, passing it relevant information, then uses super.setSprite
     * to assign itself the proper ImageIcon based on whether the trap door is open or closed.
     *
     * @param theBoolean True if the trap door is open, false if it's closed
     */
    public TrapDoor(final boolean theBoolean) {
        super(52, 52, 200);
        super.setSprite(new ImageIcon(getClass().getResource("Assets/TrapDoor" + (theBoolean ? "Open" : "Closed") + ".png")));
    }
}