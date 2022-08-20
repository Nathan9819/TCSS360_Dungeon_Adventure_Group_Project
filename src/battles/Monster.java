import javax.swing.*;
import java.util.Random;

/**
 * The Monster class. Extends DungeonCharacter.
 * 
 * @author Colton Wickens
 * @version August 6, 2022
 */
public abstract class Monster extends DungeonCharacter {
    /**
     * The width and height of the sprite
     */
    public int width, height;
    /**
     * The sprite.
     */
    private ImageIcon sprite;
    /**
     * The layer.
     */
    private Integer layer;
    /**
     * One of two offsets to make the sprite appear in the proper position.
     */
    private int offSetI;
    /**
     * One of two offsets to make the sprite appear in the proper position.
     */
    private int offSetJ;
    /**
     * The heal chance.
     */
    private double myHeal;
    /**
     * The minimum heal amount.
     */
    private int myMinHeal;
    /**
     * The maximum heal amount.
     */
    private int myMaxHeal;
    /**
     * A Random variable.
     */
    private Random rand = new Random();

    /**
     * Default constructor.
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
     * @param thewidth
     * @param theHeight
     * @param theSprite
     * @param theLayer
     * @param theOffSetI
     * @param theOffSetJ
     */
    public Monster(final int theMaxHP, final int theMinDMG, final int theMaxDMG, final String theName, final int theAtkSpd,
                   final double theHit, final double theHeal, final int theMinHeal, final int theMaxHeal, final int theWidth,
                   final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        super(theMaxHP, theMinDMG, theMaxDMG, theName, theAtkSpd, theHit);
        setHeal(theHeal, theMinHeal, theMaxHeal);
        setSprite(theWidth, theHeight, theSprite, theLayer, theOffSetI, theOffSetJ);
    }

    /**
     * Sets the sprite.
     * 
     * @param theWidth
     * @param theHeight
     * @param theSprite
     * @param theLayer
     * @param theOffSetI
     * @param theOffSetJ
     */
    protected void setSprite(final int theWidth, final int theHeight, final ImageIcon theSprite, final Integer theLayer, final int theOffSetI, final int theOffSetJ) {
        width = theWidth;
        height = theHeight;
        sprite = theSprite;
        layer = theLayer;
        offSetI = theOffSetI;
        offSetJ = theOffSetJ;
    }

    /**
     * Sets the heal values for the Monster.
     * 
     * @param theHeal
     * @param theMin
     * @param theMax
     */
    protected void setHeal(final double theHeal, final int theMin, final int theMax) {
        myHeal = theHeal;
        myMinHeal = theMin;
        myMaxHeal = theMax;
    }
    /**
     * Overrides takeDMG in DungeonCharacter.
     * Checks if the monster should heal after taking damage.
     * 
     * @param theDMG
     * @return a String exolaining what happened
     */
    @Override
    public String takeDMG(final int theDMG) {
        StringBuilder myAtkInfo = new StringBuilder();
        myAtkInfo.append(super.takeDMG(theDMG));
        if (super.getHP() > 0 && rand.nextDouble() <= myHeal) {
            myAtkInfo.append(healSelf());
        }
        return myAtkInfo.toString();
    }
    /**
     * Heals the monster.
     * 
     * @return a String desceribing what happened here
     */
    protected String healSelf() {
        int heal = 0 - (rand.nextInt((myMaxHeal + 1) - myMinHeal) + myMinHeal);
        super.takeDMG(heal);
        return ("\n" + super.getName() + " healed " + (0 - heal) + " and has " + super.getHP() + "HP!");
    }

    /**
     * Returns the Width.
     * 
     * @return the Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the Width.
     * 
     * @param theWidth
     */
    public void setWidth(final int theWidth) {
        if (theWidth > 0) {
            width = theWidth;
        }
    }

    /**
     * Returns the height.
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height.
     * 
     * @param theHeight
     */
    public void setHeight(final int theHeight) {
        if (theHeight > 0) {
            height = theHeight;
        }
    }

    /**
     * Returns the sprite.
     * 
     * @return the sprite
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * Sets the sprite.
     * 
     * @param theSprite
     */
    public void setSprite(final ImageIcon theSprite) {
        sprite = theSprite;
    }

    /**
     * Returns the layer.
     * 
     * @return the layer
     */
    public Integer getLayer() {
        return layer;
    }

    /**
     * Returns the Offset I.
     * 
     * @return offset I
     */
    public int getOffSetI() {
        return offSetI;
    }

    /**
     * Sets the Offset I.
     * 
     * @param theOffSetI
     */
    public void setOffSetI(final int theOffSetI) {
        offSetI = theOffSetI;
    }

    /**
     * Returns the Offset J.
     * 
     * @return offset J
     */
    public int getOffSetJ() {
        return offSetJ;
    }

    /**
     * Sets the Offset J.
     * 
     * @param theOffSetJ
     */
    public void setOffSetJ(final int theOffSetJ) {
        offSetJ = theOffSetJ;
    }

    /**
     * Returns the name.
     * 
     * @return the name
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the Name.
     * 
     * @param theName
     */
    public void setName(final String theName) {
        super.setName(theName);
    }
}
