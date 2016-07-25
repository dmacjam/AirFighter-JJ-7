package com.dmacjam.airfighterjj_7;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.noveogroup.android.log.Log;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;



public class Startscreen extends ActionBarActivity {
    private static final Logger LOG = LoggerManager.getLogger(Startscreen.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        LOG.e("custom logger error"+5);

        Button about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.d("Starting about...");
                v.getContext().startActivity(new Intent(v.getContext(),About.class));
            }
        });

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.d("Starting game...");
                v.getContext().startActivity(new Intent(v.getContext(),Game.class));
            }
        });

        Button bestScore = (Button) findViewById(R.id.bestScore);
        bestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.d("Starting best score...");
                v.getContext().startActivity(new Intent(v.getContext(), BestScore.class));
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.d("Starting login...");
                v.getContext().startActivity(new Intent(v.getContext(), Login.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LOG.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LOG.d("onStop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startscreen, menu);
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
}
