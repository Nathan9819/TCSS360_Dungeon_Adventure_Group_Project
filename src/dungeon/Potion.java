package dungeon;

import javax.swing.*;
import java.net.URL;

public class Potion extends Entity{

    public Potion() {
        URL url = HallwayVertical.class.getResource("Assets/PotionFull.png");
        sprite = new ImageIcon(url);
        height = 17;
        width = 17;
        layer = 200;
    }

}
