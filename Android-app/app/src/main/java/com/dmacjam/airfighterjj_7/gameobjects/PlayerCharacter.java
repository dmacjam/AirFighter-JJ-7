package com.dmacjam.airfighterjj_7.gameobjects;

import com.dmacjam.airfighterjj_7.GameView;

/**
 * Created by Jakub on 4.4.2015.
 */
public class PlayerCharacter extends Sprite {

    public PlayerCharacter(GameView gameView){
        super(gameView);
    }

    public void initializePosition(){
        this.x = GameView.NORMALIZED_X / 2;
        this.y = GameView.NORMALIZED_Y - this.height;
    }

    public void setXHorizontal(float x){
        if(this.isOutOfRangeLeftX(x)){
            setX(0);
        }else if (this.isOutOfRangeRightX(x)){
            setX(GameView.NORMALIZED_X - this.getWidth());
        }else{
            setX(x);
        }
    }

}
