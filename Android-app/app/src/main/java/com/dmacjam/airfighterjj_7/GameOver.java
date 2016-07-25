package com.dmacjam.airfighterjj_7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import org.apache.http.Header;
import org.json.JSONArray;


public class GameOver extends ActionBarActivity {
    private static final Logger LOG = LoggerManager.getLogger(GameOver.class);
    String id;
    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        int score = 0;
        if (extras != null) {
            score = extras.getInt("SCORE");
            LOG.d("Score:"+score);
        }


        if (loadUserCache()){
            setUserScore(id,score);

            TextView name = (TextView) findViewById(R.id.name);
            name.setText(mName);
        }

        TextView result = (TextView) findViewById(R.id.result);
        result.setText(String.valueOf(score));



        ImageButton playAgain = (ImageButton) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.d("Play again pressed");
                v.getContext().startActivity(new Intent(v.getContext(), Game.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(this, Startscreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);    //Clear back stack and start running activity if it is running, else create new.
        startActivity(intent);
        finish();
    }

    public boolean loadUserCache(){
        SharedPreferences prefs = getSharedPreferences(Login.CACHE_FILE, Context.MODE_PRIVATE);
        String email = prefs.getString(Login.USER_MAIL_REF, null);
        LOG.i("Email: "+email);
        if(email == null){
            LOG.w("Email is not set");
            return false;
        }
        mName = prefs.getString(Login.USER_NAME_REF, null);
        LOG.i("Name: "+mName);
        if(mName == null){
            LOG.w("Name is not set");
            return false;
        }

        id = prefs.getString(Login.USER_ID_REF, null);
        LOG.i("Id: "+id);
        if(id == null){
            LOG.w("Id is not set");
            return false;
        }


        return true;
    }

    public void setUserScore(String id,int result){
        ServerRestApi.post("user/"+id+"/score/"+String.valueOf(result),null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LOG.d("Score for user written with code "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LOG.w("Failed with status code "+statusCode);
            }
        });
    }

}
