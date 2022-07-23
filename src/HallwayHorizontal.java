import javax.swing.*;
import java.net.URL;

public class HallwayHorizontal extends Entity{

    public HallwayHorizontal() {
        URL url = HallwayVertical.class.getResource("Assets/HallwayHorizontal.png");
        sprite = new ImageIcon(url);
        height = 32;
        width = 27;
    }

}