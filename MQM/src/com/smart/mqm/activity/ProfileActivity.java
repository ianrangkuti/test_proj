package com.smart.mqm.activity;

import com.smart.mqm.R;
import com.smart.mqm.customdialog.ChangePasswordDialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends Activity implements OnClickListener{

	private ImageView ivSmsSelf;
	private ImageView ivSmsTLead;
	private ImageView ivSmsBManager;
	private ImageView ivSmsAManager;
	
	private ImageView ivCallSelf;
	private ImageView ivCallTLead;
	private ImageView ivCallBManager;
	private ImageView ivCallAManager;
	
	private TextView tvNameSelf;
	private TextView tvNameTLead;
	private TextView tvNameBManager;
	private TextView tvNameAManager;
	
	private TextView tvNoSelf;
	private TextView tvNoTLead;
	private TextView tvNoBManager;
	private TextView tvNoAManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		ivSmsSelf = (ImageView) findViewById(R.id.iv_sms_self);
		ivSmsTLead = (ImageView) findViewById(R.id.iv_sms_tlead);
		ivSmsBManager = (ImageView) findViewById(R.id.iv_sms_bmanager);
		ivSmsAManager = (ImageView) findViewById(R.id.iv_sms_amanager);
		
		ivCallSelf = (ImageView) findViewById(R.id.iv_call_self);
		ivCallTLead = (ImageView) findViewById(R.id.iv_call_tlead);
		ivCallBManager = (ImageView) findViewById(R.id.iv_call_bmanager);
		ivCallAManager = (ImageView) findViewById(R.id.iv_call_amanager);
		
		tvNoSelf = (TextView) findViewById(R.id.tv_self_no);
		tvNoTLead = (TextView) findViewById(R.id.tv_tlead_no);
		tvNoBManager = (TextView) findViewById(R.id.tv_bmanager_no);
		tvNoAManager = (TextView) findViewById(R.id.tv_amanager_no);
		
		tvNameSelf = (TextView) findViewById(R.id.tv_name_self);
		tvNameTLead = (TextView) findViewById(R.id.tv_name_tlead);
		tvNameBManager = (TextView) findViewById(R.id.tv_name_bmanager);
		tvNameAManager = (TextView) findViewById(R.id.tv_name_amanager);
		
		ivSmsSelf.setOnClickListener(this);
		ivSmsTLead.setOnClickListener(this);
		ivSmsBManager.setOnClickListener(this);
		ivSmsAManager.setOnClickListener(this);
		
		ivCallSelf.setOnClickListener(this);
		ivCallTLead.setOnClickListener(this);
		ivCallBManager.setOnClickListener(this);
		ivCallAManager.setOnClickListener(this);
		
		LinearLayout llChangePass = (LinearLayout) findViewById(R.id.ll_button_change_pass);
		llChangePass.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_sms_self:
				Intent smsSelfIntent = new Intent(Intent.ACTION_VIEW);
				smsSelfIntent.setType("vnd.android-dir/mms-sms");
				smsSelfIntent.putExtra("address", tvNoSelf.getText().toString());
				startActivity(smsSelfIntent);
				break;
			case R.id.iv_sms_tlead:
				Intent smsTLeadIntent = new Intent(Intent.ACTION_VIEW);
				smsTLeadIntent.setType("vnd.android-dir/mms-sms");
				smsTLeadIntent.putExtra("address", tvNoTLead.getText().toString());
				startActivity(smsTLeadIntent);
				break;
			case R.id.iv_sms_bmanager:
				Intent smsBManagerIntent = new Intent(Intent.ACTION_VIEW);
				smsBManagerIntent.setType("vnd.android-dir/mms-sms");
				smsBManagerIntent.putExtra("address", tvNoBManager.getText().toString());
				startActivity(smsBManagerIntent);
				break;
			case R.id.iv_sms_amanager:
				Intent smsAManagerIntent = new Intent(Intent.ACTION_VIEW);
				smsAManagerIntent.setType("vnd.android-dir/mms-sms");
				smsAManagerIntent.putExtra("address", tvNoAManager.getText().toString());
				startActivity(smsAManagerIntent);
				break;
				
			case R.id.iv_call_self:
				Intent callSelfIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNoSelf.getText().toString()));
                startActivity(callSelfIntent);
				break;
			case R.id.iv_call_tlead:
				Intent callTLeadIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNoTLead.getText().toString()));
                startActivity(callTLeadIntent);
				break;
			case R.id.iv_call_bmanager:
				Intent callBManagerIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNoBManager.getText().toString()));
                startActivity(callBManagerIntent);
				break;
			case R.id.iv_call_amanager:
				Intent callAManagerIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNoAManager.getText().toString()));
                startActivity(callAManagerIntent);
				break;
				
			case R.id.ll_button_change_pass:
				ChangePasswordDialog changeDialog = new ChangePasswordDialog(this);
				changeDialog.show();
			default:
				break;
		}
	}
}
