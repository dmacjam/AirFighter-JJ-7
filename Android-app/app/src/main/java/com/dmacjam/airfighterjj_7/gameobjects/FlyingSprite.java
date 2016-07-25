package com.dmacjam.airfighterjj_7.gameobjects;

import com.dmacjam.airfighterjj_7.GameView;

/**
 * Sprites that falls down the screen.
 * Created by Jakub on 5.4.2015.
 */
public abstract class FlyingSprite extends Sprite {

    public FlyingSprite(GameView gameView){
        super(gameView);
    }


    public void initializePosition(){
        this.y = height;
        this.x =  ((float)Math.random())*GameView.NORMALIZED_X;
        this.speedX = 0;
        this.speedY = ((float)Math.random()*15)+10;        //toto by sa mohlo zvysovat
    }
}
