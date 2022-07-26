import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Player extends Entity {
    public Point myCoords;
    public DungeonGenerator.room myRoom;

    public Player(int i, int j) {
        URL url = HallwayVertical.class.getResource("Assets/PotionFull.png");
        sprite = new ImageIcon(url);
        height = 17;
        width = 17;
        layer = 200;
        myCoords = new Point(i, j);
    }

    public void setRoom (DungeonGenerator.room r) {
        myRoom = r;
    }

    public DungeonGenerator.room getRoom () {
        return myRoom;
    }
}