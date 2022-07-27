import javax.swing.*;
import java.net.URL;

public class HallwayVertical extends Entity{
    private final boolean isLight;

    public HallwayVertical(boolean theLight) {
        height = 27;
        width = 24;
        layer = 100;
        isLight = theLight;
        setSprite();
    }

    private void setSprite() {
        URL myUrl;
        if(isLight) {
            myUrl = getClass().getResource("Assets/HallwayVertical.png");
        } else {
            myUrl = getClass().getResource("Assets/DarkHallwayVertical.png");
        }
        assert myUrl != null;
        sprite = new ImageIcon(myUrl);
    }

}