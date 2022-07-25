import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends JFrame implements KeyListener{
    DungeonAdventure da;
    JLayeredPane layeredPane;
    public JLabel bgLabel[] = new JLabel[100];

    public UI(DungeonAdventure da) {
        this.da = da;
        createMainField();
    }

    public void createMainField() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 900);

        this.setTitle("Dungeon Adventure");
        this.add(layeredPane);
        this.setSize(800, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color bgColor = new Color(115, 62, 57);
        this.getContentPane().setBackground(bgColor);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    public void spawnItem(Entity e, int x, int y, int count) {
        bgLabel[count] = new JLabel();
        bgLabel[count].setBounds(x,y,e.width,e.height);
        bgLabel[count].setIcon(e.sprite);
        layeredPane.add(bgLabel[count], e.layer);
    }

    public void refresh() {
        this.repaint();
        this.revalidate();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not being used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'w' :
                if (da.p.myRoom.myNorth != null) {
                    movePlayer(0);
                    System.out.println("w pressed");
                }
                break;
            case 'd' :
                if (da.p.myRoom.myEast != null) {
                    movePlayer(1);
                    System.out.println("d pressed");
                }
                break;
            case 's' :
                if (da.p.myRoom.mySouth != null) {
                    movePlayer(2);
                    System.out.println("s pressed");
                }
                break;
            case 'a' :
                if (da.p.myRoom.myWest != null) {
                    movePlayer(3);
                    System.out.println("a pressed");
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not being used
    }

    public void movePlayer (int direction) {
        int offsetJ = 0;
        int offsetI = 0;
        switch(direction) {
            case 0 : offsetI = -2;
                break;
            case 1 : offsetJ = 2;
                break;
            case 2 :  offsetI = 2;
                break;
            case 3 : offsetJ = -2;
                break;
        }
        bgLabel[0].setBounds((((int) Math.ceil(((double)da.p.myCoords.y + offsetJ)/ 2) * 26) + (((da.p.myCoords.y + offsetJ)/ 2) * 51) + 17),
                            (((int) Math.ceil(((double)da.p.myCoords.x + offsetI)/2) * 20) + ((((da.p.myCoords.x + offsetI) / 2)) * 51) + 17), da.p.width, da.p.height);
        da.p.myCoords = new Point(da.p.myCoords.x + offsetI, da.p.myCoords.y + offsetJ);
        DungeonGenerator.updatePlayerCoords(da.p);
        refresh();
    }
}
