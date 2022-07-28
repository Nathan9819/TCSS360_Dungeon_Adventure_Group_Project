
import javax.swing.*;
import java.net.URL;

public class Potion extends Entity{

    public Potion() {
        URL myUrl = HallwayVertical.class.getResource("DungeonGeneration/Assets/PotionFull.png");
        assert myUrl != null;
        sprite = new ImageIcon(myUrl);
        height = 17;
        width = 17;
        layer = 200;
    }

}
