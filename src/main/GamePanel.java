package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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
    //FPS
    int fps = 60;
    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
 public   ColisionChecker cChecker=new ColisionChecker(this);
  public  Player player = new Player(this, keyH);
    //Set Players Default Position --> Entity Class
    //  int playerX = 100;
    //  int playerY = 100;
    //  int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Enabling this: Improve Games Rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();//Calls Run methode
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps; //1 second/60 --> 0.016666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; //How much time has past?
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;

            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /* Second option to run with Sleep --> not that accurate
        public void runSleep() {
            double drawInterval = 1000000000 / fps; //1 second/60 --> 0.016666 seconds
            double nextDrawTime = System.nanoTime() + drawInterval;
            while (gameThread != null) { //As long as Gamethread exists
                //System.out.println("The Game loop is running");
                //  long currentTime=System.nanoTime(); //Return currentTime in nanonseconds
                //long currentTime2= System.currentTimeMillis();
                //  System.out.println(currentTime);
                //1 Update information such as character position
                update();
                //2 Draw the screen with updated information
                repaint();
                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1000000;//Nanoseonds in Milliseconds
                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }
                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    */
    public void update() {
        //Call Update Method
        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //PArent class of this class --> JPanel
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2); //Before Player--> Layer (otherwise you could not see the player)
        player.draw(g2);
        g2.dispose();

    }
}
