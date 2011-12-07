package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.SMSReceiver;
import com.t3hh4xx0r.pokepro.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PokeProActivity extends Activity {
	private Button mStartServiceButton;
	private Button mStopServiceButton;
	private Button mSettingsButton;
	
	TextView serviceState;
	
	static boolean serviceIsActive = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Preferences.getInstance().setContext(this);


        mStartServiceButton = (Button) findViewById(R.id.startService);
        mStartServiceButton.setOnClickListener (mStartServiceListener);
        mStopServiceButton = (Button) findViewById(R.id.stopService);
        mStopServiceButton.setOnClickListener (mStopServiceListener);
        mSettingsButton = (Button) findViewById(R.id.settingsButton);
        mSettingsButton.setOnClickListener(mSettingsButtonListener);
        
        serviceState = (TextView) findViewById(R.id.serviceState);

        if (serviceIsActive) {
        	serviceState.setText(R.string.is_active);
        } else {
        	serviceState.setText(R.string.is_not_active);
        }
	} 

	private OnClickListener mStartServiceListener = new OnClickListener() {
		public void onClick(View v) {
			startService();
		}
	};
	
	private OnClickListener mStopServiceListener = new OnClickListener() {
		public void onClick(View v) {
			stopService();
		}
	};
	
	private OnClickListener mSettingsButtonListener = new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(PokeProActivity.this, SettingsMenu.class);
				PokeProActivity.this.startActivity(myIntent);
			}
	};
	
	private void startService() {
		serviceIsActive = true;
    	serviceState.setText(R.string.is_active);
		Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
		
		IntentFilter SMSfilter = new IntentFilter(SMSReceiver.SMS_RECEIVED);
		this.registerReceiver(SMSReceiver.SMSbr, SMSfilter);
	}
	
	private void stopService() {
		serviceIsActive = false;
    	serviceState.setText(R.string.is_not_active);
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
		this.unregisterReceiver(SMSReceiver.SMSbr);
	}
}
