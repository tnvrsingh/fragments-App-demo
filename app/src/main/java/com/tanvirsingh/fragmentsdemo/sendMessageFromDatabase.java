package com.tanvirsingh.fragmentsdemo;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;


public class sendMessageFromDatabase {

    private static final String TAG = "SendingMFD DB";

    private final Handler handler;

    final String MESSAGES_ENDPOINT = "http://fragmentstanvir.azurewebsites.net";

    DataBaseHelper myDB;
    Context context;

    public sendMessageFromDatabase(Handler handler) {

        this.handler = handler;
        RequestParams rp = new RequestParams();
        SendMessageFromDBMethod(rp);
    }




    public void SendMessageFromDBMethod(RequestParams param) {

        Log.d(TAG, "Method started");

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

        RequestParams postParams = new RequestParams();

        // Database
        Log.d(TAG, "DATABASE OPERATIONS STARTED");

        myDB = new DataBaseHelper(context);

        Cursor res = myDB.getAllData();

        if (res.getCount() == 0) {
            Log.d(TAG, "No data found");
            return;

        }

        final ArrayList<String> messageList = new ArrayList();

        while (res.moveToNext()) {
            messageList.add(res.getString(0));
        }

        Log.d(TAG, "BUFFER LENGTH :: " + messageList.size());

        for (int i = 0; i < messageList.size(); i++) {

            client.post(MESSAGES_ENDPOINT + "/messages" + messageList.get(i).toString(), postParams, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "fuck the log");
                        }
                    });
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //Toast.makeText(, "Something went wrong :(", Toast.LENGTH_LONG).show();
                }
            });

        }

        return;

    }

}
