import java.util.Random;

/**
 * The Hero class. Extends DungeonCharacter.
 * Is abstract, and requires its child classes to define some methods.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public abstract class Hero extends DungeonCharacter {
    /**
     *The Block chance.
     */
    private double myBlock;
    /**
     * A Random variable.
     */
    private Random rand = new Random();

    /**
     * The default constructor. Shouldn't be activated, but is here for safety.
     */
    protected Hero() {
        super();
        setBlock(0.2);
    }
    /**
     * The main constructor for Hero.
     *
     * @param theMaxHP, an int
     * @param theMinDMG, an int
     * @param theMaxDMG, an int
     * @param theName, a String
     * @param theAtkSpd, an int
     * @param theHit, a double
     * @param theBlock, a double
     */
    public Hero(final int theMaxHP, final int theMinDMG, final int theMaxDMG, final String theName, final int theAtkSpd, final double theHit, final double theBlock) {
        super(theMaxHP, theMinDMG, theMaxDMG, theName, theAtkSpd, theHit);
        setBlock(theBlock);
    }
    /**
     * Sets the block value.
     * Does not allow values below 0 or above 1.
     * 
     * @param theBlock
     */
    protected void setBlock(final double theBlock) {
        if (theBlock < 0 || theBlock > 1) {
            System.out.println("Block chance must be within the range of 0-1.");
            return;
        }
        myBlock = theBlock;
    }

    /**
     * Overrides the takeDMG method from DungeonCharacter.
     * Checks if the hero should block damage before taking it.
     * 
     * @param theDMG
     * @return A String that explains what happened
     */
    @Override
    public String takeDMG(final int theDMG) {
        StringBuilder dmgInfo = new StringBuilder();
        if (rand.nextDouble() <= myBlock) {
            dmgInfo.append(super.getName() + " blocked the attack!"); //FOR TESTING
            return dmgInfo.toString();
        }
        return super.takeDMG(theDMG);
    }
    /**
     * An abstract method. Child classes must define it.
     * Will determine a class's special attack.
     * 
     * @param theOther
     * @return A String that describes what happened
     */
    public abstract String special(final Monster theOther);
}
