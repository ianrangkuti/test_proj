package com.smart.mqm.customdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.smart.mqm.R;
public class ChangePasswordDialog extends Dialog implements OnClickListener {
	
	Context context;
	
	public ChangePasswordDialog(Context context){
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		setContentView(R.layout.dialog_change_pass);
		
		LinearLayout llButtonSubmit = (LinearLayout) findViewById(R.id.ll_button_submit);
		
		llButtonSubmit.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ll_button_submit:
				new AlertDialog.Builder(context)
					.setMessage("Change password success")
					.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dismiss();
						}
					}).create().show();
					
				break;
			
			default:
				break;
		}
	}

}