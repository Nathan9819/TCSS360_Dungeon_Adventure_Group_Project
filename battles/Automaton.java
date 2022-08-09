package battles;

public class Automaton extends Monster {
	public Automaton() {
		super(125, 25, 40, "Automaton", 3,  0.6, 0.0, 0, 0);
		addShield(10);
	}
	public void takeDMG(final int theDMG) {
		super.takeDMG(theDMG);
		if (getHP() > 0) {
			addShield(10);
		}
	}
}