public class Encapsulation extends PickupItem {
    private static String PillarName3;
    private static String PillarDescription3;
    private static char EncapsulationSymbol;

    //initialize the class and pass the variable to the super class
    Encapsulation() {
        super(PillarName3, PillarDescription3, EncapsulationSymbol);
        this.PillarName3="Pillar of Encapsulation";
        this.PillarDescription3="ONE OF THE 4 PILLARS OF OO";
        this.EncapsulationSymbol='E';

    }
    public static char getEncapsulationSymbol() {
        return EncapsulationSymbol;
    }

}