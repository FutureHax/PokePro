package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.SMSReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class PokeService extends Service {
	public static String ACTION_UPDATE_SERVICE = "action_update_service";

	@Override
	public IBinder onBind(Intent arg0) {
	    return null;
	}
	
	public void onCreate(){
	    super.onCreate();
	    startService();
	    
	}
	
	public void onDestroyed(){
		this.unregisterReceiver(SMSReceiver.SMSbr);
    	if (Preferences.IS_SERVICE_ACTIVE) Preferences.IS_SERVICE_ACTIVE = false;
	    super.onDestroy();
	}
	
	public void startService() {
		IntentFilter SMSfilter = new IntentFilter(SMSReceiver.SMS_RECEIVED);
		this.registerReceiver(SMSReceiver.SMSbr, SMSfilter);
	    Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    	if (!Preferences.IS_SERVICE_ACTIVE) Preferences.IS_SERVICE_ACTIVE = true;
	}
}
