import javax.swing.*;
import java.awt.*;

public class Gremlin extends Monster {
    private Point coords;
    private Room room;

    public Gremlin() {
        super(70, 15, 30, "Gremlin", 5, 0.8, 0.4, 20, 40, 40, 42,
                new ImageIcon(Gremlin.class.getResource("DungeonGeneration/Assets/Gremlin.png")), 200, -6, 7);
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}