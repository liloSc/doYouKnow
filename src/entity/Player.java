package entity;

import main.GamePanel;
import main.KeyHandler;
import utils.IntPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyH;
    private final IntPoint screenPoint;

    //Constructor
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gamePanel = gp;
        this.keyH = keyH;
        //Display Player in Screen Center
        int screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        int screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        screenPoint = new IntPoint(screenX, screenY);
     //   solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 20;

        setDefaultValues();
        getPlayerImage();
    }

    public IntPoint getScreenPoint() {
        return screenPoint;
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23; //Not Screenposition but Position on Map
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = Direction.DOWN;
    }

    public void getPlayerImage() {
        try {
            sprite_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            sprite_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            sprite_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            sprite_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            sprite_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            sprite_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            sprite_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            sprite_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = Direction.UP;

            } else if (keyH.downPressed) {
                direction = Direction.DOWN;

            } else if (keyH.leftPressed) {
                direction = Direction.LEFT;

            } else {
                direction = Direction.RIGHT;
            }
            //Check Tile Collison
            collisionOn = false;
            gamePanel.cChecker.checkTile(this);
            //if Collision = false -> Player can move

            if (!collisionOn) {
                switch (direction) {
                    case UP -> worldY -= speed;
                    case DOWN -> worldY += speed;
                    case LEFT -> worldX -= speed;
                    case RIGHT -> worldX += speed;
                }
            }
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics g2) {
        BufferedImage image = null;
        switch (direction) {
            case UP -> {
                if (spriteNum == 1) {
                    image = sprite_up1;
                }
                if (spriteNum == 2) {
                    image = sprite_up2;
                }
            }
            case DOWN -> {
                if (spriteNum == 1) {
                    image = sprite_down1;
                }
                if (spriteNum == 2) {
                    image = sprite_down2;
                }
            }
            case LEFT -> {
                if (spriteNum == 1) {
                    image = sprite_left1;
                }
                if (spriteNum == 2) {
                    image = sprite_left2;
                }
            }
            case RIGHT -> {
                if (spriteNum == 1) {
                    image = sprite_right1;
                }
                if (spriteNum == 2) {
                    image = sprite_right2;
                }
            }
        }
        g2.drawImage(image, screenPoint.getX(), screenPoint.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
