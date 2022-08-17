import javax.swing.*;

public class Key extends Entity{
    private String color;

    public Key (String theColor) {
        super(theColor + " key", 20, 27,
                new ImageIcon(HallwayVertical.class.getResource("DungeonGeneration/Assets/" + theColor + "Key.png")), 200, 12, 16);
        color = theColor;
    }

    public String getColor() {
        return color;
    }

}
