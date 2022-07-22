import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UI {
    DungeonAdventure da;
    JFrame window;
    public JPanel bgPanel[] = new JPanel[100];
    public JLabel bgLabel[] = new JLabel[100];

    public UI(DungeonAdventure da) {
        this.da = da;
        createMainField();
//        createBackground();

//        window.setVisible(true);
    }

    public void createMainField() {
        window = new JFrame();
        window.setSize(800, 900);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.white);
        window.setLayout(null);
    }

    public void spawnItem(Entity e, int x, int y, int count) {
        bgPanel[count] = new JPanel();
        bgPanel[count].setBounds(x, y, e.width, e.height);
        bgPanel[count].setBackground(null);
        bgPanel[count].setLayout(null);
        window.add(bgPanel[count]);

        bgLabel[count] = new JLabel();
        bgLabel[count].setBounds(0,0,e.width,e.height);
        bgLabel[count].setIcon(e.sprite);
        bgPanel[count].add(bgLabel[count]);

//        window.revalidate();
//        window.repaint();

    }

//    public void createBackground() {
//        bgPanel[1] = new JPanel();
//        bgPanel[1].setBounds(50, 50, 20, 20);
//        bgPanel[1].setBackground(null);
//        bgPanel[1].setLayout(null);
//        window.add(bgPanel[1]);
//
//        bgLabel[1] = new JLabel();
//        bgLabel[1].setBounds(0,0,20,20);
//
//        URL url = Potion.class.getResource("Assets/PotionFull.png");
//        ImageIcon image = new ImageIcon(url);
//        bgLabel[1].setIcon(image);
//
//        bgPanel[1].add(bgLabel[1]);
//    }
}
