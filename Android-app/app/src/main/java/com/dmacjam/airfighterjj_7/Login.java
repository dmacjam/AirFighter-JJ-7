package com.dmacjam.airfighterjj_7;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Login extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
                                                        GoogleApiClient.OnConnectionFailedListener {
    private static final Logger LOG = LoggerManager.getLogger(Login.class);
    private GoogleApiClient mGoogleApiClient;   // Client used to interact with Google APIs
    private static final int RC_SIGN_IN = 0;    //Request code used to invoke sign in user interactions
    private boolean mIntentInProgress;          //A flag indicating that a PendingIntent is in progress and prevents us from starting further intents.

    public static final String CACHE_FILE = "login";
    public static final String USER_MAIL_REF = "email";
    public static final String USER_NAME_REF = "name";
    public static final String USER_ID_REF = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void onConnected(Bundle bundle) {
        LOG.i("User is connected");
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personGooglePlusProfile = currentPerson.getUrl();
            String personId = currentPerson.getId();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            getUserFromServer(email,personName);

            TextView name = (TextView) findViewById(R.id.loggedUserName);
            name.setText(personName);

            LOG.i("Email: " + email + ", personId: " + personId + ", person name: " + personName);
        }
    }

    /**
     * Connect to server and send logged user info.
     * @param email
     * @param name
     */
    public void getUserFromServer(final String email,final String name){
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("name", name);

        JSONObject jsonParams = new JSONObject();
        StringEntity entity=null;

        try {
            jsonParams.put("email", email);
            jsonParams.put("surname",name);
            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException e) {
            LOG.e("Json exception");
            e.printStackTrace();
        } catch (IOException e){
            LOG.e("StringEntity convert error");
            e.printStackTrace();
        }

        ServerRestApi.client.post(this,ServerRestApi.getAbsoluteUrl("users-json"),entity,"application/json",new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LOG.w("Failed with status code "+statusCode+" and response:"+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                cacheUserToken(email,name,responseString);
                LOG.d("User id: "+responseString);
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                LOG.w("Retrying to connect #"+retryNo);
            }
        });
    }

    @Override
    /**
     * Callback if our Activity loses its service connection.
     */
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Cache user info into shared preferences.
     * @param email
     * @param name
     * @param id
     */
    private void cacheUserToken(String email,String name,String id)
    {
        SharedPreferences prefs = getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_MAIL_REF, email);
        editor.putString(USER_NAME_REF, name);
        editor.putString(USER_ID_REF, id);
        editor.commit();
    }
}
