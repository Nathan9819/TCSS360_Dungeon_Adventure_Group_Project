import javax.swing.*;
import java.net.URL;

public class RoomTile extends Entity{

    public RoomTile() {
        URL url = HallwayVertical.class.getResource("Assets/RoomEmpty.png");
        sprite = new ImageIcon(url);
        height = 52;
        width = 52;
    }

}