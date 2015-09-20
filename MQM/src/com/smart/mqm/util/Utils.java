package com.smart.mqm.util;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.smart.mqm.activity.DashboardActivity;
import com.smart.mqm.activity.LoginActivity;
import com.smart.mqm.customdialog.CustomProgressDialog;
import com.smart.mqm.service.AlarmReceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.TypedValue;

public class Utils{

	private static JSONObject jObj = null;
//    private static String jsonString = "";
    private static AsyncHttpClient client;
    private static CustomProgressDialog progress;
    
	public static float dipSize(Context context, float size){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.getResources().getDisplayMetrics());
	}
	
	public static float spSize(Context context, float size){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
	}
	
	public static void checkSession(final Context context, String username, String password){
		try {
			String uri = Constant.URL_BASE+"login_api/sessionCheck";
			JSONObject jsonParams = new JSONObject();
			jsonParams.put("username", username);
			jsonParams.put("password", password);
			new Utils().getJSONObjApi(context, uri, jsonParams, true, new OnJSONResponseCallback() {
				@Override
				public void onJSONResponse(boolean success, JSONObject response) {
					// TODO Auto-generated method stub
					try {
						if(success){
							String res = response.getString("success").trim();
							if(res.equals("1")){
								((Activity)context).startActivity(new Intent(context, DashboardActivity.class));
								((Activity)context).finish();
							}
						}
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendLocation(final Context context, String idStaff, double latitude, double longitude){
		try {
			String uri = Constant.URL_BASE+"location_tracking/sendLocation";
			JSONObject jsonParams = new JSONObject();
			jsonParams.put("id_staff", idStaff);
			jsonParams.put("latitude", latitude);
			jsonParams.put("longitude", longitude);
			new Utils().getJSONObjApi(context, uri, jsonParams, false, new OnJSONResponseCallback() {
				@Override
				public void onJSONResponse(boolean success, JSONObject response) {
					
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logout(final Context context){
		
		new AlertDialog.Builder(context)
		.setMessage("Sure to logout?")
		.setPositiveButton("YES", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent alarmIntent = new Intent(context, AlarmReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

				AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				manager.cancel(pendingIntent);
				context.getSharedPreferences(Preferences.SESSION, Context.MODE_PRIVATE)
				.edit()
				.clear()
				.commit();
				context.startActivity(new Intent(context, LoginActivity.class));
				((Activity)context).finish();
				dialog.dismiss();
			}
		}).setNegativeButton("NO", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).setCancelable(false).create().show();
		
	}
	
	public JSONObject getJSONObjApi(Context context, String url, JSONObject jsonParam, final boolean showProgress, final OnJSONResponseCallback callback) throws Exception{
		StringEntity entity = new StringEntity(jsonParam.toString());
		if(showProgress){
			progress = new CustomProgressDialog(context);
			progress.setCancelable(false);
		}
		client = new AsyncHttpClient();
		client.post(context, url, entity, "application/json",new TextHttpResponseHandler() {
			@Override
		     public void onStart() {
		         // Initiated the request
				if(showProgress)
					progress.show();
		     }
			
			@Override
			public void onSuccess(int i, Header[] headers, String response) {
				if(showProgress)
					progress.dismiss();
				try {
					jObj = new JSONObject(response);
			        callback.onJSONResponse(true, jObj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String response, Throwable e) {
				if(showProgress)
					progress.dismiss();
				try {
					jObj = new JSONObject();
					jObj.put("message", e.toString());
			        callback.onJSONResponse(false, jObj);
				}catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
		    }
		});
	    return jObj;
	}
	
	public interface OnJSONResponseCallback {
	    public void onJSONResponse(boolean success, JSONObject response);
	}
}
