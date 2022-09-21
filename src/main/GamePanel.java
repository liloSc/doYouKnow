package main;

import entity.Player;
import tile.TileManager;
import utils.IntPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements Runnable {
    //screen Setting
    final int originalTileSize = 16; //16 x 16 Tile --> Default Size Characters (for the most games)
    //16x 16 may look tiny on a big screen --> Scale it!
    final int scale = 3; //common scale
    public final int tileSize = originalTileSize * scale; //48 x 48 tilesize
    //How many tiles can be displayed
    public final int maxScreenColumn = 16; //Horizontal
    public final int maxScreenRow = 12; //Vertical
    public final int screenWidth = tileSize * maxScreenColumn;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD Settings
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
    public final int worldWidth=tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxWorldRow;
    private IntPoint screenWorldPoint = new IntPoint(0, 0);
    //FPS
    int fps = 60;
    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker=new CollisionChecker(this);
    private  Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Enabling this: Improve Games Rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public Player getPlayer() {
        return player;
    }

    public IntPoint getScreenWorldPoint() {
        return screenWorldPoint;
    }

    public void setScreenWorldPoint(int x, int y) {
        screenWorldPoint.setLocation(x, y);
    }

    public void update() {
        //Call Update Method
        player.update();
        setScreenWorldPoint(player.worldX-getWidth()/2+tileSize/2, player.worldY-getHeight()/2+tileSize/2); //center tiles on player
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //Parent class of this class --> JPanel
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2); //Before Player--> Layer (otherwise you could not see the player)
        player.draw(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        update();
        repaint();
    }
}
