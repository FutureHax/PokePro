package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.PokeProActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class PokeWidget extends AppWidgetProvider {

	public static String ACTION_UPDATE_SERVICE = "action_update_service";
	
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) { 	
    	updateWidget(context);
    }

    public void onReceive(Context context, Intent intent) {
     super.onReceive(context, intent);
     
     	if (intent.getAction().equals(ACTION_UPDATE_SERVICE)) {
            Intent myIntent = new Intent(context, PokeProActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
            context.startActivity(myIntent);
     	}
    }

	public static void updateWidget(Context context) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);

		if (Preferences.IS_SERVICE_ACTIVE) {
        	updateViews.setInt(R.id.widget_textview, "setText", R.string.active);
        } else {
        	updateViews.setInt(R.id.widget_textview, "setText", R.string.not_active);
        }
        
        Intent active = new Intent(context, PokeWidget.class);
        active.setAction(ACTION_UPDATE_SERVICE);
        
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        updateViews.setOnClickPendingIntent(R.id.start_stop_button, actionPendingIntent);
    	
        ComponentName myComponentName = new ComponentName(context, PokeWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myComponentName, updateViews);
       
    }
}