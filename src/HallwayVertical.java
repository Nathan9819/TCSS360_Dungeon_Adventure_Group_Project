import javax.swing.*;
import java.net.URL;

public class HallwayVertical extends Entity{
    private boolean light;

    public HallwayVertical(boolean isLight) {
        height = 27;
        width = 24;
        layer = 100;
        light = isLight;
        setSprite();
    }

    private void setSprite() {
        URL url;
        if(light) {
            url = getClass().getResource("Assets/HallwayVertical.png");
        } else {
            url = getClass().getResource("Assets/DarkHallwayVertical.png");
        }
        sprite = new ImageIcon(url);
    }

}