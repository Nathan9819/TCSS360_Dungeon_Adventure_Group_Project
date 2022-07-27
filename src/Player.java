import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Player extends Entity {
    public Point myCoords;
    public Room myRoom;

    public Player(int i, int j) {
        URL url = getClass().getResource("Assets/PotionFull.png");
        sprite = new ImageIcon(url);
        height = 17;
        width = 17;
        layer = 200;
        myCoords = new Point(i, j);
    }

    public void setRoom (Room r) {
        myRoom = r;
    }

    public Room getRoom () {
        return myRoom;
    }
}