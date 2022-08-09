import javax.swing.*;

public class Skeleton extends Monster {
    public Skeleton() {
        super(100, 30, 50, "Skeleton", 3, 0.8, 0.4, 30, 50, 32, 46,
                new ImageIcon(Skeleton.class.getResource("DungeonGeneration/Assets/Skeleton.png")), 200, -10, 9);
    }
}