import java.util.Random;

/**
 * The Thief Class. Extends Hero.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public class Thief extends Hero {
    public Thief() {
        super(75, 20, 40, "Thief", 6, 0.8, 0.2);
    }
    /**
     * Thief's special attack. Allows the Thief to attack twice if the odds are in his favor.
     * 
     * @param theOther
     * @return a String describing what happened here
     */
    public String special(final Monster theOther) {
        StringBuilder myAtkInfo = new StringBuilder();
        Random rand = new Random();
        double hit = rand.nextDouble();
        if (hit < 0.4) {
            myAtkInfo.append(super.attack(theOther));
        }
        if (hit < 0.7) {
            myAtkInfo.append(super.attack(theOther));
        }
        return myAtkInfo.toString();
    }
}
