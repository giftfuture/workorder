package com.xbhy.workorder.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
    private static final Object object = new Object();



    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm",Locale.CHINA);
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final  ZoneId zoneId = ZoneId.systemDefault();
    //Mysql支持的时间戳限制
    static long minTime = Timestamp.valueOf("1970-01-01 09:00:00").getTime();
    static long maxTime = Timestamp.valueOf("2038-01-19 11:00:00").getTime();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dayOfStartFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static final SimpleDateFormat dayOfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYYMMDDHHMMssSSS = "YYYYMMddHHmmssSSS";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM_SLASH = "yyyy/MM/dd HH:mm";

    public static String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }
    public static final String dateTimeNowSSS()
    {
        return dateTimeNow(DateStyle.YYYY_MM_DD_SSS.getValue());
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }



    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     *
     * @return
     */
    public static Date toDate(final String format, final String ts){
        return new Date(Long.valueOf(ts));
    }



    //判断 并转换时间格式 ditNumber = 43607.4166666667
    /**
     * 数字转时间字符串
     * @param ditNumber
     * @return
     */
    public static String  getTimeStr(String ditNumber) {
        //如果不是数字
        if(!isNumeric(ditNumber)){
            return null;
        }
        //如果是数字 小于0则 返回
        BigDecimal bd = new BigDecimal(ditNumber);
        int days = bd.intValue();//天数
        int mills = (int) Math.round(bd.subtract(new BigDecimal(days)).doubleValue() * 24 * 3600);

        //获取时间
        Calendar c = Calendar.getInstance();
        c.set(1900, 0, 1);
        c.add(Calendar.DATE, days - 2);
        int hour = mills / 3600;
        int minute = (mills - hour * 3600) / 60;
        int second = mills - hour * 3600 - minute * 60;
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        Date d = c.getTime();//Date
        Timestamp t = Timestamp.valueOf(dateFormat.format(c.getTime()));//Timestamp
        try {
            //时间戳区间判断
            if(minTime <= d.getTime() && d.getTime() <= maxTime){
                return dateFormat.format(c.getTime());
            }else{
                return ditNumber;
            }
        } catch (Exception e) {
            System.out.println("传入日期错误" + c.getTime());
        }
        return "Error";
    }

    //校验是否数据含小数点
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]+\\.*[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
    public static Date now() {
        return new Date();
    }


    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }


    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return new Date(todayStart.getTime().getTime());
    }

    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 0);
        return new Date(todayEnd.getTime().getTime());
    }

    public static Date getMonthStartTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    public static Date getWeekStartTime() {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new Date(cal.getTimeInMillis());
    }


    public static Date getMonthStartTime(int month) {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH)  - month;
        c.set(Calendar.MONTH, currentMonth);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }


    public static Date getMonthEndTime() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        return new Date(ca.getTimeInMillis());
    }

    public static Date getLastMonthStartTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    public static Date getLastMonthEndTime() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 0);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        return new Date(ca.getTimeInMillis());
    }

    public static int getLastMonthDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.get(Calendar.DAY_OF_MONTH);
    }


    private static SimpleDateFormat getDateFormat(String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }


    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }


    public static boolean isOutTime(Date date, int hourAmount) {
        date = DateUtils.addHour(date, hourAmount);
        if (DateUtils.getIntervalTime(date, DateUtils.now()) != null
                && DateUtils.getIntervalTime(date, DateUtils.now()) < 0) {
            return true;
        }
        return false;
    }

    public static boolean isOutTimeByMinute(Date date, int minuteAmount) {
        date = DateUtils.addMinute(date, minuteAmount);
        if (DateUtils.getIntervalTime(date, DateUtils.now()) != null
                && DateUtils.getIntervalTime(date, DateUtils.now()) < 0) {
            return true;
        }
        return false;
    }



    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }


    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i),
                                timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
                                : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }


    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    public static Date StringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }

    public static Date StringToDateNew(String date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        return calendar.getTime();
    }

    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
                myDate = null;
            }
        }
        return myDate;
    }

    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }


    public static String StringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }


    public static String StringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }

    public static String StringToString(String date, String olddPattern,
                                        String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }


    public static String StringToString(String date, DateStyle olddDteStyle,
                                        String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(),
                    newParttern);
        }
        return dateString;
    }


    public static String StringToString(String date, String olddPattern,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern,
                    newDateStyle.getValue());
        }
        return dateString;
    }


    public static String StringToString(String date, DateStyle olddDteStyle,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(),
                    newDateStyle.getValue());
        }
        return dateString;
    }


    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }


    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    public static void main(String[] args) {
        System.out.println(timeDayInterval("2016-06-01 12:11:02", "2016-06-02 15:11:03"));//1.13
        System.out.println(timeHourInterval("2016-06-01 12:11:02", "2016-06-02 15:11:03"));//27.0
        System.out.println(addDate("2016-06-01", 5));//2016-06-06
        System.out.println(addDate("2016-06-01", -5));//2016-05-27
        System.out.println(getDate(new Date(), -5));//2016-05-27
    }


    public static double timeDayInterval(String fromdatetime, String todatetime) {
        long sec = 0;
        double hour = 0.00;
        double day = 0.00;
        try {
            Calendar fromcalendar = getCalendar(fromdatetime);
            Calendar tocalendar = getCalendar(todatetime);
            if (fromcalendar == null || tocalendar == null) return 0;
            sec = (tocalendar.getTime().getTime() - fromcalendar.getTime().getTime()) / 1000;
            hour = round(sec > 0 ? (sec / 60.0 / 60.0) : 0, 2);
            day = round(hour / 24, 2);
        } catch (Exception e) {
        }
        return day;
    }

    public static double timeHourInterval(String fromdatetime, String todatetime) {
        long sec = 0;
        double hour = 0.00;
        try {
            Calendar fromcalendar = getCalendar(fromdatetime);
            Calendar tocalendar = getCalendar(todatetime);
            if (fromcalendar == null || tocalendar == null) return 0;
            sec = (tocalendar.getTime().getTime() - fromcalendar.getTime().getTime()) / 1000;
            hour = round(sec > 0 ? (sec / 60.0 / 60.0) : 0, 2);
        } catch (Exception e) {
        }
        return hour;
    }

    public static double round(double dou, int scale) {
        return new BigDecimal(dou).divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Calendar getCalendar(String datetime) {
        Date datet = parseToDateTime(datetime);
        if (datet == null) return null;
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(datet);
        return cal;
    }

    public static Date parseToDateTime(String sDate) {
        switch (sDate.length()) {
            case 10:
                return parseToDate(sDate, FORMAT_DATE);
            case 19:
                return parseToDate(sDate, FORMAT_FULL);
            default:
                return parseToDate(sDate, FORMAT);
        }
    }

    public static String addDate(String date, int param) {
        Calendar cal = addDay(getCalendar(date), param);
        return getDate(cal.getTime());
    }

    public static Calendar addDay(Calendar cal, int param) {
        cal.add(Calendar.DATE, param);
        return cal;
    }


    public static Date getDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }


    public static Date parseToDate(String sDate, String pattern) {
        Date date = null;
        if (pattern == null || pattern.equals("")) {
            pattern = FORMAT_DATE;
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            date = sf.parse(sDate);
        } catch (Exception e) {
        }
        return date;
    }


    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }


    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }


    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }


    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }


    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }


    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }


    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }


    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }


    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    public static String getTimeString(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtils.StringToDate(DateUtils.getDate(date),
                DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtils.StringToDate(DateUtils.getDate(otherDate),
                DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    public static Long getIntervalTime(Date date, Date otherDate) {
        if (date != null && otherDate != null) {
            long dateTmp = date.getTime();
            long otherDateTmp = otherDate.getTime();
            long time = dateTmp - otherDateTmp;
            return time;
        }
        return null;
    }

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis))
                / MILLIS_IN_DAY;
    }

    public static boolean specifiedInTheDate(Date date){
        return DateUtils.getYear(date) == DateUtils.getYear(DateUtils.now()) & DateUtils.getMonth(date) ==  DateUtils.getMonth(DateUtils.now());
    }

    public static  Date lastMothOfSomeday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        return new Date(calendar.getTimeInMillis());
    }

    public static  Date lastMothOfSomeday(Date date,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -month);
        return new Date(calendar.getTimeInMillis());
    }

    public static  Date lastDayOfSomeday(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.DATE, -day);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getStartTimePfSomeday(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return new Date(todayStart.getTimeInMillis());
    }

    public static Date getEndTimePfSomeday(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY,23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 999);
        return new Date(todayStart.getTime().getTime());
    }


    /************************** Timestamp 处理 @authro 吴进 by 20181206 ****************************************/
    /**
     * 将String字符串转换为java.util.Date格式日期
     *
     * @param strDate
     *            表示日期的字符串
     * @param dateFormat
     *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
     * @return java.util.Date类型日期对象（如果转换失败则返回null）
     */
    public static Date strToUtilDate(String strDate, String dateFormat) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = sf.parse(strDate);
        return date;
    }

    /**
     * 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
     *
     * @param strDate
     *            表示日期的字符串
     * @param dateFormat
     *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
     * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
     */
    public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = sf.parse(strDate);
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        return dateSQL;
    }

    /**
     * 将java.util.Date对象转化为String字符串
     *
     * @param date
     *            要格式的java.util.Date对象
     * @param strFormat
     *            输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }

    /**
     * 将java.sql.Timestamp对象转化为String字符串
     *
     * @param time
     *            要格式的java.sql.Timestamp对象
     * @param strFormat
     *            输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
    }

    /**
     * 将java.sql.Timestamp对象转化为毫秒数String字符串
     * @param time
     * @return
     */
    public static String dateToStrTimeMillis(java.sql.Timestamp time) {
        long currTimeMillis = time.getTime();
        return String.valueOf(currTimeMillis);
    }

    /**
     * 将java.sql.Timestamp对象转化为java.util.Date对象
     *
     * @param time
     *            要转化的java.sql.Timestamp对象
     * @return 转化后的java.util.Date对象
     */
    public static Date timeToDate(java.sql.Timestamp time) {
        return time;
    }

    /**
     * 将java.util.Date对象转化为java.sql.Timestamp对象
     *
     * @param date
     *            要转化的java.util.Date对象
     * @return 转化后的java.sql.Timestamp对象
     */
    public static java.sql.Timestamp dateToTime(Date date) throws ParseException {
        String strDate = dateToStr(date, "yyyy-MM-dd HH:mm:ss SSS");
        return strToSqlDate(strDate, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * 返回表示系统当前时间的java.util.Date对象
     * @return  返回表示系统当前时间的java.util.Date对象
     */
    public static Date nowDate(){
        return new Date();
    }

    /**
     * 返回表示系统当前时间的java.sql.Timestamp对象
     * @return  返回表示系统当前时间的java.sql.Timestamp对象
     */
    public static java.sql.Timestamp nowTime() throws ParseException {
        return dateToTime(new Date());
    }

    /************************** Timestamp 处理 End ****************************************/
}
