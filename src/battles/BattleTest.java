package battles;

public class BattleTest {
	public static void main(final String[] theArgs) {
		final Warrior hero = new Warrior();
		//final Ogre ogre = new Ogre();
		final Automaton auto = new Automaton();
		boolean cont = true;
		while (cont) {
			hero.attack(auto);
			if (auto.getHP() <= 0) {
				cont = false;
			} else {
				auto.attack(hero);
				if (hero.getHP() <= 0) {
					cont = false;
				}
			}
		}
	}

}
