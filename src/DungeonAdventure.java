
public class DungeonAdventure{
    DungeonGenerator dg;
    protected Player p;
    UI ui = new UI(this);

    public DungeonAdventure() {
        dg = new DungeonGenerator();
        spawnHallways();
        spawnCharacter();
        ui.setVisible(true);
    }

    public void spawnHallways() {
        HallwayVertical hv = new HallwayVertical(false);
        HallwayHorizontal hh = new HallwayHorizontal(false);
        RoomTile darkRoom = new RoomTile(false);
        for (int i = 0; i < dg.finalDungeon.length; i++) {
            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
                    darkRoom.setRoomImage(dg.finalDungeon[i][j].roomCode);
                    ui.spawnItem(darkRoom, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), i, j);
                }
                if (dg.finalDungeon[i][j].myRoomContents.equals("|")) {
                    ui.spawnItem(hv, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)) + 14, (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) - 6, i, j);
                }
                if (dg.finalDungeon[i][j].myRoomContents.equals("-")) {
                    ui.spawnItem(hh, (((int) Math.ceil((double) j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double) i / 2) * 20) + ((i / 2) * 51)) + 11, i, j);
                }
            }
        }
        return;
    }

    public void spawnCharacter() {
        int i = 11;
        int j = 1;
        p = new Player(i, j, 0);
        p.myRoom = dg.finalDungeon[i][j];
        ui.spawnPlayer(p, (((int) Math.ceil((double)p.myCoords.y/ 2) * 26) + ((p.myCoords.y / 2) * 51) + p.offSetJ), (((int) Math.ceil((double)p.myCoords.x/2) * 20) + ((p.myCoords.x / 2) * 51) + p.offSetI));
    }

    public String getRoomCode(int i, int j) {
        return dg.finalDungeon[i][j].roomCode;
    }

    public Room getRoom(int i, int j) {
        return dg.finalDungeon[i][j];
    }
}
