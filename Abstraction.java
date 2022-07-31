public class Abstraction extends PickupItem {
    //declare the variable
    private static String PillarName2;
    private static String PillarDescription2;
    private static char AbstractionSymbol;
    //initialize the class and pass the variable to the super class
    Abstraction() {
        super(PillarName2, PillarDescription2, AbstractionSymbol);
        //initialize the varibale
        this.PillarName2="Pillar of Abstraction";
        this.PillarDescription2="ONE pillar of the 4 PILLARS OF OO";
        this.AbstractionSymbol='A';

    }
    //method for abstraction symbol
    public static char getAbstractionSymbol() {

        return AbstractionSymbol;
    }
}