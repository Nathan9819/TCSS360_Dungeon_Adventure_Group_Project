import javax.swing.*;

public class Gremlin extends Monster {
    public Gremlin() {
        super(70, 15, 30, "Gremlin", 5, 0.8, 0.4, 20, 40, 40, 42,
                new ImageIcon(Gremlin.class.getResource("DungeonGeneration/Assets/Gremlin.png")), 200, -6, 7);
    }
}