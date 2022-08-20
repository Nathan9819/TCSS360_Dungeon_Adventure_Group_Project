
import javax.swing.*;

/**
 * This class represents a room tile. It holds information regarding the room's
 * size, layer, visibility, and image.
 * @Author Nathan Mahnke
 */

public class RoomTile extends Entity{
    boolean isLight;

    /**
     * This is the DungeonGeneration.RoomTile constructor. It sets the height and width of the room, its layer, and
     * its visibility.
     *
     * @param theLight Whether the tile should be lit up or darkened
     */
    public RoomTile(boolean theLight) {
        super(52, 52, 0);
        isLight = theLight;
    }

    /**
     * This method sets the image to be displayed when a room is added to the GUI. It accepts a
     * string, which should be a 4-digit binary number. The binary number represents whether
     * cardinal direction's exit is opened or closed. The first digit of the binary represents North,
     * and it continues on clockwise. 0 represents no exit, 1 represents and exit. This string is used
     * to reference the proper image from the asset folder.
     *
     * @param theRoomCode The 4-digit binary number representing the room code
     */
    public void setRoomImage(String theRoomCode) {
        super.setSprite(new ImageIcon(getClass().getResource("Assets/" + (isLight ? "" : "Dark") + theRoomCode + ".png")));
    }

}