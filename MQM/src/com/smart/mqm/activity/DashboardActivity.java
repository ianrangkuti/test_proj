package com.smart.mqm.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smart.mqm.R;
import com.smart.mqm.service.AlarmReceiver;
import com.smart.mqm.util.Utils;

public class DashboardActivity extends Activity {

	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		startSendLocation();

		ImageView ivLogout = (ImageView) findViewById(R.id.iv_dashboard_logout);
		ivLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.logout(DashboardActivity.this);
			}
		});
		
		LinearLayout llButtonProfile = (LinearLayout) findViewById(R.id.ll_button_profile);
		llButtonProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
			}
		});
		
		LinearLayout llButtonHomePass = (LinearLayout) findViewById(R.id.ll_button_homepass);
		llButtonHomePass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DashboardActivity.this, HomePassActivity.class));
			}
		});
		
		LinearLayout btnStatistic = (LinearLayout) findViewById(R.id.ll_button_statistic);
		btnStatistic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DashboardActivity.this, StatisticActivity.class));
			}
		});
	}
	
	private void startSendLocation(){
		Intent alarmIntent = new Intent(DashboardActivity.this, AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(DashboardActivity.this, 0, alarmIntent, 0);
		
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 8000;
		manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
