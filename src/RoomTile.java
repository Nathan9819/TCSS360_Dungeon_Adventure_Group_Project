import javax.swing.*;
import java.net.URL;

public class RoomTile extends Entity{
    boolean light;

    public RoomTile(boolean isLight) {
        height = 52;
        width = 52;
        layer = 0;
        light = isLight;
    }

    public void setRoomImage(String s) {
        URL url;
        if (light) {
            url = getClass().getResource("Assets/" + s + ".png");
        } else {
            url = getClass().getResource("Assets/Dark" + s + ".png");
        }
        sprite = new ImageIcon(url);
    }

}