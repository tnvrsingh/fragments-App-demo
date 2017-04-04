package com.tanvirsingh.fragmentsdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.Date;
import cz.msebera.android.httpclient.Header;

import android.database.Cursor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JSON";

    EditText messageInput;
    Button sendButton;
    final String MESSAGES_ENDPOINT = "http://fragmentstanvir.azurewebsites.net";

    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataBaseHelper(this);

        messageInput = (EditText) findViewById(R.id.message_input);
        sendButton = (Button) findViewById(R.id.send_button);

        sendButton.setOnClickListener(this);

    }


    private void postMessage() {
        String text = messageInput.getText().toString();

        if (text.equals("")) {
            return;
        }

        final RequestParams params = new RequestParams();

        params.put("text", text);
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();

        //pusher
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("87ded5c4cb1e22c46dd4", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
            }
        });

        pusher.connect();

        //Check Internet connectivity

        if (isNetworkAvailable() == true){
            //If network is available, POST to Server
            client.post(MESSAGES_ENDPOINT + "/messages", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, params.toString());
                            messageInput.setText("");
                        }
                    });
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                }
            });

            return;

        } else {
            Log.d(TAG, "No Internet! Sending to DB");
            boolean isInserted =   myDB.insertData(params.toString());
            if (isInserted = true){
                Log.d(TAG, "Inserted into Database : " + params.toString());
                messageInput.setText("");
            }

            else{
                Toast.makeText(MainActivity.this,"Error! Data Not Inserted",Toast.LENGTH_LONG).show();
            }

        }

    }

    private boolean isNetworkAvailable(){
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet and not in Airplane mode
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile data
                    return true;
                }
            } else {
                // not connected to the internet
                Toast.makeText(getApplicationContext(), "No internet connection available!", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

}