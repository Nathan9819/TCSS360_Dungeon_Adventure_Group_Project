import java.util.Scanner;

public class BattleSystem {
    public static void doBattle(final Hero thePlayer, final Monster theEnemy) {
        Scanner in = new Scanner(System.in);
        //boolean cont = true;
        while (thePlayer.getHP() > 0 && theEnemy.getHP() > 0) {
            int atk = thePlayer.getAtkSpd() % theEnemy.getAtkSpd();
            if (atk <= 0) {
                atk = 1;
            }
            for (int i = atk; i > 0; i--) {
                System.out.print("Please input your move: Attack or Special ");
                String next = in.next();
                if (next.toLowerCase().startsWith("special")) {
                    thePlayer.special(theEnemy);
                } else if (next.toLowerCase().startsWith("attack")) {
                    thePlayer.attack(theEnemy);
                } else {
                    System.out.print("Invalid input. ");
                    i++;
                }
                if (theEnemy.getHP() <= 0) {
                    i = 0;
                }
            }
            if (theEnemy.getHP() <= 0) {
                continue;
            }
            theEnemy.attack(thePlayer);
        }
        in.close();
        //return thePlayer;
    }

}