package com.rjtech.register.utils;

import java.text.NumberFormat;
import java.text.DecimalFormat;

public class RegisterCommonUtils {

    public static final String IS_LATEST_Y = "Y";
    public static final String IS_LATEST_N = "N";

    public static final String PLANT_PO_ASSIGN_STATUS = "Y";
    public static final String PLANT_PO_ASSIGN_STATUS_NO = "N";

    public static final String MATERIAL_PROJ_DOKCET_REQ_DRAFT = "D";
    public static final String MATERIAL_PROJ_DOKCET_REQ_SUBMIT = "S";

    public static final String NEW = "N";
    public static final String PARTIAL = "P";
    public static final String COMPLETE = "C";
    
    public static final double SPACE_KB = 1024;
    public static final double SPACE_MB = 1024 * SPACE_KB;
    public static final double SPACE_GB = 1024 * SPACE_MB;
    public static final double SPACE_TB = 1024 * SPACE_GB;

    public static String bytes2String(long sizeInBytes) {

    	NumberFormat nf = new DecimalFormat();
    	nf.setMaximumFractionDigits(2);

    	try {
		    if ( sizeInBytes < SPACE_KB ) {
		      return nf.format(sizeInBytes) + " Byte(s)";
		    } else if ( sizeInBytes < SPACE_MB ) {
		      return nf.format(sizeInBytes/SPACE_KB) + " KB";
		    } else if ( sizeInBytes < SPACE_GB ) {
		      return nf.format(sizeInBytes/SPACE_MB) + " MB";
		    } else if ( sizeInBytes < SPACE_TB ) {
		      return nf.format(sizeInBytes/SPACE_GB) + " GB";
		    } else {
		      return nf.format(sizeInBytes/SPACE_TB) + " TB";
		    }          
		  } catch (Exception e) {
		    return sizeInBytes + " Byte(s)";
		 }
    }

}
