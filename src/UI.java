import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends JFrame implements KeyListener{
    DungeonAdventure da;
    JLayeredPane layeredPane;
    public JLabel bgLabel[][] = new JLabel[22][22];
    public JLabel Player;

    public UI(DungeonAdventure da) {
        this.da = da;
        createMainField();
    }

    public void createMainField() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 2560, 1440);

        this.setTitle("Dungeon Adventure");
        this.add(layeredPane);
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color bgColor = new Color(115, 62, 57);
        this.getContentPane().setBackground(bgColor);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.pack();
    }

    public void spawnItem(Entity e, int x, int y, int i, int j) {
        bgLabel[i][j] = new JLabel();
        bgLabel[i][j].setBounds(x,y,e.width,e.height);
        bgLabel[i][j].setIcon(e.sprite);
        layeredPane.add(bgLabel[i][j], e.layer);
    }

    public void spawnPlayer(Player p, int x, int y) {
        Player = new JLabel();
        Player.setBounds(x,y,p.width,p.height);
        Player.setIcon(p.sprite);
        layeredPane.add(Player, p.layer);
        setLight(p.myCoords.x, p.myCoords.y, p.myRoom);
    }

    public void setLight(int i, int j, Room r) {
        RoomTile lightRoom = new RoomTile(true);
        lightRoom.setRoomImage(da.getRoomCode(i, j));
        HallwayHorizontal lightHH = new HallwayHorizontal(true);
        HallwayVertical lightHV = new HallwayVertical(true);
        bgLabel[i][j].setIcon(lightRoom.sprite);
        if (r.myNorth != null) {
            bgLabel[i - 1][j].setIcon(lightHV.sprite);
            lightRoom.setRoomImage(da.getRoomCode(i - 2, j));
            bgLabel[i - 2][j].setIcon(lightRoom.sprite);
        }
        if (r.myEast != null) {
            bgLabel[i][j + 1].setIcon(lightHH.sprite);
            lightRoom.setRoomImage(da.getRoomCode(i, j + 2));
            bgLabel[i][j + 2].setIcon(lightRoom.sprite);
        }
        if (r.myWest != null) {
            bgLabel[i][j - 1].setIcon(lightHH.sprite);
            lightRoom.setRoomImage(da.getRoomCode(i, j - 2));
            bgLabel[i][j - 2].setIcon(lightRoom.sprite);
        }
        if (r.mySouth != null) {
            bgLabel[i + 1][j].setIcon(lightHV.sprite);
            lightRoom.setRoomImage(da.getRoomCode(i + 2, j));
            bgLabel[i + 2][j].setIcon(lightRoom.sprite);
        }
        da.p.myRoom.visited = true;
        refresh();
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

        Player.setBounds((((int) Math.ceil(((double)da.p.myCoords.y + offsetJ)/ 2) * 26) + (((da.p.myCoords.y + offsetJ)/ 2) * 51) + 17),
                            (((int) Math.ceil(((double)da.p.myCoords.x + offsetI)/2) * 20) + ((((da.p.myCoords.x + offsetI) / 2)) * 51) + 17), da.p.width, da.p.height);
        da.p.myCoords = new Point(da.p.myCoords.x + offsetI, da.p.myCoords.y + offsetJ);
        if (direction < 2) {
            da.p.myRoom = direction == 0 ? da.p.myRoom.myNorth : da.p.myRoom.myEast;
        } else {
            da.p.myRoom = direction == 2 ? da.p.myRoom.mySouth : da.p.myRoom.myWest;
        }
        if (!da.p.myRoom.visited) {
            setLight(da.p.myCoords.x, da.p.myCoords.y, da.p.myRoom);
        }
        refresh();
    }


}
