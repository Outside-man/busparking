package com.dangod.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yxm on 2018/1/26.
 */
public class DateTrans {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String DatetoString(Calendar c){
        return sdf.format(c.getTime());
    }

    public static String DatetoString(Date c){
        return sdf.format(c);
    }
}
