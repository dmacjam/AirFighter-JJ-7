package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dmacjam.airfighterjj_7.GameView;
import com.dmacjam.airfighterjj_7.R;

/**
 * Created by Jakub on 5.4.2015.
 */
public class Assets {
    public static Bitmap airfighter;
    public static Bitmap life;
    public static Bitmap gameOver;
    public static Bitmap start;
    public static Bitmap missile;

    public static void load(GameView gameView){
        airfighter = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fighter_small4);
        life = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.hearth);
        gameOver = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.game_over_big);
        start = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.play_big);
        missile = BitmapFactory.decodeResource(gameView.getResources(),R.drawable.missile);
        //TODO scalovanie podla sirky vysky obrazovky
    }
}
