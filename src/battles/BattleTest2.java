package battles;

public class BattleTest2 {
	public static void main(final String[] theArgs) {
		final Warrior hero = new Warrior();
		final Ogre ogre = new Ogre();
		BattleSystem.doBattle(hero, ogre);
		System.out.println(hero.getHP());
	}

}
