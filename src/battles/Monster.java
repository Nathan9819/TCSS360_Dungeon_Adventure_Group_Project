import javax.swing.*;
import java.util.Random;

public abstract class Monster extends DungeonCharacter {
    public int width, height;
    private ImageIcon sprite;
    private Integer layer;
    private int offSetI;
    private int offSetJ;
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
                   final double theHit, final double theHeal, final int theMinHeal, final int theMaxHeal, final int theWidth,
                   final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        super(theMaxHP, theMinDMG, theMaxDMG, theName, theAtkSpd, theHit);
        setHeal(theHeal, theMinHeal, theMaxHeal);
        setSprite(theWidth, theHeight, theSprite, theLayer, theOffSetI, theOffSetJ);
    }

    protected void setSprite(final int theWidth, final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        width = theWidth;
        height = theHeight;
        sprite = theSprite;
        layer = theLayer;
        offSetI = theOffSetI;
        offSetJ = theOffSetJ;
    }

    protected void setHeal(final double theHeal, final int theMin, final int theMax) {
        myHeal = theHeal;
        myMinHeal = theMin;
        myMaxHeal = theMax;
    }
    public String takeDMG(final int theDMG) {
        StringBuilder myAtkInfo = new StringBuilder();
        myAtkInfo.append(super.takeDMG(theDMG));
        if (super.getHP() > 0 && rand.nextDouble() <= myHeal) {
            myAtkInfo.append(healSelf());
        }
        return myAtkInfo.toString();
    }
    protected String healSelf() {
        int heal = 0 - (rand.nextInt((myMaxHeal + 1) - myMinHeal) + myMinHeal);
        super.takeDMG(heal);
        return (super.getName() + " healed " + (0 - heal) + " and has " + super.getHP() + "HP!");
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int theWidth) {
        if (theWidth > 0) {
            width = theWidth;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int theHeight) {
        if (theHeight > 0) {
            height = theHeight;
        }
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    public void setSprite(final ImageIcon theSprite) {
        sprite = theSprite;
    }

    public Integer getLayer() {
        return layer;
    }

    public int getOffSetI() {
        return offSetI;
    }

    public void setOffSetI(final int theOffSetI) {
        offSetI = theOffSetI;
    }

    public int getOffSetJ() {
        return offSetJ;
    }

    public void setOffSetJ(final int theOffSetJ) {
        offSetJ = theOffSetJ;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(final String theName) {
        super.setName(theName);
    }
}