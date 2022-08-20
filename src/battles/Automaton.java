/**
 * The Automaton class. Extends Monster.
 * Cannot be a form of Monster as it has unique mechanics.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public class Automaton extends Monster {
    public Automaton() {
        super(125, 25, 40, "Automaton", 3,  0.6, 0.0, 0, 0);
        addShield(10);
    }
    /**
     * Overrides the standard TakeDMG method.
     * Will restore 10 shield after taking any damage.
     * 
     * @param theDMG
     * @return a String that explains what happened
     */
    @Override
    public String takeDMG(final int theDMG) {
        String myString = super.takeDMG(theDMG);
        if (getHP() > 0) {
            addShield(10);
        }
        return myString;
    }
}
