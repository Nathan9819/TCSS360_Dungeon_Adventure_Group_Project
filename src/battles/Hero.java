package battles;

import java.util.Random;

public abstract class Hero extends DungeonCharacter {
	private double myBlock;
	private Random rand = new Random();
	
	public Hero() {
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
	protected void setBlock(final double theBlock) {
		myBlock = theBlock;
	}
	public void takeDMG(final int theDMG) {
		if (rand.nextDouble() <= myBlock) {
			System.out.println(super.getName() + " blocked the attack!"); //FOR TESTING
			return;
		}
		super.takeDMG(theDMG);
	}
	public abstract void special(final Monster theOther);
}
