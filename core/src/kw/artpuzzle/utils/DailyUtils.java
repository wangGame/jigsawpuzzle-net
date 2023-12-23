package kw.artpuzzle.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 23:11
 */
public class DailyUtils {

    //get current month day;
    public static int currentMonthDay(int year,int month){
        boolean isLeap = isLeap(year);
        //根據月份判斷天數
        int days = 0;
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                days = 31;
                break;
            case 3:
            case 5:
            case 8:
            case 10:
                days = 30;
                break;
            case 1:
                if (isLeap) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
        }
        return days;
    }

    //rui year
    public static boolean isLeap(int year){
        boolean isLeap = false;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            isLeap = true;
        }
        return isLeap;
    }

    public static DateBean currentDateBean() {
        DateBean bean = new DateBean();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        bean.setMonth(calendar.get(Calendar.MONTH));
        bean.setYear(calendar.get(Calendar.YEAR));
        bean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        bean.setWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        return bean;
    }

    public static DateBean subMonth(int year,int month,int type) {
        month = month + type;
        if (month>11){
            month = 0;
            year = year + 1;
        }
        if (month < 0){
            month = 11;
            year = year - 1;
        }
        DateBean bean = new DateBean();
        bean.setYear(year);
        bean.setMonth(month);
        return bean;
    }

    public static void subMonth(DateBean dateBean) {
        dateBean.setMonth(dateBean.getMonth() - 1);
        if (dateBean.getMonth()>11){
            dateBean.setMonth(0);
            dateBean.setYear(dateBean.getYear() + 1);
        }
        if (dateBean.getMonth() < 0){
            dateBean.setMonth(11);
            dateBean.setYear(dateBean.getYear() - 1);
        }
    }
}
