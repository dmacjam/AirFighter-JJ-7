package com.dmacjam.airfighterjj_7;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;


public class About extends ActionBarActivity {
    private static final Logger LOG = LoggerManager.getLogger(About.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LOG.d("onCreate");
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
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
