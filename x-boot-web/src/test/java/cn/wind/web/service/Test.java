package cn.wind.web.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * <p>Title: Test</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/25
 */
public class Test {

    public static void main(String[] args){
        jisuan();
    }

    public static void jisuan(){
//        LocalDate inputDate = LocalDate.parse(date);
        LocalDate inputDate = LocalDate.of(2018,7,14);
//        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue()- DayOfWeek.MONDAY.getValue()));
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
//        System.out.println(inputDate.with(LAST_OF_WEEK));
        LocalDate last = inputDate.with(LAST_OF_WEEK);
        Period next = Period.between(inputDate,last);
        System.out.println(next.getDays());
    }

}
