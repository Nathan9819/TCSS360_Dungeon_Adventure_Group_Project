
public class DungeonAdventure{
    DungeonGenerator dg;
    protected Player p;
    UI ui = new UI(this);

    public DungeonAdventure() {
        dg = new DungeonGenerator();
        spawnHallways();
        spawnRooms();
        spawnCharacter();
        ui.setVisible(true);
    }

    public void spawnHallways() {
        HallwayVertical hv = new HallwayVertical();
        HallwayHorizontal hh = new HallwayHorizontal();
        for (int i = 0; i < dg.finalDungeon.length; i++) {
            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
                    if (i > 1) {
                        if (dg.finalDungeon[i][j].myNorth == dg.finalDungeon[i - 2][j]) {
                            ui.spawnItem(hv, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)) + 14, (((int) Math.ceil((double)(i - 1)/2) * 20) + (((i - 1) / 2) * 51)) - 6, i, j);
                        }
                    }
                    if (j > 1) {
                        if (dg.finalDungeon[i][j].myWest == dg.finalDungeon[i][j - 2]) {
                            ui.spawnItem(hh, (((int) Math.ceil((double)(j - 1) / 2) * 26) + (((j - 1) / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)) + 11, i, j);
                        }
                    }
                }
            }
        }
        return;
    }



    public void spawnRooms() {
        RoomTile room = new RoomTile();
        StringBuilder roomExits = new StringBuilder();
        for (int i = 0; i < dg.finalDungeon.length; i++) {
            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
                    if (dg.finalDungeon[i][j].myNorth == null) {
                        roomExits.append(0);
                    } else {
                        roomExits.append(1);
                    }
                    if (dg.finalDungeon[i][j].myEast == null) {
                        roomExits.append(0);
                    } else {
                        roomExits.append(1);
                    }
                    if (dg.finalDungeon[i][j].mySouth == null) {
                        roomExits.append(0);
                    } else {
                        roomExits.append(1);
                    }
                    if (dg.finalDungeon[i][j].myWest == null) {
                        roomExits.append(0);
                    } else {
                        roomExits.append(1);
                    }
                    room.setRoomImage(roomExits.toString());
                    ui.spawnItem(room, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), i, j);
                    roomExits.setLength(0);
                }
            }
        }
    }

    public void spawnCharacter() {
        p = new Player(11, 1);
        p.setRoom(dg.finalDungeon[11][1]);
        ui.spawnPlayer(p, (((int) Math.ceil((double)p.myCoords.y/ 2) * 26) + ((p.myCoords.y / 2) * 51) + 17), (((int) Math.ceil((double)p.myCoords.x/2) * 20) + ((p.myCoords.x / 2) * 51) + 17), 11, 1);
        ui.refresh();
    }
}
