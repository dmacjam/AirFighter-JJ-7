package com.dmacjam.airfighterjj_7;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BestScore extends ActionBarActivity {
    private static final Logger LOG = LoggerManager.getLogger(BestScore.class);
    List<String> scores = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_score);
        LOG.d("onCreate");
        getBestScore();
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

    private void getBestScore(){
        ServerRestApi.get("scores/best",null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject first = (JSONObject) response.get(i);
                        String name = first.getString("name");
                        int result = first.getInt("result");
                        scores.add(name+" - "+result);
                        LOG.d("Name: "+name+" ,result: "+result);
                    }
                }catch (JSONException e){
                    LOG.e("JSON parse exception");
                    Toast.makeText(getApplicationContext(),"Error parsing JSON data",Toast.LENGTH_LONG).show();
                }
                listViewUpdate();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LOG.w("Failed with status code "+statusCode+" and response:"+responseString);
            }

            @Override
            public void onRetry(int retryNo) {
                LOG.w("Retrying to connect #"+retryNo);
            }
        });
    }

    private void listViewUpdate(){
        ArrayAdapter<String> scoresAdapter = new ArrayAdapter<String>(this,R.layout.list_item_score,R.id.list_item_score_textview,scores);
        ListView listView = (ListView) findViewById(R.id.listViewScore);
        listView.setAdapter(scoresAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_best_score, menu);
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
