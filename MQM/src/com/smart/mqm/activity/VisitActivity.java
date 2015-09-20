package com.smart.mqm.activity;

import com.smart.mqm.R;
import com.smart.mqm.adapter.PicturesChooserAdapter;
import com.smart.mqm.customdialog.PNDialog;
import com.smart.mqm.customdialog.SkipMultipleChoiceDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class VisitActivity extends Activity{
	
	private static final int REQUEST_PICTURE = 2;
	private static final int MAX_DEFAULT_IMAGES = 3;
	protected int lastImagePosition;
	private PicturesChooserAdapter picturesAdapter ;
	private GridView gvPictures;
	
	private TextView tvId;
	private TextView tvAe;
	private TextView tvAddress;
	private TextView tvCustName;
	private TextView tvPhone;
	private TextView tvMobileNo;
	private TextView tvEmail;
	
	private EditText etCustName;
	private EditText etPhone;
	private EditText etMobileNo;
	private EditText etEmail;
	
	private LinearLayout llButtonSkip;
	private LinearLayout llButtonInterested;
	private LinearLayout llButtonNotInterested;
	
	private boolean isEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit);
		
		ImageView ivEdit = (ImageView)findViewById(R.id.iv_edit);
		
		tvId = (TextView)findViewById(R.id.tv_id);
		tvAe = (TextView)findViewById(R.id.tv_ae);
		tvAddress = (TextView)findViewById(R.id.tv_address);
		tvCustName = (TextView)findViewById(R.id.tv_custname);
		tvPhone = (TextView)findViewById(R.id.tv_phone_no);
		tvMobileNo = (TextView)findViewById(R.id.tv_mobile_no);
		tvEmail = (TextView)findViewById(R.id.tv_email);
		
		etCustName = (EditText)findViewById(R.id.et_custname);
		etPhone = (EditText)findViewById(R.id.et_phone_no);
		etMobileNo = (EditText)findViewById(R.id.et_mobile_no);
		etEmail = (EditText)findViewById(R.id.et_email);
		
		ivEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isEdit){
					isEdit = false;
					enableTextEdit();
				}else {
					isEdit = true;
					enableTextEdit();
				}
			}
		});
		
		llButtonSkip = (LinearLayout) findViewById(R.id.ll_button_skip);
		llButtonSkip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SkipMultipleChoiceDialog dialog = new SkipMultipleChoiceDialog(VisitActivity.this);
				dialog.show();
			}
		});
		
		llButtonInterested = (LinearLayout) findViewById(R.id.ll_button_interested);
		llButtonInterested.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(VisitActivity.this, VisitInterestedActivity.class));
			}
		});
		
		llButtonNotInterested = (LinearLayout) findViewById(R.id.ll_button_not_interested);
		llButtonNotInterested.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				new AlertDialog.Builder(VisitActivity.this)
				.setMessage("Customer not interest?")
				.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						finish();
					}
				}).create().show();
			}
		});
		
		
		picturesAdapter = new PicturesChooserAdapter();
		picturesAdapter.setMaxCount(MAX_DEFAULT_IMAGES);
		
		gvPictures = (GridView) findViewById(R.id.gv_pictures);
		gvPictures.setNumColumns(GridView.AUTO_FIT);
		
		gvPictures.setAdapter(picturesAdapter);
		gvPictures.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        if (event.getAction() == MotionEvent.ACTION_MOVE){
		            return true;
		        }
		        return false;
			}
		});
		gvPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        Intent intent = new Intent(VisitActivity.this, PictureChooserActivity.class);
				if (position < picturesAdapter.getRealCount()) intent.putExtra(PictureChooserActivity.EXTRA_CHANGE, true);
		        lastImagePosition = position;
		        intent.putExtra(PictureChooserActivity.LAST_IMAGE_POSITION, position);
		        startActivityForResult(intent, REQUEST_PICTURE);
			}
		});
	}
	
	private void enableTextEdit(){
		if(isEdit){
			tvCustName.setVisibility(View.GONE);
			tvPhone.setVisibility(View.GONE);
			tvMobileNo.setVisibility(View.GONE);
			tvEmail.setVisibility(View.GONE);
			
			etCustName.setVisibility(View.VISIBLE);
			etPhone.setVisibility(View.VISIBLE);
			etMobileNo.setVisibility(View.VISIBLE);
			etEmail.setVisibility(View.VISIBLE);
		}else {
			tvCustName.setVisibility(View.VISIBLE);
			tvPhone.setVisibility(View.VISIBLE);
			tvMobileNo.setVisibility(View.VISIBLE);
			tvEmail.setVisibility(View.VISIBLE);
			
			etCustName.setVisibility(View.GONE);
			etPhone.setVisibility(View.GONE);
			etMobileNo.setVisibility(View.GONE);
			etEmail.setVisibility(View.GONE);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		
		if (requestCode == REQUEST_PICTURE) {
			lastImagePosition = data.getIntExtra(PictureChooserActivity.LAST_IMAGE_POSITION,0);
            //Bitmap bitmap = ACReferences.getACReferences().cachedImage;
        	if (data.getBooleanExtra(PictureChooserActivity.RESULT_DELETE, false)) {
        		picturesAdapter.removeItem(lastImagePosition);
        	} else {
                String imageId = data.getStringExtra(PictureChooserActivity.RESULT_IMAGE_ID);
                if (imageId != null) {
                	// upload succeeded
	        		if (picturesAdapter.getRealCount() > 0 && data.getBooleanExtra(PictureChooserActivity.EXTRA_CHANGE, false)) {
		           		picturesAdapter.setItem(lastImagePosition, imageId);
		            } else {
		            	picturesAdapter.addItem(imageId);
		            }
	            }
        	}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
