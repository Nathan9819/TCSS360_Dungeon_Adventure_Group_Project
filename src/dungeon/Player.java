package dungeon;

import javax.swing.*;

import battles.Hero;

import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Player extends Entity {
    public Point myCoords;
    public Room myRoom;
    private int myHealthPotions;
    private Hero myChar;
    private Random r = new Random();

    public Player(int i, int j) {
        URL url = Player.class.getResource("Assets/PotionFull.png");
        sprite = new ImageIcon(url);
        height = 17;
        width = 17;
        layer = 200;
        myCoords = new Point(i, j);
        myHealthPotions = 0;
    }
    public void setRoom (Room r) {
        myRoom = r;
    }
    public int getHealthPotions() {
    	return myHealthPotions;
    }
    public void useHealthPotion() {
    	if (myHealthPotions > 0) {
    		myHealthPotions--;
    		myChar.takeDMG(0 - (r.nextInt(10) + 5));
    	}
    }
    public void gainHealthPotion() {
    	myHealthPotions++;
    }
    public Hero getChar() {
    	return myChar;
    }
}