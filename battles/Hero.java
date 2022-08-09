package battles;

import java.util.Random;

public abstract class Hero extends DungeonCharacter {
	/**
	 * The block chance.
	 */
	private double myBlock;
	/**
	 * A Random variable.
	 */
	private Random rand = new Random();
	private int myHPotions;
	
	/**
	 * The default constructor. Here just in case.
	 */
	public Hero() {
		super();
		setBlock(0.2);
		myHPotions = 0;
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
	 * Sets the block chance for the Hero.
	 * Does not allow values below 0 or above 1.
	 * 
	 * @param theBlock, the block chance
	 */
	protected void setBlock(final double theBlock) {
		if (theBlock < 0 || theBlock > 1) {
			System.out.println("Invalid input for Block Chance");
			return;
		}
		myBlock = theBlock;
	}
	/**
	 * Overrides the takeDMG method of DungeonCharacter.
	 * Will check if the hero should block before applying the damage.
	 * Will also check if it is damage or healing before rolling for block.
	 * i.e. If positive (damage), will try a block. If negative (healing), will not.
	 * 
	 * @param theDMG, the damage taken
	 */
	@Override
	public void takeDMG(final int theDMG) {
		if (rand.nextDouble() <= myBlock && theDMG > 0) {
			System.out.println(super.getName() + " blocked the attack!"); //FOR TESTING
			return;
		}
		super.takeDMG(theDMG);
	}
	/**
	 * Increases the hero's health potion count.
	 */
	public void addHPotion() { 
		myHPotions++;
	}
	/**
	 * Uses a healing potion.
	 * If the Hero does not have a potion, will return message saying they have none.
	 * If the Hero is at Max HP, will return a message saying the are fully healed already.
	 * If neither of those is true, will heal and return a message with the heal amount.
	 * 
	 * @return A string indicating what happened
	 */
	public String useHPotion() {
		StringBuilder out = new StringBuilder();
		if (myHPotions > 0 && getHP() < getMaxHP()) {
			out.append(getName() + " used a healing potion!\n");
			int heal = rand.nextInt(11) + 20;
			out.append(getName() + " healed " + heal + " health!");
		} else if (myHPotions <= 0) {
			out.append("You don't have any healing potions!");
		} else if (getHP() >= getMaxHP()) {
			out.append("You already have max HP!");
		}
		return out.toString();
	}
	/**
	 * Will be overwritten by child classes, as each hero's special is too unique.
	 * 
	 * @param theOther
	 */
	public abstract void special(final Monster theOther);
}
