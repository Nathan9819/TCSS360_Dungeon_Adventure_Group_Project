import java.util.Random;

/**
 * The Warrior class. Extends Hero.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public class Warrior extends Hero {
    public Warrior() {
        super(125, 35, 60, "Warrior", 4, 0.8, 0.2);
//        super(125, 1000, 1001, "Warrior", 4, 1, 0.2);
    }
    /**
     * The Special attack for Warrior. Allows it to do massive damage, but with low accuracy.
     * 
     * @author Colton Wickens
     * @version August 6, 2022
     */
    public String special(final Monster theOther) {
        StringBuilder myAtkInfo = new StringBuilder();
        myAtkInfo.append(super.getName() + " performed Crushing Blow!\n");
        Random rand = new Random();
        if (rand.nextDouble() < 0.4) {
            myAtkInfo.append(theOther.takeDMG(rand.nextInt(101) + 75));
        } else {
            myAtkInfo.append(super.getName() + " missed!");
        }
        return myAtkInfo.toString();
    }

}
