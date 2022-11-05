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
        gp.objects[0] = new OBJ_Key();
        gp.objects[0].worldX = 4 * gp.tileSize;
        gp.objects[0].worldY = 6 * gp.tileSize;

        gp.objects[1] = new OBJ_Door();
        gp.objects[1].worldX = 7 * gp.tileSize;
        gp.objects[1].worldY = 18 * gp.tileSize;

        gp.objects[2] = new OBJ_Chest();
        gp.objects[2].worldX = 6 * gp.tileSize;
        gp.objects[2].worldY = 18 * gp.tileSize;

        gp.objects[3] = new OBJ_Boots();
        gp.objects[3].worldX = 12 * gp.tileSize;
        gp.objects[3].worldY = 18 * gp.tileSize;
    }
}
