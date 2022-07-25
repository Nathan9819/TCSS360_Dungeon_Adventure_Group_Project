import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Player extends Entity{
    protected Point myCoords;

    public Player(int i, int j) {
        URL url = HallwayVertical.class.getResource("Assets/PotionFull.png");
        sprite = new ImageIcon(url);
        height = 17;
        width = 17;
        layer = 200;
        myCoords = new Point(i, j);
    }
}
