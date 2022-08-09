package battles;

import java.util.Random;

public class Sorceress extends Hero {
	public Sorceress() {
		super(75, 25, 45, "Sorceress", 5, 0.7, 0.3);
	}
	public void special(final Monster theOther) {
		System.out.println(super.getName() + " used Divine Shield!");
		Random rand = new Random();
		int shield = rand.nextInt(50) + 40;
		System.out.println(super.getName() + " gained " + shield + " shield!");
		super.addShield(shield);
	}

}
