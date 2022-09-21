package utils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class IntPoint {
    private int x;
    private int y;
    public IntPoint (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

    public IntPoint add(@NotNull Dimension dimension){
        return add(dimension.width, dimension.height);
    }

    public IntPoint add(int x, int y){
        return new IntPoint(this.x+x, this.y+y);
    }

    public IntPoint deduce(@NotNull Dimension dimension){
        return deduce(dimension.width, dimension.height);
    }

    public IntPoint deduce(int x, int y){
        return new IntPoint(this.x-x, this.y-y);
    }

    public IntPoint distanceFrom(IntPoint other){
        return deduce(other.x, other.y);
    }

    public boolean isBetween(@NotNull IntPoint leftUp, IntPoint rightDown){
        return (x>=leftUp.x && x<=rightDown.x) && (y>=leftUp.y && y<=rightDown.y);
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
}
