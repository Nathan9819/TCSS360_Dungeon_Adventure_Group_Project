
public class DungeonAdventure{
    DungeonGenerator dg;
    UI ui = new UI(this);

    public DungeonAdventure() {
        dg = new DungeonGenerator();
        spawnRooms();
        ui.window.setVisible(true);
    }

    public void spawnRooms() {
        RoomTile room = new RoomTile();
        HallwayVertical hv = new HallwayVertical();
        HallwayHorizontal hh = new HallwayHorizontal();
        int count = 0;
        for (int i = 0; i < dg.finalDungeon.length; i++) {
            for (int j = 0; j < dg.finalDungeon[0].length; j++) {
                if (j % 2 == 1 && i % 2 == 1 && dg.finalDungeon[i][j].myRoomContents.equals("S")) {
                    ui.spawnItem(room, (((52 * j) + (32 * (j - 1))) / 2), (((52 * i) + (32 * (i - 1))) / 2), count++);
//                    if (i < dg.finalDungeon.length - 1) {
//                        if (dg.finalDungeon[i][j].mySouth == dg.finalDungeon[i + 2][j]) {
//                            System.out.println("Vertical hallway added");
//                            ui.spawnItem(hv, (((52 * j) + (32 * (j - 1))) / 2), (((52 * (i + 1)) + (32 * (i + 1))) / 2), count++);
//                        }
//                    }
//                    if (j < dg.finalDungeon[0].length - 1) {
//                        if (dg.finalDungeon[i][j].myEast == dg.finalDungeon[i][j + 2]) {
//                            System.out.println("Horizontal hallway added");
//                            ui.spawnItem(hh, ((((52 * (j + 1)) + (32 * (j + 1))) / 2) + 14), ((((52 * i) + (32 * i)) / 2) + 10), count++);
//                        }
//                    }
                }
            }
        }
    }
}
