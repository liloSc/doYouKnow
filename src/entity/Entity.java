package entity;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Entity Class stores variables that will be used in player, monster and NPC classes
 */
public class Entity {
    public enum Direction {UP, DOWN, LEFT, RIGHT};
    //   public int x, y;
    //Position
    public int worldX, worldY;
    public int speed;
    public BufferedImage sprite_up1, sprite_up2, sprite_down1, sprite_down2, sprite_left1, sprite_left2, sprite_right1, sprite_right2;
    public Direction direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn=false;

}
