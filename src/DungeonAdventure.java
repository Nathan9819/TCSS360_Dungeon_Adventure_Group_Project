
public class DungeonAdventure{
    DungeonGenerator dg;
    protected Player p;
    UI ui = new UI(this);

    public DungeonAdventure() {
        dg = new DungeonGenerator();
        spawnHallways();
//        spawnRooms();
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
                    if (i > 1) {
                        if (dg.finalDungeon[i][j].myNorth == dg.finalDungeon[i - 2][j]) {
                            ui.spawnItem(hv, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)) + 14, (((int) Math.ceil((double)(i - 1)/2) * 20) + (((i - 1) / 2) * 51)) - 6, i - 1, j);
                        }
                    }
                    if (j > 1) {
                        if (dg.finalDungeon[i][j].myWest == dg.finalDungeon[i][j - 2]) {
                            ui.spawnItem(hh, (((int) Math.ceil((double)(j - 1) / 2) * 26) + (((j - 1) / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)) + 11, i, j - 1);
                        }
                    }
                }
            }
        }
        return;
    }



//    public void spawnRooms() {
//        RoomTile darkRoom = new RoomTile(false);
//        for (int i = 0; i < dg.finalDungeon.length; i++) {
//            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
//                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
//                    darkRoom.setRoomImage(dg.finalDungeon[i][j].roomCode);
//                    ui.spawnItem(darkRoom, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), i, j);
//                }
//            }
//        }
//    }

    public void spawnCharacter() {
        int i = 11;
        int j = 1;
        p = new Player(i, j);
        p.setRoom(dg.finalDungeon[i][j]);
        ui.spawnPlayer(p, (((int) Math.ceil((double)p.myCoords.y/ 2) * 26) + ((p.myCoords.y / 2) * 51) + 17), (((int) Math.ceil((double)p.myCoords.x/2) * 20) + ((p.myCoords.x / 2) * 51) + 17));
    }

    public String getRoomCode(int i, int j) {
        return dg.finalDungeon[i][j].roomCode;
    }

    public Room getRoom(int i, int j) {
        return dg.finalDungeon[i][j];
    }
}
