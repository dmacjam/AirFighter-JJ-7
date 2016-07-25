package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.dmacjam.airfighterjj_7.GameView;
import com.dmacjam.airfighterjj_7.R;

/**
 * Created by Jakub on 2.4.2015.
 */
public class AirFighter extends PlayerCharacter {

    // Constructor
    public AirFighter(GameView gameView) {
        super(gameView);

        this.width = Assets.airfighter.getWidth();
        this.height = Assets.airfighter.getHeight();
        this.speedX = 0;
        this.speedY = 0;

        initializePosition();
    }

    /*public void moveWithCollisionDetection(BoundingBox box,long elapsed) {
        delta = 1.0f/elapsed;
        // Get new (x,y) position
        //x += speedX;
        //y += speedY*delta;

        //yScreen = y*(box.yMax+1);

        // Detect collision and react
        if (xScreen + radius > box.xMax) {
            speedX = -speedX;
            xScreen = box.xMax-radius;
            x = xScreen / box.xMax;
        } else if (xScreen - radius < box.xMin) {
            speedX = -speedX;
            xScreen = box.xMin+radius;
            x = xScreen / box.xMax;
        }
        if (yScreen + radius >= box.yMax) {
            speedY = -speedY;
            yScreen = box.yMax-radius;
            y = yScreen / box.yMax;
        } else if (yScreen - radius <= box.yMin) {
            speedY = -speedY;
            yScreen = box.yMin+radius;
            y = yScreen / box.yMax;
        }
    }*/

}
