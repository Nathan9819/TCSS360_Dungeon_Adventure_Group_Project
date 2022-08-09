package battles;

import java.util.Random;

public class Thief extends Hero {
	public Thief() {
		super(75, 20, 40, "Thief", 6, 0.8, 0.2);
	}
	public void special(final Monster theOther) {
		Random rand = new Random();
		double hit = rand.nextDouble();
		if (hit < 0.4) {
			super.attack(theOther);
		}
		if (hit < 0.7) {
			super.attack(theOther);
		}
	}
}
