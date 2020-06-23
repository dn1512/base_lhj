package com.tj.lhj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DateUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final String DATE_TIME_CHN_PATTERN = "yyyy年M月dd日 ah:mm:ss";
    public static Map<Integer, String> DATE_MAP = new HashMap();

    public DateUtils() {
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } else {
            return null;
        }
    }

    public static String long2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static int getDayInt(String time, String format) {
        try {
            return getDayInt((new SimpleDateFormat(format)).parse(time));
        } catch (ParseException var3) {
            throw new IllegalArgumentException("日期格式错误:" + time);
        }
    }

    public static int getDayInt(Date date) {
        Period period = Period.between(LocalDate.now(), date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return period.getYears() == 0 && period.getMonths() == 0 ? period.getDays() : 1000;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static String getDayString(Date date, String format) {
        int between = getDayInt(date);
        if (DATE_MAP.containsKey(between)) {
            return (String) DATE_MAP.get(between);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    public static Calendar getCalendar(Date date, String format) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld;
    }

    public static int calcIsThisWeek(Date date) {
        Date currentDate = new Date();
        Date current = getFirstDayOfWeek(currentDate);
        Date calc = getFirstDayOfWeek(date);
        return current.before(calc) ? -1 : 0;
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        int first = calendar.getFirstDayOfWeek();
        calendar.set(7, first);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getNeedTime(Date date, int hour, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, hour);
        calendar.set(12, minute);
        calendar.set(13, second);
        calendar.set(14, millisecond);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getDate(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(time);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Date getLastMonthDate(Date date, String format) {
        Date temp = getDate(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        cal.add(2, -1);
        return cal.getTime();
    }

    public static Date getDate(Date date, String format) {
        String temp = format(date, format);
        return getDate(temp, format);
    }

    public static boolean isBeforeMonth(Date d1, Date d2, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(d1).equals(fmt.format(d2));
    }

    public static Integer date2Integer(Date date) {
        return (new Long(date.getTime())).intValue();
    }

    public static Date long2Date(long time, String format) {
        new SimpleDateFormat(format);
        new Date(time);
        return new Date(time);
    }

    public static boolean isHoliday(Date date, List<String> holidays) {
        boolean result = false;
        String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
        if (holidays.size() > 0) {
            Iterator var4 = holidays.iterator();

            while (var4.hasNext()) {
                String holiday = (String) var4.next();
                if (holiday.equals(dateStr)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public static boolean isHolidayOrFestival(Date date, List<String> holidays) {
        boolean result = false;
        boolean isHolidayTmp = isHoliday(date, holidays);
        if (isHolidayTmp) {
            result = true;
        } else {
            result = isHolidayOrFestival(date);
        }

        return result;
    }

    public static boolean isHolidayOrFestival2(Date date, List<String> holidays) {
        boolean result = false;
        boolean isHolidayTmp = isHoliday(date, holidays);
        if (isHolidayTmp) {
            result = true;
        } else {
            result = isHolidayOrFestival2(date);
        }

        return result;
    }

    public static boolean isHolidayOrFestival(Date date) {
        boolean result = false;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(7) != 1 && c.get(7) != 7) {
            int hour = c.get(11);
            int minute = c.get(12);
            if (hour < 9 || hour == 17 && minute > 30 || hour >= 18) {
                result = true;
            }
        } else {
            result = true;
        }

        return result;
    }

    public static boolean isHolidayOrFestival2(Date date) {
        boolean result = false;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(7) == 1 || c.get(7) == 7;
    }

    public static boolean isHolidayOrFestival(Date date, List<String> workTimes, List<String> restTimes) {
        String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Iterator var5 = workTimes.iterator();

        String restTime;
        do {
            if (!var5.hasNext()) {
                var5 = restTimes.iterator();

                do {
                    if (!var5.hasNext()) {
                        if (c.get(7) != 7 && c.get(7) != 1) {
                            return false;
                        }

                        return true;
                    }

                    restTime = (String) var5.next();
                } while (!restTime.equals(dateStr));

                return true;
            }

            restTime = (String) var5.next();
        } while (!restTime.equals(dateStr));

        return false;
    }

    public static boolean isHolidayOrFestivalForNextDate(Date date, List<String> workTimes, List<String> restTimes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(6, 1);
        return isHolidayOrFestival(c.getTime(), workTimes, restTimes);
    }

    public static Date dateMove(Date date, int type, int mode, int size) {
        Calendar cTmp;
        cTmp = Calendar.getInstance();
        cTmp.setTime(date);
        int i;
        label174:
        switch (type) {
            case 0:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(6, 1);
                    } else {
                        cTmp.add(6, -1);
                    }

                    ++i;
                }
            case 1:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(5, 1);
                    } else {
                        cTmp.add(5, -1);
                    }

                    ++i;
                }
            case 2:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(7, 1);
                    } else {
                        cTmp.add(7, -1);
                    }

                    ++i;
                }
            case 3:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(8, 1);
                    } else {
                        cTmp.add(8, -1);
                    }

                    ++i;
                }
            case 4:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(10, 1);
                    } else {
                        cTmp.add(10, -1);
                    }

                    ++i;
                }
            case 5:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(12, 1);
                    } else {
                        cTmp.add(12, -1);
                    }

                    ++i;
                }
            case 6:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(13, 1);
                    } else {
                        cTmp.add(13, -1);
                    }

                    ++i;
                }
            case 7:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(14, 1);
                    } else {
                        cTmp.add(14, -1);
                    }

                    ++i;
                }
            case 8:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(3, 1);
                    } else {
                        cTmp.add(3, -1);
                    }

                    ++i;
                }
            case 9:
                i = 0;

                while (true) {
                    if (i >= size) {
                        break label174;
                    }

                    if (mode == 1) {
                        cTmp.add(4, 1);
                    } else {
                        cTmp.add(4, -1);
                    }

                    ++i;
                }
            case 10:
                for (i = 0; i < size; ++i) {
                    if (mode == 1) {
                        cTmp.add(2, 1);
                    } else {
                        cTmp.add(2, -1);
                    }
                }

                cTmp.set(5, 1);
                cTmp.set(11, 0);
                cTmp.set(12, 0);
                cTmp.set(13, 0);
                cTmp.set(14, 0);
                break;
            case 11:
                for (i = 0; i < size; ++i) {
                    if (mode == 1) {
                        cTmp.add(2, 1);
                    } else {
                        cTmp.add(2, -1);
                    }
                }
        }

        date = cTmp.getTime();
        return date;
    }

    public static Map<String, Object> calculationTime(Date date, int daySize, int duration, List<String> workTimes, List<String> restTimes, int type, int mode) {
        int durationCalculation = 0;
        Calendar cTmp = Calendar.getInstance();
        cTmp.setTime(date);
        cTmp.add(6, daySize);

        Date calculationDate;
        for (calculationDate = cTmp.getTime(); isHolidayOrFestival(calculationDate, workTimes, restTimes); calculationDate = cTmp.getTime()) {
            cTmp.setTime(calculationDate);
            if (type == 1) {
                cTmp.add(6, 1);
            } else {
                cTmp.add(6, -1);
            }
        }

        Date startDate = null;
        Date endDate = null;
        if (mode == 1) {
            cTmp.setTime(calculationDate);
            cTmp.set(11, 23);
            cTmp.set(12, 59);
            cTmp.set(13, 59);
            cTmp.set(14, 0);
            endDate = cTmp.getTime();
            calculationDate = endDate;
        } else {
            startDate = calculationDate;
        }

        while (duration != durationCalculation) {
            cTmp.setTime(calculationDate);
            if (type == 1) {
                cTmp.add(6, 1);
            } else {
                cTmp.add(6, -1);
            }

            calculationDate = cTmp.getTime();
            if (!isHolidayOrFestival(calculationDate, workTimes, restTimes)) {
                ++durationCalculation;
            }
        }

        if (mode == 1) {
            cTmp.setTime(calculationDate);
            cTmp.add(13, 1);

            for (calculationDate = cTmp.getTime(); isHolidayOrFestival(calculationDate, workTimes, restTimes); calculationDate = cTmp.getTime()) {
                cTmp.setTime(calculationDate);
                cTmp.add(6, 1);
            }

            startDate = calculationDate;
        } else {
            cTmp.setTime(calculationDate);
            cTmp.set(11, 23);
            cTmp.set(12, 59);
            cTmp.set(13, 59);
            cTmp.set(14, 0);
            endDate = cTmp.getTime();
        }

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("startDate", startDate);
        resultMap.put("endDate", endDate);
        resultMap.put("duration", duration);
        return resultMap;
    }

    public static String getPreWorkDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (!isHolidayOrFestival(date)) {
            return datechange(date, "yyyy-MM-dd HH:mm:ss");
        } else {
            if (c.get(7) == 1) {
                c.add(5, -2);
            } else if (c.get(7) == 7) {
                c.add(5, -1);
            } else {
                int hour;
                if (c.get(7) == 2) {
                    hour = c.get(11);
                    if (hour < 9) {
                        c.add(5, -3);
                    }
                } else {
                    hour = c.get(11);
                    if (hour < 9) {
                        c.add(5, -1);
                    }
                }
            }

            c.set(11, 17);
            c.set(12, 30);
            c.set(13, 0);
            return datechange(c.getTime(), "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static String datechange(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String demo = sdf.format(date);
        return demo;
    }

    public static int differentDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / 86400000L);
        return days;
    }

    public static String toStr(String format, Date time) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        } else {
            df = new SimpleDateFormat(format);
        }

        try {
            return df.format(time);
        } catch (Exception var4) {
            return null;
        }
    }

    public static Date toDate(String source) {
        String formatString = "yyyy-MM-dd hh:mm:ss";
        if (source != null && !"".equals(source.trim())) {
            source = source.trim();
            if (source.matches("^\\d{4}$")) {
                formatString = "yyyy";
            } else if (source.matches("^\\d{4}-\\d{1,2}$")) {
                formatString = "yyyy-MM";
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                formatString = "yyyy-MM-dd";
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")) {
                formatString = "yyyy-MM-dd hh";
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                formatString = "yyyy-MM-dd hh:mm";
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                formatString = "yyyy-MM-dd hh:mm:ss";
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$")) {
                formatString = "yyyy-MM-dd HH:mm:ss.SSS";
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formatString);
                Date date = sdf.parse(source);
                return date;
            } catch (ParseException var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date getHourStartTime(Date date) {
        Date dt = null;

        try {
            dt = longHourSdf.parse(longHourSdf.format(date));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return dt;
    }

    public static Date getHourEndTime(Date date) {
        Date dt = null;

        try {
            dt = longSdf.parse(longHourSdf.format(date) + ":59:59.999");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return dt;
    }

    public static Date getDayStartTime(Date date) {
        Date dt = null;

        try {
            dt = shortSdf.parse(shortSdf.format(date));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return dt;
    }

    public static Date getDayEndTime(Date date) {
        Date dt = null;

        try {
            dt = longSdf.parse(shortSdf.format(date) + " 23:59:59.999");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return dt;
    }

    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(7);
        return week_of_year - 1;
    }

    public static Date getWeekStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        try {
            int weekday = c.get(7) - 2;
            c.add(5, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return c.getTime();
    }

    public static Date getWeekEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        try {
            int weekday = c.get(7);
            c.add(5, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return c.getTime();
    }

    public static Date getMonthStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;

        try {
            c.set(5, 1);
            dt = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return dt;
    }

    public static Date getMonthFirstTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        try {
            int firstDay = c.getActualMinimum(5);
            c.set(5, firstDay);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return c.getTime();
    }

    public static Date getMonthEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;

        try {
            c.set(5, 1);
            c.add(2, 1);
            c.add(5, -1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return dt;
    }

    public static Date getYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;

        try {
            c.set(2, 0);
            c.set(5, 1);
            dt = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return dt;
    }

    public static Date getYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;

        try {
            c.set(2, 11);
            c.set(5, 31);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return dt;
    }

    public static Date getQuarterStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;

        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(2, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(2, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(2, 6);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(2, 9);
            }

            c.set(5, 1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return dt;
    }

    public static Date getQuarterEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;

        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(2, 2);
                c.set(5, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(2, 5);
                c.set(5, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(2, 8);
                c.set(5, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(2, 11);
                c.set(5, 31);
            }

            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return dt;
    }

    public static Date getHalfYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;

        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(2, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(2, 6);
            }

            c.set(5, 1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return dt;
    }

    public static Date getHalfYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;

        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(2, 5);
                c.set(5, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(2, 11);
                c.set(5, 31);
            }

            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return dt;
    }

    public static int getTenDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(5);
        if (i < 11) {
            return 1;
        } else {
            return i < 21 ? 2 : 3;
        }
    }

    public static Date getTenDayStartTime(Date date) {
        int ten = getTenDay(date);

        try {
            if (ten == 1) {
                return getMonthStartTime(date);
            } else {
                SimpleDateFormat df;
                if (ten == 2) {
                    df = new SimpleDateFormat("yyyy-MM-11");
                    return shortSdf.parse(df.format(date));
                } else {
                    df = new SimpleDateFormat("yyyy-MM-21");
                    return shortSdf.parse(df.format(date));
                }
            }
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date getTenDayEndTime(Date date) {
        int ten = getTenDay(date);

        try {
            SimpleDateFormat df;
            if (ten == 1) {
                df = new SimpleDateFormat("yyyy-MM-10 23:59:59.999");
                return longSdf.parse(df.format(date));
            } else if (ten == 2) {
                df = new SimpleDateFormat("yyyy-MM-20 23:59:59.999");
                return longSdf.parse(df.format(date));
            } else {
                return getMonthEndTime(date);
            }
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static int getYearDayIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(6);
    }

    public static int getYearWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(3);
    }

    public static int getYearMonthIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getYeartQuarterIndex(Date date) {
        int month = getYearMonthIndex(date);
        if (month <= 3) {
            return 1;
        } else if (month <= 6) {
            return 2;
        } else {
            return month <= 9 ? 3 : 4;
        }
    }

    public static List<Date[]> yearDayList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);

        while (calendar.getTime().before(endtm)) {
            Date st = getDayStartTime(calendar.getTime());
            Date et = getDayEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(6, 1);
        }

        return result;
    }

    public static List<Date[]> yearWeekList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        calendar.setFirstDayOfWeek(2);

        while (calendar.getTime().before(endtm)) {
            Date st = getWeekStartTime(calendar.getTime());
            Date et = getWeekEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(3, 1);
        }

        return result;
    }

    public static List<Date[]> yearMonthList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);

        while (calendar.getTime().before(endtm)) {
            Date tm = calendar.getTime();
            Date st = getMonthStartTime(tm);
            Date et = getMonthEndTime(tm);
            result.add(new Date[]{st, et});
            calendar.add(2, 1);
        }

        return result;
    }

    public static List<Date[]> yearQuarterList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);

        while (calendar.getTime().before(endtm)) {
            Date st = getQuarterStartTime(calendar.getTime());
            Date et = getQuarterEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(2, 3);
        }

        return result;
    }

    public static List<Date[]> monthTenDayList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getMonthStartTime(date);
        Date endtm = getMonthEndTime(date);
        calendar.setTime(starttm);

        while (calendar.getTime().before(endtm)) {
            Date st = getTenDayStartTime(calendar.getTime());
            Date et = getTenDayEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(5, 11);
        }

        return result;
    }

    public static List<Date[]> yearTenDayList(Date date) {
        List<Date[]> result = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);

        while (calendar.getTime().before(endtm)) {
            result.addAll(monthTenDayList(calendar.getTime()));
            calendar.add(2, 1);
        }

        return result;
    }

    public static Date getLastDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, -1);
        return c.getTime();
    }

    public static Date getNextDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, 1);
        return c.getTime();
    }

    public static List<Date> getDatesByBE(Date begin, Date end) {
        List<Date> days = new LinkedList();
        days.add(begin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);

        while (end.after(calBegin.getTime())) {
            calBegin.add(5, 1);
            days.add(calBegin.getTime());
        }

        return days;
    }

}
