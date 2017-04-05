package com.tanvirsingh.fragmentsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NetworkChangeReceiver extends BroadcastReceiver{

    private static final String TAGNCR = "JSON";
    // Handler used to execute code on the UI thread

    public NetworkChangeReceiver() {

    }

    final String MESSAGES_ENDPOINT = "http://fragmentstanvir.azurewebsites.net";

    @Override
    public void onReceive(Context context, Intent intent) {

        //
        // DataBaseHelper dataBaseHelper = new DataBaseHelper();

        final RequestParams params = new RequestParams();

        if (isNetworkAvailable(context) == true){
            //CALL METHOD FROM sendMessageFromDatabase
            Handler handler = new Handler(Looper.getMainLooper());

            sendMessageFromDatabase sendMessageFromDatabaseOBJECT = new sendMessageFromDatabase(handler);


            return;

        } else {

        }
    }

        public static String[] toStringArr(StringBuffer[] sb) {
            if (sb == null) return null;
            String str[] = new String[sb.length];
            for (int i = 0; i < sb.length; i++) {
                if (sb[i] != null) {
                    str[i] = sb[i].toString();
                }
            }
            return str;
        }



    //function to check if internet is available
    private static boolean isNetworkAvailable(Context context){
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
