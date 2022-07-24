/*
strength potions will increase your attack damage
 */
package main;

 public class StrengthPotion extends PickupItem {
        public StrengthPotion(DungeonGenerator dungeon) {
            super("StrengthPotion", dungeon);
        }
        @Override
        public void work (DungeonCharacter DungenCharacter) {
           // System.out.println(" ");
        }
    }

