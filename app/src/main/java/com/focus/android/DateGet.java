package com.focus.android;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateGet {
    public static String[] getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        String[] dateGroup = new String[7];
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            // 获取星期的显示名称，例如：周一、星期一、Monday等等
            dateGroup[i] = new SimpleDateFormat("MM-dd").format(calendar.getTime());
        }
        return dateGroup;
    }

    public static String getMonth(){
        Calendar cd = Calendar.getInstance();
        int month = cd.get(Calendar.MONTH)+1;
        return  String.valueOf(month)+"\n月";
    }
}
