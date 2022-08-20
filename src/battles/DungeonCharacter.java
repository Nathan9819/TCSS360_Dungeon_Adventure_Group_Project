import javax.swing.*;
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
    private int myMaxDMG;
    private String myName;
    private int myAtkSpd;
    private double myHit;
    private int myShield;
    private Random rand = new Random();

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
    public String attack(final DungeonCharacter theOther) {
        StringBuilder myAttackInfo = new StringBuilder();
        if (rand.nextDouble() <= myHit) {
            int dmg = rand.nextInt((myMaxDMG + 1) - myMinDMG) + myMinDMG;
            //theOther.takeDMG(rand.nextInt((myMaxDMG + 1) - myMinDMG) + myMinDMG);
            myAttackInfo.append(myName + " rolled " + dmg + " damage!\n"); //FOR TESTING
            myAttackInfo.append(theOther.takeDMG(dmg));
            return myAttackInfo.toString(); //FOR TESTING!
        }
        myAttackInfo.append(myName + " missed!");
        return myAttackInfo.toString();
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
    public String takeDMG(final int theDMG) {
        StringBuilder myDMGInfo = new StringBuilder();
        if (myShield > 0 && theDMG > 0) {
            myDMGInfo.append("Shield took " + theDMG + " damage!\n");
            myShield -= theDMG;
            if (myShield < 0) { //FOR TESTING!
                myDMGInfo.append("Shield only had " + (myShield + theDMG) + " and broke!\n");
                myHP += myShield;
                myDMGInfo.append(myName + " took " + (0 - myShield) + " damage and has " + myHP + "HP left!");
                myShield = 0;
            } else {
                myDMGInfo.append("Shield's holding with" + myShield + " durability!");
            }
        } else {
            myHP -= theDMG;
            if (theDMG > 0) { //FOR TESTING!
                myDMGInfo.append(myName + " took " + theDMG + " damage and has " + myHP + "HP left!");
            }
        }
        if (myHP > myMaxHP) {
            myHP = myMaxHP;
        }
        return myDMGInfo.toString();
    }
    public int getAtkSpd() {
        return myAtkSpd;
    }
    public String getName() {
        return myName;
    }
    protected void addShield(final int theShield) {
        myShield += theShield;
    }
    public int getShield() {
        return myShield;
    }
    public void cheatsActivated() {
        setDMGRange(1000, 1001);
        setHit(1);
    }
    public void setName(final String theName) {
        myName = theName;
    }
}
