import java.util.Random;

public abstract class Monster extends DungeonCharacter {
    private double myHeal;
    private int myMinHeal;
    private int myMaxHeal;
    private Random rand = new Random();

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
    public Monster(final int theMaxHP, final int theMinDMG, final int theMaxDMG, final String theName, final int theAtkSpd,
                   final double theHit, final double theHeal, final int theMinHeal, final int theMaxHeal) {
        super(theMaxHP, theMinDMG, theMaxDMG, theName, theAtkSpd, theHit);
        setHeal(theHeal, theMinHeal, theMaxHeal);
    }
    protected void setHeal(final double theHeal, final int theMin, final int theMax) {
        myHeal = theHeal;
        myMinHeal = theMin;
        myMaxHeal = theMax;
    }
    public String takeDMG(final int theDMG) {
        String myString = super.takeDMG(theDMG);
        if (super.getHP() > 0 && rand.nextDouble() <= myHeal) {
            healSelf();
        }
        return myString;
    }
    protected void healSelf() {
        int heal = 0 - (rand.nextInt((myMaxHeal + 1) - myMinHeal) + myMinHeal);
        System.out.println(super.getName() + " healed " + (0 - heal) + " and has " + super.getHP() + "HP!");
        super.takeDMG(heal);
    }
}