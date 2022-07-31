public class Inheritance extends PickupItem {
    private static String PillarName1 = "Pillar of inheritance";
    private static String PillarDescription1 = "ONE OF THE 4 PILLARS OF OO!!!";
    private static char InheritanceSymbol = 'I';
    //intialize the class and pass the variable to the super class
    Inheritance() {
        super(PillarName1, PillarDescription1, InheritanceSymbol);
    }
    public static char getInheritanceSymbol() {

        return InheritanceSymbol;
    }
}