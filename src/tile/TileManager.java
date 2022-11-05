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
        int i = gp.originalTileSize;
        try {
            registerTile(0, false, 0, 0); // GRASS
            registerTile(1, false, i, 0); // GRASS ACCENT 0
            registerTile(2, false, 2*i, 0); // GRASS ACCENT 1
            registerTile(4, true, 4*i, 0); // BRICK
            registerTile(5, true, 5*i, 0); // TILED FLOOR
            registerTile(6, true, 6*i, 0); // CHISELED STONE
            registerTile(7, false, 7*i, 0); // PLANK
            registerTile(8, true, 8*i, 0); // LOG VERTICAL
            registerTile(9, true, 9*i, 0); // LOG HORIZONTAL

            // WATER TILES
            registerTile(10, true, 10*i, 0); // TOP LEFT WATER
            registerTile(11, true, 11*i, 0); // TOP RIGHT WATER
            registerTile(12, true, 12*i, 0); // BOTTOM RIGHT WATER
            registerTile(13, true, 13*i, 0); // BOTTOM LEFT WATER

            registerTile(14, true, 14*i, 0); // BOTTOM WATER
            registerTile(15, true, 15*i, 0); // LEFT WATER
            registerTile(16, true, 0, i); // RIGHT WATER
            registerTile(17, true, 1*i, i); // TOP WATER
            registerTile(18, true, 2*i, i); // WATER

            registerTile(19, true, 3*i, i); // TOP LEFT WATER ACCENT
            registerTile(20, true, 4*i, i); // TOP RIGHT WATER ACCENT
            registerTile(21, true, 5*i, i); // BOTTOM RIGHT WATER ACCENT
            registerTile(22, true, 6*i, i); // BOTTOM LEFT WATER ACCENT

            registerTile(23, true, 7*i, i); // BOTTOM WATER ACCENT
            registerTile(24, true, 8*i, i); // LEFT WATER ACCENT
            registerTile(25, true, 9*i, i); // RIGHT WATER ACCENT
            registerTile(26, true, 10*i, i); // TOP WATER ACCENT
            registerTile(27, true, 11*i, i); // WATER ACCENT

            registerTile(28, true, 12*i, i); // BOTTOM RIGHT CORNER WATER
            registerTile(29, true, 13*i, i); // BOTTOM LEFT CORNER WATER
            registerTile(30, true, 14*i, i); // TOP RIGHT WATER
            registerTile(31, true, 15*i, i); // TOP LEFT CORNER WATER

            registerTile(32, true, 0, 2*i); // BOTTOM RIGHT CORNER WATER ACCENT
            registerTile(33, true, 1*i, 2*i); // BOTTOM LEFT CORNER WATER ACCENT
            registerTile(34, true, 2*i, 2*i); // TOP RIGHT WATER ACCENT
            registerTile(35, true, 3*i, 2*i); // TOP LEFT CORNER WATER ACCENT

            // PATH TILES
            registerTile(36, false, 4*i, 2*i); // BOTTOM PATH
            registerTile(37, false, 5*i, 2*i); // LEFT PATH
            registerTile(38, false, 6*i, 2*i); // TOP PATH
            registerTile(39, false, 7*i, 2*i); // RIGHT PATH

            registerTile(40, false, 8*i, 2*i); // HORIZONTAL PATH
            registerTile(41, false, 9*i, 2*i); // VERTICAL PATH

            registerTile(42, false, 10*i, 2*i); // RIGHT TURN PATH
            registerTile(43, false, 11*i, 2*i); // DOWN TURN PATH
            registerTile(44, false, 12*i, 2*i); // LEFT TURN PATH
            registerTile(45, false, 13*i, 2*i); // UP TURN PATH

            registerTile(46, false, 14*i, 2*i); // TOP END PATH
            registerTile(47, false, 15*i, 2*i); // BOTTOM END PATH
            registerTile(48, false, 0, 3*i); // LEFT END PATH
            registerTile(49, false, 1*i, 3*i); // RIGHT END PATH

            registerTile(50, false, 2*i, 3*i); // NEUTRAL VERTICAL PATH
            registerTile(51, false, 3*i, 3*i); // NEUTRAL HORIZONTAL PATH

            // TREE TILES
            registerTile(52, true, 4*i, 3*i); // TREE TOP
            registerTile(53, true, 5*i, 3*i); // TREE BOTTOM
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
