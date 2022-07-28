import java.util.Random;

public class Warrior extends Hero {
    public Warrior() {
        super(125, 35, 60, "Warrior", 4, 0.8, 0.2);
    }
    public void special(final Monster theOther) {
        System.out.println(super.getName() + " performed Crushing Blow!");
        Random rand = new Random();
        if (rand.nextDouble() < 0.4) {
            theOther.takeDMG(rand.nextInt(101) + 75);
        } else {
            System.out.println(super.getName() + " missed!");
        }
    }

}