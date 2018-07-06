package cn.wind.common.datetime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 金米袋
 * Created by xukk on 2018/3/14.
 */

public class LocalDateUtil {
    //默认使用系统当前时区
    private static final ZoneId ZONE = ZoneId.systemDefault();

    private static final String REGEX = "\\:|\\-|\\s";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateTimeFormat.FORMAT_DATE_TIME);

    public static void main(String[] args) {
        System.out.println(timeByFormat(new Date()));
    }
    /**
     * 根据传入的时间格式返回系统当前的时间
     *
     * @param format string
     * @return
     */
    public static String timeByFormat(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }
    public static String timeByFormat(LocalDateTime localDateTime,String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }
    public static String timeByFormat(Date date,String format) {
        return timeByFormat(dateToLocalDateTime(date),format);
    }
    public static String timeByFormat(Date date) {
        return timeByFormat(dateToLocalDateTime(date),DateTimeFormat.FORMAT_DATE_TIME);
    }
    /**
     * 将Date转换成LocalDateTime
     *
     * @param d date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    /**
     * 将Date转换成LocalDate
     *
     * @param d date
     * @return
     */
    public static LocalDate dateToLocalDate(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    /**
     * 将Date转换成LocalTime
     *
     * @param d date
     * @return
     */
    public static LocalTime dateToLocalTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * 将LocalDate转换成Date
     *
     * @param localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDateTime转换成Date
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将相应格式yyyy-MM-dd yyyy-MM-dd HH:mm:ss 时间字符串转换成Date
     *
     * @param time string
     * @param format string
     * @return date
     */
    public static Date stringToDate(String time , String format) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
        if (DateTimeFormat.FORMAT_DATE_TIME.equals(format)) {
            return LocalDateUtil.localDateTimeToDate(LocalDateTime.parse(time, f));
        } else if (DateTimeFormat.FORMAT_DATE.equals(format)){
            return LocalDateUtil.localDateToDate(LocalDate.parse(time, f));
        }
        return null;
    }
    /**
     * time 类型等于Date返回String time 类型等于String返回对应格式化日期类型
     * time 等于String 暂时支持format为yyyy-MM-dd. yyyy-MM-dd HH:mm:ss. yyyyMMddHHmmss
     * time 等于Date 不限制格式化类型，返回String
     *
     * @param time string or date
     * @param format string
     * @param <T> T
     * @return localDateTime or localDate or string
     */
    public static <T> T timeFormat(T time,String format){
        DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
        //暂时支持三种格式转换
        if (ClassIdentical.isCompatible(String.class,time)){
            if (DateTimeFormat.FORMAT_DATE_TIME.equals(format)) {
                LocalDateTime localDateTime = LocalDateTime.parse(time.toString(), f);
                return (T) localDateTime.atZone(ZONE).toInstant();
            }
            if (DateTimeFormat.FORMAT_DATE.equals(format)) {
                LocalDate localDate = LocalDate.parse(time.toString(), f);
                return (T) localDate;
            }
            if (DateTimeFormat.FORMAT_NOFULL_DATE_TIME.equals(format)) {
                String rp =  time.toString().replaceAll(REGEX,"");
                LocalDateTime localDate = LocalDateTime.parse(time.toString(), f);
                return (T) localDate;
            }
        }
        if (ClassIdentical.isCompatible(Date.class,time)){
            Date date = (Date) time;
            Instant instant = date.toInstant();
            instant.atZone(ZONE).format(f);
            return (T) instant.atZone(ZONE).format(f);
        }
        return null;
    }
    public static <T> T timeFormat(T time){
        DateTimeFormatter f = DateTimeFormatter.ofPattern(DateTimeFormat.FORMAT_DATE_TIME);
        //暂时支持三种格式转换
        if (ClassIdentical.isCompatible(String.class,time)){
            LocalDateTime localDateTime = LocalDateTime.parse(time.toString(), f);
            return (T) localDateTime.atZone(ZONE).toInstant();
        }
        if (ClassIdentical.isCompatible(Date.class,time)){
            Date date = (Date) time;
            Instant instant = date.toInstant();
            instant.atZone(ZONE).format(f);
            return (T) instant.atZone(ZONE).format(f);
        }
        return null;
    }
    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 date
     * @return
     */
    public static long chronoUnitBetweenByDate(ChronoUnit cu, Date d1, Date d2) {
        return cu.between(LocalDateUtil.dateToLocalDateTime(d1), LocalDateUtil.dateToLocalDateTime(d2));
    }

    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param s1 string
     * @param s2 string
     * @return
     */
    public static Long chronoUnitBetweenByString(ChronoUnit cu, String s1, String s2, String dateFormat) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(dateFormat);
        if (DateTimeFormat.FORMAT_DATE_TIME.equals(dateFormat)) {
            LocalDateTime l1 = LocalDateUtil.dateToLocalDateTime(LocalDateUtil.stringToDate(s1,dateFormat));
            LocalDateTime l2 = LocalDateUtil.dateToLocalDateTime(LocalDateUtil.stringToDate(s2,dateFormat));
            return cu.between(l1,l2);
        }
        if (DateTimeFormat.FORMAT_DATE.equals(dateFormat)) {
            LocalDate l1 = LocalDateUtil.dateToLocalDate(LocalDateUtil.stringToDate(s1,dateFormat));
            LocalDate l2 = LocalDateUtil.dateToLocalDate(LocalDateUtil.stringToDate(s2,dateFormat));
            return cu.between(l1,l2);
        }
        if (DateTimeFormat.FORMAT_NOFULL_DATE_TIME.equals(dateFormat)) {
            LocalDateTime l1 = LocalDateTime.parse(s1.replaceAll(REGEX,""), f);
            LocalDateTime l2 = LocalDateTime.parse(s2.replaceAll(REGEX,""), f);
            return cu.between(l1,l2);
        }
        return null;
    }

    /**
     * 根据ChronoUnit枚举计算两个时间相加，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 long
     * @return
     */
    public static Date chronoUnitPlusByDate(ChronoUnit cu, Date d1, long d2) {
        return LocalDateUtil.localDateTimeToDate(cu.addTo(LocalDateUtil.dateToLocalDateTime(d1),d2));
    }

    /**
     * 将time时间转换成毫秒时间戳
     *
     * @param time string
     * @return
     */
    public static long stringDateToMilli (String time) {
        return LocalDateUtil.stringToDate(time,DateTimeFormat.FORMAT_DATE_TIME).toInstant().toEpochMilli();
    }

    /**
     * 将毫秒时间戳转换成Date
     *
     * @param time string
     * @return
     */
    public static Date timeMilliToDate (String time) {
        return Date.from(Instant.ofEpochMilli(Long.parseLong(time)));
    }
    //获取一天的开始时间，2017,7,22 00:00
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    //获取一天的结束时间，2017,7,22 23:59:59.999999999
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    //日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }
}
