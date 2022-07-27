import javax.swing.*;
import java.net.URL;

public class HallwayHorizontal extends Entity{
    boolean light;

    public HallwayHorizontal(boolean isLight) {
        height = 32;
        width = 27;
        layer = 100;
        light = isLight;
        setSprite();
    }

    private void setSprite() {
        URL url;
        if(light) {
            url = getClass().getResource("Assets/HallwayHorizontal.png");
        } else {
            url = getClass().getResource("Assets/DarkHallwayHorizontal.png");
        }
        sprite = new ImageIcon(url);
    }

}