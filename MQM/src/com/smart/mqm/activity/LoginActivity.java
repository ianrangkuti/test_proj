package com.smart.mqm.activity;

import com.smart.mqm.R;
import com.smart.mqm.util.Preferences;
import com.smart.mqm.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends Activity {
	private EditText etUserName;
	private EditText etPassword;
	private SharedPreferences sharedpreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedpreferences = getSharedPreferences(Preferences.SESSION, MODE_PRIVATE);
		if(sharedpreferences != null){
			Utils.checkSession(this, sharedpreferences.getString(Preferences.SESSION_USERNAME, ""), sharedpreferences.getString(Preferences.SESSION_PASSWORD, ""));
		}
		setContentView(R.layout.activity_login);
		
		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		
		LinearLayout btnLogin = (LinearLayout)findViewById(R.id.button_login);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//				try {
//					String uri = Constant.URL_BASE+"login_api/loginCheck";
//					JSONObject jsonParams = new JSONObject();
//					jsonParams.put("username", etUserName.getText().toString().trim());
//					jsonParams.put("password", etPassword.getText().toString().trim());
//					new Utils().getJSONObjApi(LoginActivity.this, uri, jsonParams, true, new OnJSONResponseCallback() {
//						@Override
//						public void onJSONResponse(boolean success, JSONObject response) {
//							// TODO Auto-generated method stub
//							try {
//								if(success){
//									String res = response.getString("success").trim();
//									if(res.equals("1")){
//										Editor editor = sharedpreferences.edit();
//										editor.putString(Preferences.SESSION_USERNAME, etUserName.getText().toString().trim());
//										editor.putString(Preferences.SESSION_PASSWORD, etPassword.getText().toString().trim());
//										editor.putString(Preferences.SESSION_STAFF_ID, response.getString("user").trim());
//										editor.commit();
//										startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//										finish();
//									}else {
//										new AlertDialog.Builder(LoginActivity.this)
//										.setMessage(response.getString("message"))
//										.setNeutralButton("OK", new android.content.DialogInterface.OnClickListener() {
//											@Override
//											public void onClick(DialogInterface dialog, int which) {
//												// TODO Auto-generated method stub
//												dialog.dismiss();
//											}
//										}).setCancelable(false).create().show();
//									}
//								}else {
//									new AlertDialog.Builder(LoginActivity.this)
//									.setMessage(response.getString("message"))
//									.setNeutralButton("OK", new android.content.DialogInterface.OnClickListener() {
//										@Override
//										public void onClick(DialogInterface dialog, int which) {
//											// TODO Auto-generated method stub
//											dialog.dismiss();
//										}
//									}).setCancelable(false).create().show();
//								}
//							}catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//								Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//							}
//						}
//					});
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
