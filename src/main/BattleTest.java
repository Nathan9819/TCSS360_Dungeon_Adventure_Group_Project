package main;

public class BattleTest {
	public static void main(final String[] theArgs) {
		final Warrior hero = new Warrior();
		final Ogre ogre = new Ogre();
		boolean cont = true;
		while (cont) {
			hero.attack(ogre);
			if (ogre.getHP() <= 0) {
				cont = false;
			} else {
				ogre.attack(hero);
				if (hero.getHP() <= 0) {
					cont = false;
				}
			}
		}
	}

}
