package com.t3hh4xx0r.pokepro;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class SettingsMenu extends PreferenceActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.menu);
	}
}
