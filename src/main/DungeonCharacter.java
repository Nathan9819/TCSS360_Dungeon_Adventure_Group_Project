package main;

import java.util.Random;

public class DungeonCharacter {
	private int myMaxHP;
	private int myHP;
	private int myMinDMG;
	private int myMaxDMG;
	private String myName;
	private int myAtkSpd;
	private double myHit;
	private Random rand = new Random();
	
	protected DungeonCharacter() {
		setHP(100);
		setDMGRange(10, 35);
		setAtkSpd(2);
		setHit(0.6);
		myName = "Anonymous";
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
	
	public int getHP() {
		return myHP;
	}
	protected void setHP(final int theMaxHP) {
		myMaxHP = theMaxHP;
		myHP = theMaxHP;
	}
	protected void setDMGRange(final int theMinDMG, final int theMaxDMG) {
		myMinDMG = theMinDMG;
		myMaxDMG = theMaxDMG;
	}
	protected void setAtkSpd(final int theAtkSpd) {
		myAtkSpd = theAtkSpd;
	}
	protected void setHit(final double theHit) {
		myHit = theHit;
	}
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
		myHP -= theDMG;
		if (theDMG > 0) { //FOR TESTING!
			System.out.println(myName + " took " + theDMG + " damage and has " + myHP + "HP left!");
		}
		if (myHP > myMaxHP) {
			myHP = myMaxHP;
		}
	}
	public int getAtkSpd() {
		return myAtkSpd;
	}
	public String getName() {
		return myName;
	}
}
