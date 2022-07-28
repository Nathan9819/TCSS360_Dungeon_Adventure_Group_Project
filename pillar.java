/*
strength potions will increase your attack damage
 */
package main;

 public class pillar extends PickupItem {
        public StrengthPotion(DungeonGenerator dungeon) {
            super("pillar", dungeon);
        }
        @Override
        public void work (DungeonCharacter DungenCharacter) {
           // System.out.println(" ");
        }
    }

