package main;
import java.util.Random;
public class HealingPotion extends PickupItem {
    public int HealingPotion;
    public int minHealingPotion;
    public int maxHealingPotion;
    String player = "HealingPotion";

    public HealingPotion() {
        this(null);
        this.HealingPotion=0;
        this.minHealingPotion=3;
        this.maxHealingPotion=9;
    }
    public void work (DungeonCharacter dungencharacter) {
        //System.out.println(" ");
        //System.out.println(" ");
    }
    public HealingPotion(DungeonGenerator dungeon) {
        super("HealingPotion", dungeon);
        Random getRandomHealing = new Random();
        this.HealingPotion = minHealingPotion + getRandomHealing.nextInt(maxHealingPotion);
    }
}