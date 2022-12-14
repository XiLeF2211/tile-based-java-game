package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject {
    GamePanel gp;
    
    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boot.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        solidArea.x = 5;
    }
}