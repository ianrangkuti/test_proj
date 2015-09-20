package com.smart.mqm.customdialog;

import com.smart.mqm.R;
import com.smart.mqm.activity.HomePassActivity;
import com.smart.mqm.activity.VisitActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PNDialog extends Dialog implements OnClickListener {
	
	Context context;
	String dialogMessage;
	
	public PNDialog(Context context, String dialogMessage){
		super(context);
		this.context = context;
		this.dialogMessage = dialogMessage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		setContentView(R.layout.dialog_pn);
		
		TextView tvDialogMessage = (TextView) findViewById(R.id.tv_dialog_message);
		LinearLayout llButtonYes = (LinearLayout) findViewById(R.id.ll_button_yes);
		LinearLayout llButtonNo = (LinearLayout) findViewById(R.id.ll_button_no);
		
		tvDialogMessage.setText(dialogMessage);
		llButtonYes.setOnClickListener(this);
		llButtonNo.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ll_button_yes:
				context.startActivity(new Intent(context, VisitActivity.class));
				dismiss();
				break;
			case R.id.ll_button_no:
				dismiss();
				break;
			default:
				break;
		}
	}

}
