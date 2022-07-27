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
		Monster out = null;
		Random rand = new Random();
		int mon = rand.nextInt();
		if (mon == 0) {
			out = new Ogre();
		} else if (mon == 1) {
			out = new Skeleton();
		} else if (mon == 2) {
			out = new Gremlin();
		} else if (mon == 3) {
			out = new Automaton();
		}
		return out;
	}
}
