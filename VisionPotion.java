public class VisionPotion extends PickupItem {
    //declare
    private static String description;
    private static String potionName;
    private static char VisionPotionSymbol;

    //initialize the class and pass the variable to the super class
    VisionPotion() {

        super(description, potionName, VisionPotionSymbol);
        //initialize
        this.description = "A potion that allows to adjacent rooms";
        this.potionName = "Potion of Seeing";
        this.VisionPotionSymbol='V';
    }

    //get method for getting the vision potion symbol.
    public static  char getHealingSymbol() {
        return VisionPotionSymbol;
    }
    //method to get the potion's symbol
    public static char getVisionSymbol() {

        return VisionPotionSymbol;
    }
    //method to get the potion Name
    public String getPotionName()
    {
        return potionName;
    }
    //method to get the potion symbol
    public char getPotionSymbol()
    {
        return VisionPotionSymbol;
    }
    //method to get the potion description
    public String getPotionDescription()
    {
        return description;
    }
}