package com.tanvirsingh.fragmentsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        if (isNetworkAvailable(context) == true){
            //check for messages to be sent


            return;
        } else {

        }
    }

    //function to check if internet is available
    private boolean isNetworkAvailable(Context context){
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet and not in Airplane mode
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    Toast.makeText(context, activeNetwork.getTypeName() + " Connected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile data
                    Toast.makeText(context, activeNetwork.getTypeName() + " Connected", Toast.LENGTH_SHORT).show();
                    return true;
                }
            } else {
                // not connected to the internet
                Toast.makeText(context, "No internet connection available!", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e){
            return false;
        }
        return false;
    }
}

