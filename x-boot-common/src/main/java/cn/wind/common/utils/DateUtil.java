package cn.wind.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 20:31
 * @Description:
 */
public class DateUtil {

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String specifiedDay = sdf.format(date);
        System.out.println(getSpecifiedDayBefore(specifiedDay));
        System.out.println(getSpecifiedDayAfter(specifiedDay));
    }

    /**
     * 获取本周的第一天日期
     * @return
     */
    public static LocalDate getFirstDayByNow(){

        LocalDate inputDate = LocalDate.now();
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue()- DayOfWeek.MONDAY.getValue()));
        return inputDate.with(FIRST_OF_WEEK);
    }

    /**
     * 获取本周的最后一天日期
     * @return
     */
    public static LocalDate getLastDayByNow(){

        LocalDate inputDate = LocalDate.now();
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return inputDate.with(LAST_OF_WEEK);
    }

    /**
     * 获取当天与本周最后一天相差几天
     * @return
     */
    public static int getPoorWithLastDay(){
        LocalDate inputDate = LocalDate.now();
        LocalDate lastDate = getLastDayByNow();
        Period next = Period.between(inputDate,lastDate);
        return next.getDays();
    }


    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

//        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
//                .format(c.getTime());
        return c.getTime();
    }
}
