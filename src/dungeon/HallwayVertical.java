package dungeon;

import javax.swing.*;
import java.net.URL;

public class HallwayVertical extends Entity{

    public HallwayVertical() {
        URL url = HallwayVertical.class.getResource("Assets/HallwayVertical.png");
        sprite = new ImageIcon(url);
        height = 27;
        width = 24;
        layer = 100;
    }

}