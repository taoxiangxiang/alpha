package com.alpha.constans;

import com.alibaba.citrus.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by taoxiang on 2017/4/9.
 */
public class CalendarUtil {

    private static final Logger logger = LoggerFactory.getLogger(CalendarUtil.class);
    static GregorianCalendar cldr = new GregorianCalendar();
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN_SHORT = "dd/MM/yy HH:mm:ss";
    public static final String TIME_PATTERN_SHORT_1 = "yyyy/MM/dd HH:mm";
    public static final String TIME_PATTERN_SESSION = "yyyyMMddHHmmss";
    public static final String DATE_FMT_0 = "yyyyMMdd";
    public static final String DATE_FMT_1 = "yyyy/MM/dd";
    public static final String DATE_FMT_2 = "yyyy/MM/dd hh:mm:ss";
    public static final String DATE_FMT_3 = "yyyy-MM-dd";
    public static final String DATE_FMT_5 = "yyyy";

    static {
        cldr.setTimeZone(TimeZone.getTimeZone("GMT+9:00"));
    }

    public CalendarUtil() {
    }

    public static Date toDate(String sDate, String sFmt) {
        if(!StringUtil.isBlank(sDate) && !StringUtil.isBlank(sFmt)) {
            SimpleDateFormat sdfFrom = null;
            Date dt = null;

            try {
                sdfFrom = new SimpleDateFormat(sFmt);
                dt = sdfFrom.parse(sDate);
                return dt;
            } catch (Exception var8) {
                logger.error("catch exception",var8);
            } finally {
                sdfFrom = null;
            }

            return null;
        } else {
            return null;
        }
    }

    public static String toString(Date dt) {
        return toString(dt, "yyyyMMdd");
    }

    public static String toString(Date dt, String sFmt) {
        if(dt != null && !StringUtil.isBlank(sFmt)) {
            SimpleDateFormat sdfFrom = null;
            String sRet = null;

            try {
                sdfFrom = new SimpleDateFormat(sFmt);
                sRet = sdfFrom.format(dt).toString();
                return sRet;
            } catch (Exception var8) {
                logger.error("catch exception", var8);
            } finally {
                sdfFrom = null;
            }

            return null;
        } else {
            return null;
        }
    }

    public static Date getMonthLastDate(Date date) {
        if(date == null) {
            return null;
        } else {
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            ca.set(11, 23);
            ca.set(12, 59);
            ca.set(13, 59);
            ca.set(5, 1);
            ca.add(2, 1);
            ca.add(5, -1);
            Date lastDate = ca.getTime();
            return lastDate;
        }
    }

    public static String getMonthLastDate(Date date, String pattern) {
        Date lastDate = getMonthLastDate(date);
        if(lastDate == null) {
            return null;
        } else {
            if(StringUtil.isBlank(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            return toString(lastDate, pattern);
        }
    }

    public static Date getMonthFirstDate(Date date) {
        if(date == null) {
            return null;
        } else {
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            ca.set(11, 0);
            ca.set(12, 0);
            ca.set(13, 0);
            ca.set(5, 1);
            Date firstDate = ca.getTime();
            return firstDate;
        }
    }

    public static String getMonthFirstDate(Date date, String pattern) {
        Date firstDate = getMonthFirstDate(date);
        if(firstDate == null) {
            return null;
        } else {
            if(StringUtil.isBlank(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            return toString(firstDate, pattern);
        }
    }

    public static int getIntervalDays(Date firstDate, Date lastDate) {
        if(firstDate != null && lastDate != null) {
            long intervalMilli = lastDate.getTime() - firstDate.getTime();
            return (int)(intervalMilli / 86400000L);
        } else {
            return -1;
        }
    }

    public static int getTimeIntervalHours(Date firstDate, Date lastDate) {
        if(firstDate != null && lastDate != null) {
            long intervalMilli = lastDate.getTime() - firstDate.getTime();
            return (int)(intervalMilli / 3600000L);
        } else {
            return -1;
        }
    }

    public static int getTimeIntervalMins(Date firstDate, Date lastDate) {
        if(firstDate != null && lastDate != null) {
            long intervalMilli = lastDate.getTime() - firstDate.getTime();
            return (int)(intervalMilli / 60000L);
        } else {
            return -1;
        }
    }

    public static String formatDate(Date d, String pattern) {
        if(d != null && !StringUtil.isBlank(pattern)) {
            SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();
            formatter.applyPattern(pattern);
            return formatter.format(d);
        } else {
            return null;
        }
    }

    public static int compareDate(Date src, Date desc) {
        if(src == null && desc == null) {
            return 3;
        } else if(desc == null) {
            return 1;
        } else if(src == null) {
            return 2;
        } else {
            long timeSrc = src.getTime();
            long timeDesc = desc.getTime();
            return timeSrc == timeDesc?3:(timeDesc > timeSrc?2:1);
        }
    }

    public static int compareTwoDate(Date first, Date second) {
        return first == null && second == null?3:(first == null?1:(second == null?2:(first.before(second)?1:(first.after(second)?2:3))));
    }

    public static boolean isDateBetween(Date date, Date begin, Date end) {
        int c1 = compareTwoDate(begin, date);
        int c2 = compareTwoDate(date, end);
        return c1 == 1 && c2 == 1 || c1 == 3 || c2 == 3;
    }


    public static Date addDate(Date date, int day) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(5, calendar.get(5) + day);
            return calendar.getTime();
        }
    }

    public static String addDays(Date date, int day, String pattern) {
        return addDays(toString(date, pattern), day, pattern);
    }

    public static String addDays(Date date, int day) {
        return addDays(toString(date, "yyyy-MM-dd HH:mm:ss"), day);
    }

    public static String addDays(String date, int day) {
        return addDays(date, day, "yyyy-MM-dd HH:mm:ss");
    }

    public static String addDays(String date, int day, String pattern) {
        if(date == null) {
            return "";
        } else if(date.equals("")) {
            return "";
        } else if(day == 0) {
            return date;
        } else {
            try {
                SimpleDateFormat ex = new SimpleDateFormat(pattern);
                Calendar calendar = ex.getCalendar();
                calendar.setTime(ex.parse(date));
                calendar.set(5, calendar.get(5) + day);
                return ex.format(calendar.getTime());
            } catch (Exception var5) {
                return "";
            }
        }
    }

    public static String formatTimestamp(Timestamp t, String sFmt) {
        if(t != null && !StringUtil.isBlank(sFmt)) {
            t.setNanos(0);
            SimpleDateFormat ft = new SimpleDateFormat(sFmt);
            String str = "";

            try {
                str = ft.format(t);
            } catch (NullPointerException var5) {
                logger.error("catch exception", var5);
            }

            return str;
        } else {
            return "";
        }
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    public static Timestamp getCurrentDateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static final int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static final int getYear(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(1);
    }

    public static final int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static final int getMonth(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(2) + 1;
    }

    public static final int getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static final int getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(5);
    }

    public static final int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    public static final int getHour(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(11);
    }

    public static final Date zerolizedTime(Date fullDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }
}
