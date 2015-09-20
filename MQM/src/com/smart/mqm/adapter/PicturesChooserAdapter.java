package com.smart.mqm.adapter;

import com.smart.mqm.R;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class PicturesChooserAdapter  extends BaseAdapter{
	private static final int DEFAULT_MAX_COUNT = 3;
	private List<String> imageIds = new ArrayList<String>(DEFAULT_MAX_COUNT);
	private int maxCount = DEFAULT_MAX_COUNT;
	
	@Override
	public int getCount() {
		return maxCount;
	}

	@Override
	public Object getItem(int position) {
		return imageIds.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		View view;
		if (position <= getRealCount()) {
			view = LayoutInflater.from(context).inflate(R.layout.adapter_picture_chooser, null);
			if (position < getRealCount()) {
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				final ImageView ivThumb = (ImageView) view.findViewById(R.id.iv_thumb);
				Bitmap bmImg = BitmapFactory.decodeFile(imageIds.get(position), options);
//				String url = PictureChooserActivity.getThumbUrl(imageIds.get(position));
				ivThumb.setImageBitmap(bmImg);
			} 
		} else {
			view = LayoutInflater.from(context).inflate(R.layout.activity_picture_empty, null);
		}
		return view;
	}

	
	@Override
	public boolean isEnabled(int position) {
		//return true;
		return (position <= getRealCount());
	}
	
	public int getRealCount() {
		return imageIds.size();
	}
	
	public void addItem(String imageId) {
		imageIds.add(imageId);
		notifyDataSetChanged();
	}
	
	public void setItem(int position, String imageId) {
		imageIds.set(position, imageId);
		notifyDataSetChanged();
	}
	
	public void removeItem(int position) {
		if (position < getRealCount()) {
			imageIds.remove(position);
		}
		notifyDataSetChanged();
	}
	
	public int getMaxCount() {
		return maxCount;
	}
	
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
		while (getRealCount() > getMaxCount()) {
			removeItem(getRealCount()-1);
		}
		notifyDataSetChanged();
	}
}