import java.util.Random;

public class Priestess extends Hero {
    public Priestess() {
        super(75, 25, 45, "Sorceress", 5, 0.7, 0.3);
    }
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