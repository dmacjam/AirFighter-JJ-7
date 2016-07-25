package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Canvas;

import com.dmacjam.airfighterjj_7.GameView;

/**
 * Created by Jakub on 4.4.2015.
 */
public class Missile extends Enemy{

    public Missile(GameView gameView){
        super(gameView);
        this.width = Assets.missile.getWidth();
        this.height = Assets.missile.getHeight();
        initializePosition();
    }

}
