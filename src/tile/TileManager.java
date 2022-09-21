package tile;

import main.GamePanel;
import utils.IntPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
  public  Tile[] tile;

    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");

    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath); //import map as txt file
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); //Read 1 line of textfile
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;


                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int tileCol = 0;
        int tileRow = 0;

        IntPoint screenWorldPoint = gp.getScreenWorldPoint();
        IntPoint UpperLeftBound = screenWorldPoint.deduce(gp.tileSize, gp.tileSize);
        IntPoint BottomRightBound = screenWorldPoint.add(gp.tileSize, gp.tileSize).add(gp.getSize());

        while (tileCol < gp.maxWorldCol && tileRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[tileCol][tileRow];
            IntPoint tileWorldPos = new IntPoint(tileCol * gp.tileSize, tileRow * gp.tileSize);

            if (tileWorldPos.isBetween(UpperLeftBound, BottomRightBound)){
                IntPoint tileScreenPos = tileWorldPos.distanceFrom(screenWorldPoint);
                g2.drawImage(tile[tileNum].image, tileScreenPos.getX(), tileScreenPos.getY(), gp.tileSize, gp.tileSize, null);
            }
            //Only Draw in Boundary
            tileCol++;
            if (tileCol == gp.maxWorldCol) {
                tileCol = 0;
                tileRow++;
            }
        }


    }
}
