import javax.swing.*;
import java.net.URL;

public class RoomTile extends Entity{

    public RoomTile() {
//        URL url = HallwayVertical.class.getResource("Assets/1111.png");
//        sprite = new ImageIcon(url);
        height = 52;
        width = 52;
    }

    public void setRoomImage(String s) {
        URL url = HallwayVertical.class.getResource("Assets/" + s + ".png");
        sprite = new ImageIcon(url);
    }

}