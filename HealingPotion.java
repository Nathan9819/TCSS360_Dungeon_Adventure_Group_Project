//extend HealingPotion class with the pickupItem
public class HealingPotion extends PickupItem {
    // protected static int HealingPotion;
    //declared
    protected static int minHealingPotion;
    protected static int maxHealingPotion;
    private static char HealingPotionSymbol;
    private static String potionName;
    private static String potionDescription;
    //private char symbol;
//constructor to intialize the class
    public HealingPotion(){
        //intilialize the parent class by 'super'
        super(potionDescription, potionDescription, HealingPotionSymbol);
        //this.HealingPotion=0;
        //intialize
        this.minHealingPotion=5;
        this.maxHealingPotion=15;
        this.HealingPotionSymbol='H';
        this.potionName="HealingPotion";
        this.potionDescription="A potion is healed from 5 to 15 HP";
    }
    //provide access to the healing symbol
    public static  char getHealingSymbol() {
        return HealingPotionSymbol;
    }
    //provide access to potion name
    public String getPotionName(){

        return potionName;
    }
    //provide access to the description
    public String getDescription(){

        return potionDescription;
    }
    }
