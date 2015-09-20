package com.smart.mqm.customdialog;

import com.smart.mqm.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
public class CustomProgressDialog extends Dialog {
	
	Context context;
	
	public CustomProgressDialog(Context context){
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		setContentView(R.layout.dialog_custom_progress);
		
	}
}
