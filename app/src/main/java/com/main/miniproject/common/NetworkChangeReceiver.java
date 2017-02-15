package com.main.miniproject.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {
	 
    @Override
    public void onReceive(final Context context, final Intent intent) { 
        String status = InternetConnection.getConnectivityStatusString(context);  
    }
}
