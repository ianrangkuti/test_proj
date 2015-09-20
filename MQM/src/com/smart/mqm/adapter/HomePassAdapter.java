package com.smart.mqm.adapter;

import com.smart.mqm.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HomePassAdapter extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] homepass;
	
	static class ViewHolder {
		public TextView txtHomePass;
	}
	
	public HomePassAdapter(Activity context, String[] homepass) {
	    super(context, R.layout.adapter_homepass, homepass);
	    this.context = context;
	    this.homepass = homepass;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.adapter_homepass, null);
  			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.txtHomePass = (TextView) rowView.findViewById(R.id.tv_homepass);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		String s = homepass[position];
		holder.txtHomePass.setText(s);

		return rowView;
	}
}
