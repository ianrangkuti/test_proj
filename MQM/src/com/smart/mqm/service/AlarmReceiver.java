package com.smart.mqm.service;

import com.smart.mqm.util.Preferences;
import com.smart.mqm.util.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private GPSTracker gps;
	private double lat = 0, lang = 0;
	private SharedPreferences sharedpreferences;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		sharedpreferences = context.getSharedPreferences(Preferences.SESSION, Context.MODE_PRIVATE);
		if(sharedpreferences != null){
			if(!"".equals(sharedpreferences.getString(Preferences.SESSION_STAFF_ID, ""))){
				gps = new GPSTracker(context);
				if (gps.canGetLocation()) {
					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();
					lat = latitude;
					lang = longitude;
					Utils.sendLocation(context, sharedpreferences.getString(Preferences.SESSION_STAFF_ID, ""), lat, lang);
				} else {
					gps.showSettingsAlert();
				}
			}
		}
	}
}