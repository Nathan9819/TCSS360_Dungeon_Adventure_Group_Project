import javax.swing.*;

public class Key extends Entity{

    private String color;
    private int floor;

    public Key (String theColor, final int theFloor) {
        // Concat string name of sprite with color string received
        // placeholder potion used while art is in the works
        super(theColor + " key", 17, 17, new ImageIcon(HallwayVertical.class.getResource("DungeonGeneration/Assets/PotionFull.png")), 200, 17, 17);
        color = theColor;
        floor = theFloor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String theColor) {
        color = theColor;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int theFloor) {
        floor = theFloor;
    }
}
