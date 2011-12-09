package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.Preferences;

import android.app.Activity;
import android.content.Intent;
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

    	if (Preferences.IS_SERVICE_ACTIVE) {
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
	
	public void startService() {
        startService(new Intent(PokeProActivity.this, PokeService.class));
		
    	Preferences.IS_SERVICE_ACTIVE = true;
    	
    	restart();
   	}
	
	public void stopService() {
        stopService(new Intent(PokeProActivity.this, PokeService.class));
        
    	Preferences.IS_SERVICE_ACTIVE = false;
       
		Toast.makeText(this, "Service will be stopped", Toast.LENGTH_SHORT).show();
		
		restart();
	}
	
	private void restart() {
        finish();
        Intent intent = new Intent(PokeProActivity.this, PokeProActivity.class);
        startActivity(intent);
        PokeWidget.updateWidget(getBaseContext());
	}
}
