import java.util.Random;

public class Thief extends Hero {
    public Thief() {
        super(75, 20, 40, "Thief", 6, 0.8, 0.2);
    }
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