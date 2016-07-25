package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dmacjam.airfighterjj_7.GameView;
import com.dmacjam.airfighterjj_7.R;

/**
 * Created by Jakub on 4.4.2015.
 */
public class Life extends Bonus {

    public Life(GameView gameView){
        super(gameView);
        this.width = Assets.life.getWidth();
        this.height = Assets.life.getHeight();
        initializePosition();
    }
}
