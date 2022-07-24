package main;
public abstract class PickupItem {
    private String player;
    protected DungeonGenerator Dungeon;
    public abstract void work (DungeonCharacter dungeonCharacter);
    public PickupItem(String player, DungeonGenerator dungeon) {
        this.player = player;
        this.Dungeon = dungeon;
    }
    public String getName() {
        return this.player;
    }

    public String toString() {
        return this.getName();
    }
}
