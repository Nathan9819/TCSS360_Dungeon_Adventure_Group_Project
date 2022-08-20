import javax.swing.*;

/**
 * This class represents a Key which can be found in the dungeon. It contains
 * a string representing the color of the key. Different color keys have different
 * purposes in the dungeon.
 *
 * @author Nathan Mahnke
 */
public class Key extends Entity{
    private final String color;

    /**
     * This is the Key constructor. It calls the Entity constructor passing it the proper
     * information and setting its color to the given color.
     *
     * @param theColor A String representing the color of the Key
     */
    public Key (final String theColor) {
        super(theColor + " key", 20, 27,
                new ImageIcon(Key.class.getResource("DungeonGeneration/Assets/" + theColor + "Key.png")), 200, 12, 16);
        color = theColor;
    }

    /**
     * getColor returns the color of the Key
     *
     * @return A String representing the color of the Key
     */
    public String getColor() {
        return color;
    }
}