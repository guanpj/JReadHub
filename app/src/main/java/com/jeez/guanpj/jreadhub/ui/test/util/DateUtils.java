package com.jeez.guanpj.jreadhub.ui.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dream on 16/5/28.
 */
public class DateUtils {



    public static String parseMMSS(int timeInMillis) {
        long hour = timeInMillis / (60 * 60 * 1000);
        long minute = (timeInMillis - hour * 60 * 60 * 1000) / (60 * 1000);
        long second = (timeInMillis - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        if (second >= 60) {
            second = second % 60;
            minute += second / 60;
        }
        if (minute >= 60) {
            minute = minute % 60;
            hour += minute / 60;
        }
        String sh = "";
        String sm = "";
        String ss = "";
        if (hour < 10) {
            sh = "0" + String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = "0" + String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (second < 10) {
            ss = "0" + String.valueOf(second);
        } else {
            ss = String.valueOf(second);
        }
        return sm+":"+ss;
    }

    public static String parseDate(String createTime){
        try {
            String ret = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long create = sdf.parse(createTime).getTime();
            Calendar now = Calendar.getInstance();
            long ms  = 1000*(now.get(Calendar.HOUR_OF_DAY)*3600+now.get(Calendar.MINUTE)*60+now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            if(ms_now-create<ms){
                ret = "今天 " + createTime.substring(createTime.indexOf(" ")+1);
            }else if(ms_now-create<(ms+24*3600*1000)){
                ret = "昨天 " + createTime;
            }else if(ms_now-create<(ms+24*3600*1000*2)){
                ret = "前天 " + createTime;
            }else{
                ret = "更早 " + createTime;
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
