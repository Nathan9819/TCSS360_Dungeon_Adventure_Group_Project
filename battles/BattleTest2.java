package battles;

public class BattleTest2 {
	public static void main(final String[] theArgs) {
		final Hero hero = new Sorceress();
		final Monster ogre = new Automaton();
		BattleSystem.doBattle(hero, ogre);
		//System.out.println(hero.getHP());
	}

}
