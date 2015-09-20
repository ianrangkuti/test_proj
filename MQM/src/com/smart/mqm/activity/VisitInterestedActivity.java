package com.smart.mqm.activity;

import com.smart.mqm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class VisitInterestedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_interested);
		
		LinearLayout llSubmit = (LinearLayout) findViewById(R.id.ll_button_submit_interested);
		llSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(VisitInterestedActivity.this)
				.setMessage("Success")
				.setNeutralButton("OK", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						finish();
					}
				}).create().show();
			}
		});
	}
}