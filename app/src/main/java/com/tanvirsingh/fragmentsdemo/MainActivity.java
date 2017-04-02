package com.tanvirsingh.fragmentsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(this); //Passing context to non-activity class

    String username;
    EditText messageInput;
    Button sendButton;
    final String MESSAGES_ENDPOINT = "http://fragmentstanvir.azurewebsites.net";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = (EditText) findViewById(R.id.message_input);
        sendButton = (Button) findViewById(R.id.send_button);

        sendButton.setOnClickListener(this);

    }


    private void postMessage() {
        String text = messageInput.getText().toString();

        if (text.equals("")) {
            return;
        }

        RequestParams params = new RequestParams();

        params.put("text", text);
        params.put("name", username);
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();
        //client.setLoggingLevel(Log.ERROR);

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

        client.post(MESSAGES_ENDPOINT + "/messages", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

}