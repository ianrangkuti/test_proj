package com.smart.mqm.customdialog;

import com.smart.mqm.R;
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

public class SkipMultipleChoiceDialog extends Dialog implements OnClickListener {
	
	Context context;
	
	public SkipMultipleChoiceDialog(Context context){
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		setContentView(R.layout.dialog_skip_multiplechoice);
		
		LinearLayout llButtonCancel = (LinearLayout) findViewById(R.id.ll_button_cancel);
		LinearLayout llButtonKirim = (LinearLayout) findViewById(R.id.ll_button_kirim);
		
		llButtonCancel.setOnClickListener(this);
		llButtonKirim.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ll_button_kirim:
				dismiss();
				((Activity)context).finish();
				break;
			case R.id.ll_button_cancel:
				dismiss();
				((Activity)context).finish();
				break;
			default:
				break;
		}
	}

}
