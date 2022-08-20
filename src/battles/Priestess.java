import java.util.Random;

/**
 * The Priestess class. Exends Hero.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public class Priestess extends Hero {
    /**
     * Priestess constructor.
     */
    public Priestess() {
        super(75, 25, 45, "Sorceress", 5, 0.7, 0.3);
    }
    /**
     * Priestess's special attack. Grants herself a shield.
     * 
     * @param theOther
     */
    public String special(final Monster theOther) {
        StringBuilder myAtkInfo = new StringBuilder();
        myAtkInfo.append(super.getName() + " used Divine Shield!");
        Random rand = new Random();
        int shield = rand.nextInt(50) + 40;
        myAtkInfo.append(super.getName() + " gained " + shield + " shield!");
        super.addShield(shield);
        return myAtkInfo.toString();
    }

}
