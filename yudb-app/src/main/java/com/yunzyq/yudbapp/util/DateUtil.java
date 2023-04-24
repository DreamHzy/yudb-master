package com.yunzyq.yudbapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String DATE_FORMAT_01 = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMAT_02 = "yyyyMMddHHmmss";

    public static String DATE_FORMAT_03 = "yyyy-MM-dd";

    public static String DATE_FORMAT_05 = "yyyy年MM月dd日";

    public static String DATE_FORMAT_04 = "HH:mm:ss";


    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getTimesHalfMonthmorning(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();

    }

    /**
     * 判断时间是否在多少天内
     *
     * @param addtime
     * @return
     */
    public static boolean getIsTime(Date addtime, int day) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, day);  //设置为
        Date before7days = calendar.getTime();   //得到后面的时间
        if (before7days.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取指定日期未来第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);
        return calendar.getTime();
    }


    /**
     * 根据传入的字符串得到相应的日期
     * taskTime:1天后 1月后 1年后
     *
     * @param taskTime
     * @return
     */
    public static Date endTime(String taskTime, Date paramTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        if (taskTime.contains("天后")) {
            String[] str = taskTime.split("天");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.DATE, index);
        }
        if (taskTime.contains("月后")) {
            String[] str = taskTime.split("月");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.MONTH, index);
        }
        if (taskTime.contains("年后")) {
            String[] str = taskTime.split("年");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.YEAR, index);
        }
        Date resultDate = calendar.getTime();
        return resultDate;
    }

    public static long getDaySub(Date beginDateStr, Date endDateStr) {
        long day = (endDateStr.getTime() - beginDateStr.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

}




