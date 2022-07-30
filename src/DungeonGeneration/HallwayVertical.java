
import javax.swing.*;
import java.net.URL;

public class HallwayVertical extends Entity{
    private final boolean isLight;

    public HallwayVertical(boolean theLight) {
        super(24, 27, 100);
        isLight = theLight;
        setSprite();
    }

    private void setSprite() {
        URL myUrl;
        if(isLight) {
            myUrl = getClass().getResource("DungeonGeneration/Assets/HallwayVertical.png");
        } else {
            myUrl = getClass().getResource("DungeonGeneration/Assets/DarkHallwayVertical.png");
        }
        assert myUrl != null;
        super.setSprite(new ImageIcon(myUrl));
    }

}