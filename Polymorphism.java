public class Polymorphism extends PickupItem  {
    private static String PillarName4="Pillar of POLYMORPHISM";
    private static String PillarDescription4="ONE OF THE 4 PILLARS OF OO!!!";
    private static char PolyMorphismSymbol='P';
    Polymorphism() {
        super(PillarName4, PillarDescription4, PolyMorphismSymbol);
    }
    public static char getPolymorphismSymbol() {

        return PolyMorphismSymbol;
    }
}