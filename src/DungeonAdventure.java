
public class DungeonAdventure{
    DungeonGenerator dg;
    protected Player p;
    UI ui = new UI(this);

    public DungeonAdventure() {
        dg = new DungeonGenerator();
//        int count = spawnHallways();
//        spawnRooms(count);
        int count = spawnHallways();
        spawnRooms(count);
        spawnCharacter();
        ui.setVisible(true);
    }

    public int spawnHallways() {
        HallwayVertical hv = new HallwayVertical();
        HallwayHorizontal hh = new HallwayHorizontal();
        int count = 1;
        for (int i = 0; i < dg.finalDungeon.length; i++) {
            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
                    if (i > 1) {
                        if (dg.finalDungeon[i][j].myNorth == dg.finalDungeon[i - 2][j]) {
                            ui.spawnItem(hv, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)) + 14, (((int) Math.ceil((double)(i - 1)/2) * 20) + (((i - 1) / 2) * 51)) - 6, count++);
                        }
                    }
                    if (j > 1) {
                        if (dg.finalDungeon[i][j].myWest == dg.finalDungeon[i][j - 2]) {
                            ui.spawnItem(hh, (((int) Math.ceil((double)(j - 1) / 2) * 26) + (((j - 1) / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)) + 11, count++);
                        }
                    }
                }
            }
        }
        return count;
    }



    public int spawnRooms(int c) {
        int count = c;
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
                    ui.spawnItem(room, (((int) Math.ceil((double)j / 2) * 26) + ((j / 2) * 51)), (((int) Math.ceil((double)i/2) * 20) + ((i / 2) * 51)), count++);
                    roomExits.setLength(0);
                }
            }
        }
        return count;
    }

    public void spawnCharacter() {
        p = new Player(11, 1);
        DungeonGenerator.updatePlayerCoords(p);
        ui.spawnItem(p, (((int) Math.ceil((double)p.myCoords.y/ 2) * 26) + ((p.myCoords.y / 2) * 51) + 17), (((int) Math.ceil((double)p.myCoords.x/2) * 20) + ((p.myCoords.x / 2) * 51) + 17), 0);
        ui.refresh();
    }
}
