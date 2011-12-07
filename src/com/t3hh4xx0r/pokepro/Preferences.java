package com.t3hh4xx0r.pokepro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    private static Preferences _Current = null;

    public static Preferences getInstance() {
        if (_Current == null)
            _Current = new Preferences();
        return _Current;
    }

    private SharedPreferences mPreferences = null;

    public void setContext(Context context) {
        if (context == null && mPreferences != null) {
            mPreferences = null;
        } else if (context != null) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    static final String ENABLE_NOTIFICATIONS = "enable_notifications";
    static final String ENABLE_INTRUSIVE_NOTIFICATIONS = "enable_intrusive_notifications";

    public boolean getEnableNotifications() {
        return mPreferences.getBoolean(ENABLE_NOTIFICATIONS, true);
    }
    public boolean getEnableIntrusiveNotifications() {
    	return mPreferences.getBoolean(ENABLE_INTRUSIVE_NOTIFICATIONS, false);
    }
}
