package com.ys.fuckmoney.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * time
 * Created by nufeng on 11/30/16.
 */

public class TimeUtils {

    public static final long s = 1000;
    public static final long min = s * 60;
    public static final long hour = min * 60;
    public static final long day = hour * 24;

    /**
     * 获取当天 零点的时间戳
     */
    public static long getTodayLong() {
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getOneDayBeforeLong(long time) {
        return time-day;
    }
    public static long getOneDayBeforeLong(){
        return getOneDayBeforeLong(System.currentTimeMillis());
    }
}
