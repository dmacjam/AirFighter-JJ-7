package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.dmacjam.airfighterjj_7.GameView;

import java.util.Formatter;

/**
 * Created by Jakub on 2.4.2015.
 */
public class StatusMessage {
    // Status message to show Ball's (x,y) position and speed.
    private StringBuilder statusMsg = new StringBuilder();
    private StringBuilder scoreMsg = new StringBuilder();
    private Formatter formatter = new Formatter(statusMsg);
    private Paint paint;

    // Constructor
    public StatusMessage(int color) {
        paint = new Paint();
        // Set the font face and size of drawing text
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(30);
        paint.setColor(color);
    }

    public void update(PlayerCharacter player,long elapsed,Score score) {
        // Build status message
        statusMsg.delete(0, statusMsg.length());   // Empty buffer
        formatter.format("Player@(%3.0f,%3.0f),FPS=(%2.0f)", player.getX(), player.getY(),1000f/elapsed);

        scoreMsg.delete(0,scoreMsg.length());
        scoreMsg.append("Score: "+score.getPoints());
    }

    public void draw(Canvas canvas) {
        canvas.drawText(statusMsg.toString(), 10, 30, paint);
        canvas.drawText(scoreMsg.toString(), GameView.NORMALIZED_X - 250, 20,paint);
    }

    public void drawFinalScore(Canvas canvas){
        canvas.drawText(scoreMsg.toString(), 450, 600,paint);
    }
}
