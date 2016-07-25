package com.dmacjam.airfighterjj_7.gameobjects;

import com.dmacjam.airfighterjj_7.GameView;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Created by Jakub on 4.4.2015.
 * General class for every game objects.
 */
public abstract class Sprite{
    private static final Logger LOG = LoggerManager.getLogger(Sprite.class);
    protected float x,y;        //in normalized screen 1080x1920
    protected float speedX, speedY;
    protected int width,height;
    protected GameView gameView;

    public Sprite(){

    }

    public Sprite(GameView gameView){
        this.gameView = gameView;
    }

    public boolean isOutOfRangeY(){
        return this.y - height > GameView.NORMALIZED_Y;
    }

    public boolean isOutOfRangeLeftX(){
        return this.x < 0;
    }

    public boolean isOutOfRangeRightX(){
        return this.x + width > GameView.NORMALIZED_X;
    }

    public boolean isOutOfRangeLeftX(float newX){
        return newX < 0;
    }

    public boolean isOutOfRangeRightX(float newX){
        return newX + width > GameView.NORMALIZED_X;
    }

    public boolean checkCollision(Sprite sprite){
        if(pointCollision(sprite.getX(),sprite.getY())){        //top left point
            return true;
        }

        if(pointCollision(sprite.getX(),sprite.getY())){        //top center point
            return true;
        }

        if(pointCollision(sprite.getX()+sprite.getWidth(),sprite.getY()+sprite.getHeight())){        //bottom right point
            return true;
        }

        if(pointCollision(sprite.getX(),sprite.getY()+sprite.getHeight())){        //bottom left point
            return true;
        }

        if(pointCollision(sprite.getX()+sprite.getWidth(),sprite.getY())){        //top right point
            return true;
        }

        return false;
    }

    private boolean pointCollision(float x,float y){
        return (x > this.x && x < this.x+this.width &&
                y > this.y && y < this.y+this.height);
    }

    public void move(){
        x += speedX;
        y += speedY;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
