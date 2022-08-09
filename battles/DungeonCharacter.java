package battles;

import java.util.Random;

/**
 * The DungeonCharacter class. Abstract, as it is only a base for Hero and Monster.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public abstract class DungeonCharacter {
	/**
	 * The Max HP.
	 */
	private int myMaxHP;
	/**
	 * The current Hp.
	 */
	private int myHP;
	/**
	 * The lower bound of the damage range.
	 */
	private int myMinDMG;
	/**
	 * The upper bound of the damage range.
	 */
	private int myMaxDMG;
	/**
	 * The character's name.
	 */
	private String myName;
	/**
	 * The attack speed.
	 */
	private int myAtkSpd;
	/**
	 * The hit chance.
	 */
	private double myHit;
	/**
	 * The current shield health. Shield takes damage before HP.
	 */
	private int myShield;
	/**
	 * A Random variable.
	 */
	private Random rand = new Random();
	
	/**
	 * Default Constructor. Should not be used, exists as backup.
	 */
	protected DungeonCharacter() {
		setHP(100);
		setDMGRange(10, 35);
		setAtkSpd(2);
		setHit(0.6);
		myName = "Anonymous";
		myShield = 0;
	}
	/**
	 * The main constructor for DungeonCharacter.
	 * 
	 * @param theMaxHP, an int
	 * @param theMinDMG, an int
	 * @param theMaxDMG, an int
	 * @param theName, a String
	 * @param theAtkSpd, an int
	 * @param theHit, a double
	 */
	protected DungeonCharacter(final int theMaxHP, final int theMinDMG, final int theMaxDMG, final String theName, final int theAtkSpd, final double theHit) {
		setHP(theMaxHP);
		setDMGRange(theMinDMG, theMaxDMG);
		setAtkSpd(theAtkSpd);
		setHit(theHit);
		myName = theName;
	}
	/**
	 * Returns the DungeonCharacter's HP.
	 * 
	 * @return the HP of this DungeonCharacter
	 */
	public int getHP() {
		return myHP;
	}
	/**
	 * Sets the max and current HP to the provided value.
	 * Does not allow HP values less than 1.
	 * 
	 * @param theMaxHP
	 */
	protected void setHP(final int theMaxHP) {
		if (theMaxHP <= 0) {
			System.out.println("Invalid Input for setHP");
			return;
		}
		myMaxHP = theMaxHP;
		myHP = theMaxHP;
	}
	/**
	 * Sets the damage range.
	 * Does not allow the max to be less than the min.
	 * 
	 * @param theMinDMG
	 * @param theMaxDMG
	 */
	protected void setDMGRange(final int theMinDMG, final int theMaxDMG) {
		myMinDMG = theMinDMG;
		myMaxDMG = theMaxDMG;
	}
	/**
	 * Sets the Attack Speed.
	 * Does not allow values less than 1.
	 * 
	 * @param theAtkSpd
	 */
	protected void setAtkSpd(final int theAtkSpd) {
		if (theAtkSpd <= 0) {
			System.out.println("Invalid input for attack speed");
			return;
		}
		myAtkSpd = theAtkSpd;
	}
	/**
	 * Sets the hit chance
	 * @param theHit
	 */
	protected void setHit(final double theHit) {
		myHit = theHit;
	}
	/**
	 * Checks to see if the character can attack its opponent.
	 * If it can, rolls a number in the character's damage range and damages the enemy.
	 * 
	 * @param theOther, the enemy to be attacked
	 */
	public void attack(final DungeonCharacter theOther) {
		if (rand.nextDouble() <= myHit) {
			int dmg = rand.nextInt((myMaxDMG + 1) - myMinDMG) + myMinDMG;
			//theOther.takeDMG(rand.nextInt((myMaxDMG + 1) - myMinDMG) + myMinDMG);
			System.out.println(myName + " rolled " + dmg + " damage!"); //FOR TESTING
			theOther.takeDMG(dmg);
			return; //FOR TESTING!
		}
		System.out.println(myName + " missed!");
	}
	/**
	 * Changes the HP of the DungeonCharacter.
	 * 
	 * IMPORTANT!
	 * Method subtracts the input from the HP. This means positive inputs will REDUCE HP.
	 * Negative inputs will heal the DungeonCharacter. Positive will hurt it.
	 * 
	 * @param theDMG
	 */
	public void takeDMG(final int theDMG) {
		if (myShield > 0 && theDMG > 0) {
			System.out.println("Shield took " + theDMG + " damage!");
			myShield -= theDMG;
			if (myShield < 0) { //FOR TESTING!
				System.out.println("Shield only had " + (myShield + theDMG) + " and broke!");
				myHP += myShield;
				System.out.println(myName + " took " + (0 - myShield) + " damage and has " + myHP + "HP left!");
				myShield = 0;
			} else {
				System.out.println("Shield's holding with" + myShield + " durability!");
			}
		} else {
			myHP -= theDMG;
			if (theDMG > 0) { //FOR TESTING!
				System.out.println(myName + " took " + theDMG + " damage and has " + myHP + "HP left!");
			}
		}
		if (myHP > myMaxHP) {
			myHP = myMaxHP;
		}
	}
	/**
	 * Returns the character's attack speed.
	 * 
	 * @return the attack speed of the character
	 */
	public int getAtkSpd() {
		return myAtkSpd;
	}
	/**
	 * Returns the name of the character.
	 * 
	 * @return The character's name
	 */
	public String getName() {
		return myName;
	}
	/**
	 * Adds shield to the user. Shield takes damage before HP.
	 * 
	 * @param theShield, the amount of shield to be added
	 */
	protected void addShield(final int theShield) {
		if (theShield + myShield < 0) {
			myShield = 0;
		} else {
			myShield += theShield;
		}
	}
	/**
	 * Returns the shield value of the character.
	 * 
	 * @return the sheild health of the character
	 */
	public int getShield() {
		return myShield;
	}
	/**
	 * Returns the max hp of the character.
	 * 
	 * @return the character's maxHP
	 */
	protected int getMaxHP() {
		return myMaxHP;
	}
}
