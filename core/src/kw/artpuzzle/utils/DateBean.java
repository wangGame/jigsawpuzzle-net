package kw.artpuzzle.utils;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 23:15
 */
public class DateBean {
    private int year;
    private int month;
    private int day;
    private int weekDay;

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
