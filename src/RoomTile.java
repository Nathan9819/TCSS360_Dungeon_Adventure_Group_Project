import javax.swing.*;
import java.net.URL;

public class RoomTile extends Entity{

    public RoomTile() {
        height = 52;
        width = 52;
        layer = 0;
    }

    public void setRoomImage(String s) {
        URL url = HallwayVertical.class.getResource("Assets/" + s + ".png");
        sprite = new ImageIcon(url);
    }

}