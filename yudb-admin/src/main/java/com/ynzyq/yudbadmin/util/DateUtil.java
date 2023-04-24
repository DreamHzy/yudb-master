package com.ynzyq.yudbadmin.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
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
        if (taskTime.contains("年前")) {
            String[] str = taskTime.split("年");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.YEAR, -index);
        }
        Date resultDate = calendar.getTime();
        return resultDate;
    }
}
