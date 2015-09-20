package com.smart.mqm.util;

import java.io.ByteArrayInputStream;

import android.graphics.Bitmap;

public class ACReferences {
    public static final int RESULT_OK = 1;
    public static final int RESULT_ERROR = 0;
    private static ACReferences acReferences;
    
    public String home_pass_id = null;
    public String ae_id = null;
    public String address = null;
    
	public String customer_name = null;
    public String phone_no = null;
    public String mobile_no = null;
    public String email = null;
    public String remarks = null;
    
    public Bitmap cachedImage;
	public int insert_ad_exif_info;
	
    public static ByteArrayInputStream cachedImageInputStream;
    
    private ACReferences() {
    }
    
    public static ACReferences getACReferences() {
        if (acReferences == null) {
        	clearACReferences();
        }
        return acReferences;
    }
    
    public static void clearACReferences() {
        acReferences = new ACReferences();
    }
}