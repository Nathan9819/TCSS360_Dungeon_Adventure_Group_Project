import javax.swing.*;
import java.net.URL;

/**
 * This class represents a room tile. It holds information regarding the room's
 * size, layer, visibility, and image.
 * @Author Nathan Mahnke
 */

public class RoomTile extends Entity{
    boolean light;

    /**
     * This is the RoomTile constructor. It sets the height and width of the room, its layer, and
     * its visibility.
     *
     * @param isLight Whether the tile should be lit up or darkened
     */
    public RoomTile(boolean isLight) {
        height = 52;
        width = 52;
        layer = 0;
        light = isLight;
    }

    /**
     * This method sets the image to be displayed when a room is added to the GUI. It accepts a
     * string, which should be a 4-digit binary number. The binary number represents whether
     * cardinal direction's exit is opened or closed. The first digit of the binary represents North,
     * and it continues on clockwise. 0 represents no exit, 1 represents and exit. This string is used
     * reference the proper image from the asset folder.
     *
     * @param s The 4-digit binary number representing the room code
     */
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