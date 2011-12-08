package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.Preferences;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class PokeWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName pokeWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
        pokeWidget = new ComponentName(context, PokeWidget.class );
        
    	CharSequence active = "PokePro is active.";
    	CharSequence notActive = "PokePro is not active.";
    	
    	if (Preferences.IS_SERVICE_ACTIVE) {
    		remoteViews.setTextViewText(R.id.widget_main, active);
    	} else {
    		remoteViews.setTextViewText(R.id.widget_textview, notActive);    		
    	}
    	appWidgetManager.updateAppWidget(pokeWidget, remoteViews );
    }
}
