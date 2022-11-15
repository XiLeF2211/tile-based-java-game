package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetPlacer {
    GamePanel gp;

    public AssetPlacer(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objects[0] = new OBJ_Key(gp);
        gp.objects[0].worldX = 27 * gp.tileSize;
        gp.objects[0].worldY = 34 * gp.tileSize;

        gp.objects[1] = new OBJ_Door(gp);
        gp.objects[1].worldX = 26 * gp.tileSize;
        gp.objects[1].worldY = 15 * gp.tileSize;

        gp.objects[2] = new OBJ_Chest(gp);
        gp.objects[2].worldX = 26 * gp.tileSize;
        gp.objects[2].worldY = 13 * gp.tileSize;

        gp.objects[3] = new OBJ_Boots(gp);
        gp.objects[3].worldX = 27 * gp.tileSize;
        gp.objects[3].worldY = 33 * gp.tileSize;
    }
}
