package com.dmacjam.airfighterjj_7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dmacjam.airfighterjj_7.gameobjects.AirFighter;
import com.dmacjam.airfighterjj_7.gameobjects.Assets;
import com.dmacjam.airfighterjj_7.gameobjects.Bonus;
import com.dmacjam.airfighterjj_7.gameobjects.BoundingBox;
import com.dmacjam.airfighterjj_7.gameobjects.Enemy;
import com.dmacjam.airfighterjj_7.gameobjects.Life;
import com.dmacjam.airfighterjj_7.gameobjects.Missile;
import com.dmacjam.airfighterjj_7.gameobjects.PlayerCharacter;
import com.dmacjam.airfighterjj_7.gameobjects.Score;
import com.dmacjam.airfighterjj_7.gameobjects.StatusMessage;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jakub on 2.4.2015.
 * Game view where everything is drawing.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,SensorEventListener{
    private static final Logger LOG = LoggerManager.getLogger(GameView.class);
    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private BoundingBox box;
    private StatusMessage statusMsg;
    private GameThread gameThread;
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<Bonus> bonuses = new ArrayList<Bonus>();
    private PlayerCharacter playerCharacter;
    private Score score;
    private Game game;
    private GameState state;
    private int level;
    //private Drawable renderer;

    enum GameState{
        Playing,
        Paused,
        GameOver
    }

    //reference screen
    public static final int NORMALIZED_X = 1080;
    public static final int NORMALIZED_Y = 1920;
    public static final int ACCELEROMETER_MULTIPLY_CHANGE = 3;
    public static final int[] LEVEL_POINTS = {20,50,100,150,200};


    // For touch inputs - previous touch (x, y)
    private float currentX;
    private float currentY;
    private float previousX;
    private float previousY;


    public GameView(Context context) {
        super(context);
        LOG.d("GameView constructor started");
        this.game = (Game) context;
        state = GameState.Playing;

        //load images
        Assets.load(this);
        LOG.d("Assets loaded.");

        //create sprites
        box = new BoundingBox(Color.BLACK);
        playerCharacter = new AirFighter(this);
        statusMsg = new StatusMessage(Color.CYAN);
        bonuses.add(new Life(this));
        bonuses.add(new Life(this));
        enemies.add(new Missile(this));
        enemies.add(new Missile(this));
        score = new Score();
        level = 1;

        //implement surface holder
        getHolder().addCallback(this);
        //create game thread
        gameThread = new GameThread(this);

        //register accelerometer
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        boolean hasAccelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0;
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        //sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_GAME);

        //to handle events
        this.setFocusableInTouchMode(true);
        LOG.d("GameView constructor ended");
    }

    /**
     * Rendering of all objects in the game.
     * @param canvas Canvas where to render.
     */
    protected void render(Canvas canvas) {
        switch(state){
            case Playing:
                renderRunning(canvas);
                break;
            case GameOver:
                gameOver(canvas);
                break;
            default:
                break;
        }

    }

    /**
     * Updating game state.
     * @param elapsed
     */
    protected void update(long elapsed){
        if(state == GameState.Playing){
            updateRunning(elapsed);
        }
    }

    private void renderRunning(Canvas canvas){
        box.draw(canvas);
        statusMsg.draw(canvas);

        //TODO render by nemala kazda instancia robit sama, sprav to tu podla modelov
        canvas.drawBitmap(Assets.airfighter,getScreenX(playerCharacter.getX()),getScreenY(playerCharacter.getY()),null);

        //render bonuses
        for(Bonus bonus: bonuses){
            canvas.drawBitmap(Assets.life,getScreenX(bonus.getX()),getScreenY(bonus.getY()),null);
        }

        for(Enemy enemy: enemies){
            canvas.drawBitmap(Assets.missile,getScreenX(enemy.getX()),getScreenY(enemy.getY()),null);
        }
    }

    private void updateRunning(long elapsed){
        //move objects
        for(Bonus bonus:bonuses){
            bonus.move();
        }

        for(Enemy enemy:enemies){
            enemy.move();
        }

        statusMsg.update(playerCharacter,elapsed,score);

        removeObjectsOutOfRange();
        updateLevel();
        createEnemies();
        createBonuses();
        checkCollisions();
        //check passes to add points
    }


    private void updateLevel() {
        if(level < LEVEL_POINTS.length && score.getPoints() > LEVEL_POINTS[level-1]){
            level++;
        }
    }


    private void checkCollisions(){
        for(Enemy enemy: enemies){
            if(playerCharacter.checkCollision(enemy)){
                state = GameState.GameOver;
            }
        }

        Iterator<Bonus> iteratorB = bonuses.iterator();
        while(iteratorB.hasNext()){
            Bonus bonus = iteratorB.next();
            if(playerCharacter.checkCollision(bonus)){
                iteratorB.remove();
                score.addPoints(level*5);
            }
        }
    }

    /**
     * Removes objects out of the screen.
     */
    private void removeObjectsOutOfRange(){
        Iterator<Enemy> iteratorE = enemies.iterator();
        while(iteratorE.hasNext()){
            Enemy enemy = iteratorE.next();
            if(enemy.isOutOfRangeY()){
                iteratorE.remove();
                score.addPoints(1);
            }
        }

        Iterator<Bonus> iteratorB = bonuses.iterator();
        while(iteratorB.hasNext()){
            Bonus bonus = iteratorB.next();
            if(bonus.isOutOfRangeY()){
                iteratorB.remove();
            }
        }
    }

    private void createEnemies(){
        if(enemies.size() < level){
            enemies.add(new Missile(this));
        }
    }

    private void createBonuses(){
        if(bonuses.size() < 2){
            bonuses.add(new Life(this));
        }
    }


    private void gameOver(Canvas canvas){
        LOG.d("Game over started...");

        box.draw(canvas);
        canvas.drawBitmap(Assets.gameOver, getScreenX(150), getScreenY(NORMALIZED_Y / 2), null);
        canvas.drawBitmap(Assets.start,getScreenX(400),getScreenY(1300),null);
        statusMsg.drawFinalScore(canvas);
        game.startGameOver(score);

        LOG.d("Missile width, height"+Assets.missile.getWidth()+" "+Assets.missile.getHeight());
        LOG.d("Game over ended...");
    }

    public void pause(){
        state = GameState.Paused;
        killGameThread();
    }

    public void resume(){
        if(state != GameState.GameOver){
            state = GameState.Playing;
        }
        gameThread = new GameThread(this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    private float getScreenX(float x){
        return x*(box.xMax / (float) NORMALIZED_X);
    }

    private float getScreenY(float y){
        return y*(box.yMax / (float) NORMALIZED_Y);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        return true;  // Event handled
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LOG.d("SurfaceCreated method started");
        //Create and start the thread
        if(!gameThread.isAlive()){
            gameThread = new GameThread(this);
            gameThread.setRunning(true);
            gameThread.start();
            LOG.d("Game thread created");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        box.set(0,0,width,height);
        LOG.d("Surface changed...");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LOG.d("SurfaceDestroyed method started");
        if(gameThread.isAlive()){
            killGameThread();
        }
    }

    public void killGameThread(){
        gameThread.setRunning(false);
        boolean retry = true;
        while(retry){
            try{
                gameThread.join();
                retry = false;
            }catch(InterruptedException e){
                LOG.d("Error destroying the thread");
            }
        }
        LOG.d("GameThread stopped");
    }

    @Override
    /**
     * Callback that is called at time specified time limits.
     * Event values are changes in accelerometer values.
     */
    public void onSensorChanged(SensorEvent event) {
        float currentX = event.values[0];
        float currentY = event.values[1];
        float currentZ = event.values[2];

        //move player
        playerCharacter.setXHorizontal(playerCharacter.getX()-(currentX*ACCELEROMETER_MULTIPLY_CHANGE));

        previousX = currentX;
        previousY = currentY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not implemented yet
    }
}
