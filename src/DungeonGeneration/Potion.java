
import javax.swing.*;

/**
 * This class represents the potions found throughout the dungeon.
 *
 * @author Nathan Mahnke
 */
public class Potion extends Entity {

    /**
     * This is the potion constructor. It calls the Entity constructor and passes it relevant information.
     */
    public Potion() {
        super("Potion", 17, 17, new ImageIcon(Potion.class.getResource("Assets/PotionFull.png")), 200, 17, 17);
    }

}
