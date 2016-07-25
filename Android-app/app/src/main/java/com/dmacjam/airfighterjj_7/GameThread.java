package com.dmacjam.airfighterjj_7;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

/**
 * Thread for game logic. Call view for rendering and updating of game state.
 * Created by Jakub on 3.4.2015.
 */
public class GameThread extends Thread {
    private static final Logger LOG = LoggerManager.getLogger(GameThread.class);
    private boolean isRunning = false;
    private static final long MAX_FPS = 30;
    private static final int MAX_FRAMES_SKIPPED = 10;
    private static final long TICK_FRAME_PERIOD = 1000 / MAX_FPS;
    private GameView gameView;
    private SurfaceHolder holder;

    public GameThread(GameView gameView){
        this.gameView = gameView;
        this.holder = gameView.getHolder();
        LOG.d("GameThread constructor");
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        long startTime;
        long sleepTime;
        long elapsedTime = 0;
        int framesSkipped = 0;

        //Thread loop
        while(isRunning){
            Canvas canvas = null;
            try{
                startTime = System.currentTimeMillis();
                framesSkipped = 0;
                canvas = holder.lockCanvas();

                gameView.update(elapsedTime);
                gameView.render(canvas);


                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = TICK_FRAME_PERIOD-elapsedTime;

                try{
                    if(sleepTime>0)
                    {
                        sleep(sleepTime);
                    }

                    //it takes long time, update without rendering
                    while (sleepTime < 0 && framesSkipped < MAX_FRAMES_SKIPPED ){
                        gameView.update(elapsedTime);
                        sleepTime += TICK_FRAME_PERIOD;
                        framesSkipped++;
                    }
                }catch (InterruptedException e){
                    LOG.e("sleep error");
                }

                elapsedTime = System.currentTimeMillis() - startTime;

            }catch (Exception e){
                LOG.e("Game loop error");
            }finally{
                //always unlock canvas
                if(canvas != null){
                    holder.unlockCanvasAndPost(canvas);
                }
            }

        }

    }
}
