public abstract class PickupItem {

    protected String itemDescription;
    protected String itemName;
    protected char symbol;

    PickupItem(String itemDescription, String itemName, char symbol){
        this.itemDescription = itemDescription;
        this.itemName = itemName;
        this.symbol = symbol;
    }

//    protected PickupItem() {
//    }
    //public static void use(Hero dungeoncharacter) {};
    //public static void use(Hero dungencharacter){};
}
