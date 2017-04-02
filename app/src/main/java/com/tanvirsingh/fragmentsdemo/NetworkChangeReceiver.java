package com.tanvirsingh.fragmentsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kalol on 2/4/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    Context mContext;
    public NetworkChangeReceiver(Context mContext){
        this.mContext = mContext;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
