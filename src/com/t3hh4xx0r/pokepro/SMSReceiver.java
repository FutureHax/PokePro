package com.t3hh4xx0r.pokepro;

import com.t3hh4xx0r.pokepro.Preferences;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver {

	final public static String POKE_MESSAGE = "You have been poked";
	final public static String POKE_SENDER = "32665";
	final public static String POKE_ERROR = "has not received your last poke yet.";
	
	public static final Uri SMS_CONTENT_URI = Uri.parse("content://sms");
	final public static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	
	
	static public BroadcastReceiver SMSbr = new BroadcastReceiver() {
		private boolean isPokeSms = false;
		private boolean isOtherFacebookText = false;
		private boolean isPokeError = false;
		
		public void onReceive(Context context, Intent intent) {	
		
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++)
                        	messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
    	                if (messages[0].getMessageBody().contains(POKE_MESSAGE) && messages[0].getOriginatingAddress().equals(POKE_SENDER)) {
    	                    isPokeSms = true;
                        } else if (messages[0].getOriginatingAddress().equals(POKE_SENDER) && messages[0].getMessageBody().contains(POKE_ERROR)){
                        	isPokeError = true;
                        } else if (messages[0].getOriginatingAddress().startsWith(POKE_SENDER)){
    	                	isOtherFacebookText = true;
    	                } else {
    	                	isPokeSms = false;
    	            		isOtherFacebookText = false;
    	            		isPokeError = false;
    	                }

    	                if (isOtherFacebookText && Preferences.getInstance().getHideTexts()) {
    	                	abortBroadcast();
    	                } else if (isPokeError || isPokeSms) {
    	                	abortBroadcast();
    	                }
	                
	                if (messages.length > -1 && isPokeSms) {
	                    if (Preferences.getInstance().getEnableIntrusiveNotifications()) {
	                        	notifyIntrusive(context);
	                    } else {
	                        	notifyNonIntrusive(context);
	                    }
	                pokeReply(context);
                	isPokeSms = false;
            		isOtherFacebookText = false;
            		isPokeError = false;
	                }
	            }    		
		}
	};
	
	public static void pokeReply(Context context) {
        SmsManager sms = SmsManager.getDefault();
        try {
        	sms.sendTextMessage(POKE_SENDER, null, "P", null, null);
        } catch (Exception e) {
	        Toast.makeText(context, "Poke failed", Toast.LENGTH_SHORT)
	            .show();
        }
	}
	
	public static void notifyIntrusive(Context context) {
		 	String ns = Context.NOTIFICATION_SERVICE;
		 	NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		 	int icon = R.drawable.ic_notification;        // icon from resources
		 	CharSequence tickerText = "Poke Received!";              // ticker-text
		 	long when = System.currentTimeMillis();         // notification time
		 	CharSequence contentTitle = "PokePro";  // expanded message title
		 	CharSequence contentText = "You just got poked fool!";      // expanded message text

		 	Intent notificationIntent = new Intent(context, com.t3hh4xx0r.pokepro.SMSReceiver.class);

		 	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		 	
			Notification notification = new Notification(icon, tickerText, when);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			final int HELLO_ID = 1;
			 
			mNotificationManager.notify(HELLO_ID, notification);
	}

	public static void notifyNonIntrusive(Context context) {
		Toast.makeText(context, R.string.poke_alert, Toast.LENGTH_LONG).show();
	}

}
