package com.smart.mqm.activity;

import com.smart.mqm.R;
import com.smart.mqm.util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class PictureChooserActivity  extends FragmentActivity {
	public static final String RESULT_DELETE = "delete";
	public static final String EXTRA_CHANGE = "change";
	public static final String LAST_IMAGE_POSITION = "last_image_position";
	public static final String RESULT_IMAGE_ID = "image_id";
    public static final String RESULT_BITMAP = "bitmap";
    public static final String EXTRA_TYPE = "type";
    
	private static final int REQUEST_TAKE = 1;
	private static final int REQUEST_BROWSE = 2;
	private static final int LOADER_UPLOAD_BITMAP = 1;
	private Button takePicture;
	private Button browseImage;
	private Button deleteImage;
	private Boolean change = false;
    private int selectedPosition = 0;
    private String type;
    
    private OnClickListener takePictureListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//intent.putExtra(MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // FIXME: Is this necessary? With this, Android camera app returns null Intent onActivityResult()...
			File file = getTempImageFile();
			try {
				file.getParentFile().mkdirs();
				if (file.getParentFile().exists() == false) throw new IOException("mkdirs() failed.");
				file.createNewFile();
				if (file.exists() == false) throw new IOException("createNewFile() failed.");
				Uri imageUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, REQUEST_TAKE);
				
				// Mixpanel: Ignore landing page event tagging when back from taking photo.
//				Mixpanel.tagLandingPage = false;
			} catch (IOException e) {
				Toast.makeText(PictureChooserActivity.this, R.string.err_take_photo, Toast.LENGTH_SHORT).show();
			}
        }
    };

    private OnClickListener browseImageListener = new OnClickListener() {
        public void onClick(View v) {
        	try{
	            Intent intent = new Intent(Intent.ACTION_PICK);
	            intent.setType("image/*");
	            intent.setAction(Intent.ACTION_GET_CONTENT);
	            startActivityForResult(intent, REQUEST_BROWSE);
        	}catch(ActivityNotFoundException noActivity){
        		Toast.makeText(PictureChooserActivity.this, getString(R.string.err_not_supported), Toast.LENGTH_SHORT).show();
        	}
            // Mixpanel: Ignore landing page event tagging when back from browsing image.
//            Mixpanel.tagLandingPage = false;
        }
    };
    
    private OnClickListener deleteImageListener = new OnClickListener() {
        public void onClick(View v) {
            Intent delete = new Intent(PictureChooserActivity.this, VisitActivity.class);
            delete.putExtra(RESULT_DELETE, true);
            delete.putExtra(LAST_IMAGE_POSITION, selectedPosition);
            setResult(RESULT_OK, delete);
            finish();
        }
    };
	private ProgressBar pbLoading;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_picture_chooser);
        
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        pbLoading.setVisibility(View.GONE);
        
        
        takePicture = (Button) findViewById(R.id.btn_picture_chooser_take_picture_button);
        takePicture.setOnClickListener(takePictureListener);
        browseImage = (Button) findViewById(R.id.btn_picture_chooser_get_picture_button);
        browseImage.setOnClickListener(browseImageListener);
        deleteImage = (Button) findViewById(R.id.btn_picture_chooser_delete_picture_button);
        deleteImage.setOnClickListener(deleteImageListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_CHANGE)) {
            deleteImage.setVisibility(Button.VISIBLE);
            selectedPosition = extras.getInt(LAST_IMAGE_POSITION);
            change = true;
        } else {
            deleteImage.setVisibility(Button.GONE);
            change = false;
        }
        
        if (getSupportLoaderManager().getLoader(LOADER_UPLOAD_BITMAP) != null) {
        	getSupportLoaderManager().initLoader(LOADER_UPLOAD_BITMAP, null, newUploadBitmapCallbacks(null));
        }
    }
	
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
        	return;
        }
        if (requestCode != REQUEST_BROWSE && requestCode != REQUEST_TAKE) {
        	return;
        }
        
        Uri selectedImage = null;
        if (data != null) {
        	// REQUEST_BROWSE (content:// or file://)
        	selectedImage = data.getData();
        } else {
        	// REQUEST_TAKE (file://)
			File file = getTempImageFile();
			selectedImage = Uri.fromFile(file);
        }
        getSupportLoaderManager().restartLoader(LOADER_UPLOAD_BITMAP, null, newUploadBitmapCallbacks(selectedImage));
    }

	private File getTempImageFile() {
		return new File(Environment.getExternalStorageDirectory().getPath()+"/Android/data/"+getPackageName()+"/cache/insert_ad.jpg");
	}
    
	private LoaderCallbacks<Intent> newUploadBitmapCallbacks(final Uri selectedImage) {
		takePicture.setVisibility(View.GONE);
		browseImage.setVisibility(View.GONE);
		deleteImage.setVisibility(View.GONE);
		pbLoading.setVisibility(View.VISIBLE);
    	return new LoaderCallbacks<Intent>() {
    		public byte[] InputStreamTOByte(InputStream in) throws IOException{  

    	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
    	        byte[] data = new byte[1024*16];  
    	        int count = -1;  
    	        while((count = in.read(data,0,1024*16)) != -1)  
    	            outStream.write(data, 0, count);  

    	        data = null;  
    	        return outStream.toByteArray();  
    	    }
    	    
    	    public InputStream byteTOInputStream(byte[] in){  

    	        ByteArrayInputStream is = new ByteArrayInputStream(in);  
    	        return is;  
    	    }
			@Override
			public void onLoaderReset(Loader<Intent> loader) {
			}
			@Override
			public void onLoadFinished(Loader<Intent> loader, Intent data) {
				if (data != null) {
					getSupportLoaderManager().destroyLoader(loader.getId());
					setResult(RESULT_OK, data);
					finish();
				} else {
					Toast.makeText(PictureChooserActivity.this, getString(R.string.err_not_supported), Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public Loader<Intent> onCreateLoader(int id, Bundle args) {
				return new AsyncTaskLoader<Intent>(PictureChooserActivity.this) {
					private boolean dataIsReady = false;
					private Intent data;
					@Override
					protected void onStartLoading() {
					  if (dataIsReady) {
						  deliverResult(data);
					  } else {
						  forceLoad();
					  }
					}
					@Override
					public Intent loadInBackground() {
						dataIsReady = false;
						String filePath;
						if (selectedImage != null && "content".equals(selectedImage.getScheme())) {
							filePath = FileUtils.getPath(PictureChooserActivity.this, selectedImage);
						} else if (selectedImage != null && "file".equals(selectedImage.getScheme())) {
							// convert file:// URI to filePath
							filePath = selectedImage.getPath();
						} else {
							data = null;
							dataIsReady = true;
							return data;
						}
						
                        Intent intent = new Intent(PictureChooserActivity.this, VisitActivity.class);
                        intent.putExtra(RESULT_IMAGE_ID, filePath);
                        if (change) {
                            intent.putExtra(EXTRA_CHANGE, true);
                        }
                        
                        data = intent;
                        dataIsReady = true;
                        return intent;
                    }
				};
			}
		};    	
    }

    
    private Bitmap getRotatedImage(Bitmap original, int exif) {
        int degrees = 0;
        Matrix matrix = new Matrix();
        switch (exif) {
            case 1:
                return original;
            case 2:
                return original;
            case 3:
                degrees = 180;
                matrix.postRotate(degrees);
                Bitmap rotated3 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated3;
            case 4:
                degrees = 180;
                matrix.postRotate(degrees);
                Bitmap rotated4 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated4;
            case 5:
                degrees = 90;
                matrix.postRotate(degrees);
                Bitmap rotated5 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated5;
            case 6:
                degrees = 90;
                matrix.postRotate(degrees);
                Bitmap rotated6 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated6;
            case 7:
                degrees = 270;
                matrix.postRotate(degrees);
                Bitmap rotated7 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated7;
            case 8:
                degrees = 270;
                matrix.postRotate(degrees);
                Bitmap rotated8 = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                return rotated8;
        }
        return original;
    }
    
    public static int calculateInSampleSize(int width, int height) {
        int inSampleSize = 1;
        
        if (width >= height) {
        	inSampleSize = Math.round((float) width / (float) 640);
        }else{
        	inSampleSize = Math.round((float) height / (float) 640);
        }
        
        return inSampleSize;
    }
    
   /* private void getExifInteger(String path) {
        // filepath is the path to your image file stored in SD card (which contains exif info)
        IImageMetadata metadata;
        try {
        	Log.d("filepath: "+path);
            metadata = Imaging.getMetadata(new File(path));//Sanselan.getMetadata(new File(path));
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            if (jpegMetadata != null) {
	            TiffField field = jpegMetadata.findEXIFValue(TiffConstants.TIFF_TAG_ORIENTATION);
	            if (field != null) {
	            	ref.insert_ad_exif_info = Integer.parseInt(field.getValueDescription());
	            	return;
	            }
            }
            ref.insert_ad_exif_info = 0;
        } catch (NullPointerException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (ImageReadException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (IOException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (ClassCastException cc){
        	// TO SOLVE
        	// java.lang.ClassCastException: org.apache.commons.imaging.common.ImageMetadata 
        	// cannot be cast to org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
            Log.w(cc);
            ref.insert_ad_exif_info = 0;
        } catch (NegativeArraySizeException e) {
			//throw new RuntimeException("{path:"+path+"}", e);
			Log.w("{path:"+path+"}"+e);
            ref.insert_ad_exif_info = 0;
		}
    }

    private void getExifInteger(byte[] imageBytes) {
        // filepath is the path to your image file stored in SD card (which contains exif info)
        IImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(imageBytes);
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            if (jpegMetadata != null) {
	            TiffField field = jpegMetadata.findEXIFValue(TiffConstants.TIFF_TAG_ORIENTATION);
	            if (field != null) {
	            	ref.insert_ad_exif_info = Integer.parseInt(field.getValueDescription());
	            	Log.d("ref.insert_ad_exif_info: "+ref.insert_ad_exif_info);
	            	return;
	            }
            }
            ref.insert_ad_exif_info = 0;
        } catch (NullPointerException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (ImageReadException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (IOException e) {
            // recoverable
            Log.w(e);
            ref.insert_ad_exif_info = 0;
        } catch (ClassCastException cc){
        	// TO SOLVE
        	// java.lang.ClassCastException: org.apache.commons.imaging.common.ImageMetadata 
        	// cannot be cast to org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
            Log.w(cc);
            ref.insert_ad_exif_info = 0;
        } catch (NegativeArraySizeException e) {
			//throw new RuntimeException("{path:"+path+"}", e);
			Log.w(e);
            ref.insert_ad_exif_info = 0;
		}
    }*/
    
//	public static String getThumbPath(String imageId) {
//		if(!ACUtils.isEmpty(imageId))
//			return Config.THUMB_ROOT+"/"+imageId.substring(0, 2)+"/"+imageId;
//		else
//			return null;
//	}
}
