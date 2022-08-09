package battles;

import java.util.Random;

/**
 * Generates a random Monster.
 * We do it here so that it only needs modifying once if we decide to alter it.
 * 
 * @author Colton Wickens
 * @version July 25, 2022
 */
public class GenerateMonster {
	public static Monster generate() {
		Random rand = new Random();
		return generate(rand.nextInt(4));
	}
	public static Monster generate(final int num) {
		Monster out = null;
		//Random rand = new Random();
		//int mon = rand.nextInt(4);
		if (num == 0) {
			//out = new Ogre();
			out = new Monster(200, 30, 60, "Ogre", 2, 0.6, 0.2, 30, 60);
		} else if (num == 1) {
			//out = new Skeleton();
			out = new Monster(100, 30, 50, "Skeleton", 3, 0.8, 0.3, 30, 50);
		} else if (num == 2) {
			//out = new Gremlin();
			out = new Monster(70, 15, 30, "Gremlin", 5, 0.8, 0.4, 20, 40);
		} else if (num == 3) {
			out = new Automaton();
		}
		return out;
	}
}
