package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public BufferedImage textureAtlas;

    public TileManager(GamePanel gp, String textureAtlas) {
        this.gp = gp;

        tile = new Tile[54];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        try {
            this.textureAtlas = ImageIO.read(getClass().getResourceAsStream(textureAtlas));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        int ots = gp.originalTileSize;
        try {
            registerTile(0, false, 0, 0); // GRASS
            registerTile(1, false, ots, 0); // GRASS ACCENT 0
            registerTile(2, false, 2*ots, 0); // GRASS ACCENT 1
            registerTile(4, true, 4*ots, 0); // BRICK
            registerTile(5, true, 5*ots, 0); // TILED FLOOR
            registerTile(6, true, 6*ots, 0); // CHISELED STONE
            registerTile(7, false, 7*ots, 0); // PLANK
            registerTile(8, true, 8*ots, 0); // LOG VERTICAL
            registerTile(9, true, 9*ots, 0); // LOG HORIZONTAL

            // for (int j = 10; j <= 35; j++) {
            //     for (int k = 10; k <= 16; k++) {
            //         registerTile(j, true, k*ots, 0);
            //     }
            //     for (int k = 0; k <=16; k++) {
            //         for (int l = 0; l < 3; l++) {
            //             registerTile(j, true, k*ots, l*ots);
            //         }
            //         if (k > 15) {
            //             k = 0;
            //         }
            //     }
            // }

            // WATER TILES
            registerTile(10, true, 10*ots, 0); // TOP LEFT WATER
            registerTile(11, true, 11*ots, 0); // TOP RIGHT WATER
            registerTile(12, true, 12*ots, 0); // BOTTOM RIGHT WATER
            registerTile(13, true, 13*ots, 0); // BOTTOM LEFT WATER

            registerTile(14, true, 14*ots, 0); // BOTTOM WATER
            registerTile(15, true, 15*ots, 0); // LEFT WATER
            registerTile(16, true, 0, i); // RIGHT WATER
            registerTile(17, true, 1*ots, i); // TOP WATER
            registerTile(18, true, 2*ots, i); // WATER

            registerTile(19, true, 3*ots, i); // TOP LEFT WATER ACCENT
            registerTile(20, true, 4*ots, i); // TOP RIGHT WATER ACCENT
            registerTile(21, true, 5*ots, i); // BOTTOM RIGHT WATER ACCENT
            registerTile(22, true, 6*ots, i); // BOTTOM LEFT WATER ACCENT

            registerTile(23, true, 7*ots, i); // BOTTOM WATER ACCENT
            registerTile(24, true, 8*ots, i); // LEFT WATER ACCENT
            registerTile(25, true, 9*ots, i); // RIGHT WATER ACCENT
            registerTile(26, true, 10*ots, i); // TOP WATER ACCENT
            registerTile(27, true, 11*ots, i); // WATER ACCENT

            registerTile(28, true, 12*ots, i); // BOTTOM RIGHT CORNER WATER
            registerTile(29, true, 13*ots, i); // BOTTOM LEFT CORNER WATER
            registerTile(30, true, 14*ots, i); // TOP RIGHT WATER
            registerTile(31, true, 15*ots, i); // TOP LEFT CORNER WATER

            registerTile(32, true, 0, 2*ots); // BOTTOM RIGHT CORNER WATER ACCENT
            registerTile(33, true, 1*ots, 2*ots); // BOTTOM LEFT CORNER WATER ACCENT
            registerTile(34, true, 2*ots, 2*ots); // TOP RIGHT WATER ACCENT
            registerTile(35, true, 3*ots, 2*ots); // TOP LEFT CORNER WATER ACCENT

            // PATH TILES
            registerTile(36, false, 4*ots, 2*ots); // BOTTOM PATH
            registerTile(37, false, 5*ots, 2*ots); // LEFT PATH
            registerTile(38, false, 6*ots, 2*ots); // TOP PATH
            registerTile(39, false, 7*ots, 2*ots); // RIGHT PATH

            registerTile(40, false, 8*ots, 2*ots); // HORIZONTAL PATH
            registerTile(41, false, 9*ots, 2*ots); // VERTICAL PATH

            registerTile(42, false, 10*ots, 2*ots); // RIGHT TURN PATH
            registerTile(43, false, 11*ots, 2*ots); // DOWN TURN PATH
            registerTile(44, false, 12*ots, 2*ots); // LEFT TURN PATH
            registerTile(45, false, 13*ots, 2*ots); // UP TURN PATH

            registerTile(46, false, 14*ots, 2*ots); // TOP END PATH
            registerTile(47, false, 15*ots, 2*ots); // BOTTOM END PATH
            registerTile(48, false, 0, 3*ots); // LEFT END PATH
            registerTile(49, false, 1*ots, 3*ots); // RIGHT END PATH

            registerTile(50, false, 2*ots, 3*ots); // NEUTRAL VERTICAL PATH
            registerTile(51, false, 3*ots, 3*ots); // NEUTRAL HORIZONTAL PATH

            // TREE TILES
            registerTile(52, true, 4*ots, 3*ots); // TREE TOP
            registerTile(53, true, 5*ots, 3*ots); // TREE BOTTOM
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerTile(int index, boolean collisionOn, int x, int y) {
        BufferedImage croppedImage = textureAtlas.getSubimage(x, y, 16, 16);
        try {
            tile[index] = new Tile();
            tile[index].image = croppedImage;
            tile[index].collision = collisionOn;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
