package com.smart.mqm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.smart.mqm.R;
import com.smart.mqm.adapter.HomePassAdapter;
import com.smart.mqm.customdialog.PNDialog;

public class HomePassActivity extends Activity{

	String[] values = new String[] { 
			"Serpong Damai Blok AB No 17, BSD", 
			"Serpong Damai Blok AB No 18, BSD", 
			"Serpong Damai Blok AB No 19, BSD", 
			"Serpong Damai Blok AB No 20, BSD", 
			"Serpong Damai Blok AB No 21, BSD", 
			"Serpong Damai Blok AB No 22, BSD", 
			"Serpong Damai Blok AB No 23, BSD",
	        "Serpong Damai Blok AB No 24, BSD", 
	        "Serpong Damai Blok AB No 25, BSD", 
	        "Serpong Damai Blok AB No 26, BSD"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepass);
		
		ListView lvHomepass = (ListView)findViewById(R.id.lv_homepass);
		final HomePassAdapter aHomePass = new HomePassAdapter(this, values);
		lvHomepass.setAdapter(aHomePass);
		lvHomepass.setClickable(true);  
		lvHomepass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				PNDialog dialog = new PNDialog(HomePassActivity.this, getString(R.string.txt_confirm_location));
				dialog.show();
			}
		});
		
		
		EditText edSearchHomePass = (EditText) findViewById(R.id.ed_search_homepass);
		edSearchHomePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
            	aHomePass.getFilter().filter(cs);   
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub

            }
           @Override
           public void afterTextChanged(Editable s) {
               // TODO Auto-generated method stub

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
