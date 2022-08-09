package battles;

import java.util.Random;

/**
 * The Monster class. Extends DungeonCharacter.
 * Monsters can heal themselves after taking damage, assuming they don't die.
 * 
 * @author Colton Wickens
 * @version Augest 6, 2022
 */
public class Monster extends DungeonCharacter {
	/**
	 * The Heal Chance.
	 */
	private double myHeal;
	/**
	 * The Minimum value of a heal.
	 */
	private int myMinHeal;
	/**
	 * The Maximum value of a heal.
	 */
	private int myMaxHeal;
	/**
	 * A Random variable.
	 */
	private Random rand = new Random();
	
	/**
	 * The default constructor. Shouldn't be called, but here in case.
	 */
	public Monster() {
		super();
		myHeal = 0.2;
		myMinHeal = 10;
		myMaxHeal = 30;
	}
	/**
	 * The main constructor for Monster.
	 * 
	 * @param theMaxHP, an int
	 * @param theMinDMG, an int
	 * @param theMaxDMG, an int
	 * @param theName, a String
	 * @param theAtkSpd, an int
	 * @param theHit, a double
	 * @param theHeal, a double
	 * @param theMinHeal, an int
	 * @param theMaxHeal, an int
	 */
	public Monster(final int theMaxHP, final int theMinDMG, final int theMaxDMG, final String theName, final int theAtkSpd, final double theHit, final double theHeal, final int theMinHeal, final int theMaxHeal) {
		super(theMaxHP, theMinDMG, theMaxDMG, theName, theAtkSpd, theHit);
		setHeal(theHeal, theMinHeal, theMaxHeal);
	}
	/**
	 * Sets the heal chance and range of values that can be healed by the monster.
	 * Does not allow the heal chance to be less than 0 or greater than 1.
	 * Does not allow the max to be less than the min.
	 * Does not allow the min to be less than 1.
	 * 
	 * @param theHeal, the healing chance
	 * @param theMin, the lower bound of the heal range
	 * @param theMax, the upper bound of the heal range
	 */
	protected void setHeal(final double theHeal, final int theMin, final int theMax) {
		if (theHeal < 0 || theHeal > 1) {
			System.out.println("Invalid input for Heal Chance");
			return;
		}
		if (theMin > theMax) {
			System.out.println("Minimum Heal cannot be more than Maximum Heal");
			return;
		}
		if (theMin <= 0) {
			System.out.println("Minimum value cannot be less than 0");
			return;
		}
		myHeal = theHeal;
		myMinHeal = theMin;
		myMaxHeal = theMax;
	}
	/**
	 * Overrides the DungeonCharacter takeDMG method.
	 * Take damage, check if alive, then heal.
	 * 
	 * @param theDMG, the damage taken
	 */
	@Override
	public void takeDMG(final int theDMG) {
		super.takeDMG(theDMG);
		if (super.getHP() > 0 && rand.nextDouble() <= myHeal) {
			healSelf();
		}
	}
	/**
	 * Checks if the monster should heal itself.
	 * If it should, the monster heals itself.
	 */
	protected void healSelf() {
		int heal = 0 - (rand.nextInt((myMaxHeal + 1) - myMinHeal) + myMinHeal);
		System.out.println(super.getName() + " healed " + (0 - heal) + " and has " + super.getHP() + "HP!");
		super.takeDMG(heal);
	}
}
