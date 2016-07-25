package com.dmacjam.airfighterjj_7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dmacjam.airfighterjj_7.gameobjects.Score;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Created by Jakub on 2.4.2015.
 */
public class Game extends Activity {
    private static final Logger LOG = LoggerManager.getLogger(Game.class);
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gameView = new GameView(this);
        setContentView(gameView);
        LOG.d("Game created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    public void startGameOver(Score score){
        Intent gameOver = new Intent(this,GameOver.class);
        gameOver.putExtra("SCORE",score.getPoints());
        startActivity(gameOver);
    }

}
