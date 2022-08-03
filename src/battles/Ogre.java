import javax.swing.*;

public class Ogre extends Monster {
    public Ogre() {
            super(200, 30, 60, "Ogre", 2, 0.6, 0.1, 30, 60, 27, 52,
            new ImageIcon(Ogre.class.getResource("DungeonGeneration/Assets/Ogre.png")), 200, -16, 12);
}
}