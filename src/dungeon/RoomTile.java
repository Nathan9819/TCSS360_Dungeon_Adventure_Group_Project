package dungeon;

import javax.swing.*;
import java.net.URL;

public class RoomTile extends Entity{

    public RoomTile() {
        height = 52;
        width = 52;
        layer = 0;
    }

    public void setRoomImage(String s) {
        try {
            URL url = HallwayVertical.class.getResource("Assets/" + s + ".png");
            sprite = new ImageIcon(url);
        } catch (NullPointerException e) {
            System.out.println(s);
        }
    }

}