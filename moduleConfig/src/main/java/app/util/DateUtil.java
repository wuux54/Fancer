package app.util;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作工具类
 */

public class DateUtil {
    private static final String COLON = ":";
    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FORMAT_ONE_DATE = "yyyy-MM-dd";
    public static final String FORMAT_ONE_TIME = "HH:mm";
    public static final String FORMAT_LOG_APP_START = "yyyyMMddHHmmss.SSS";
    public static final String FORMAT_MONTH_AND_DAY = "MM.dd";


    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;

    /**
     * 秒数转换
     *
     * @param duration
     * @return (00 : 00 : 00)
     */
    public static String secondTurnTime(int duration) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (duration <= 0) {
            return "00:00";
        } else {
            minute = duration / 60;
            if (minute < 60) {
                second = duration % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = duration - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * 两个日期相减
     *
     * @param firstTime
     * @param secTime
     * @return 相减得到的秒数
     */
    public static int timeSub(String firstTime, String secTime) {
        try {
            long first = stringToDate(firstTime, FORMAT_ONE).getTime();
            long second = stringToDate(secTime, FORMAT_ONE).getTime();
            return Integer.parseInt(String.valueOf(((second - first) / 1000)));
        } catch (Exception e) {
            return 0;
        }

    }

    public static int timeSubDay(String firstTime, String secTime) {
        try {
            long first = stringToDate(firstTime, DateUtil.FORMAT_MONTH_AND_DAY).getTime();
            long second = stringToDate(secTime, DateUtil.FORMAT_MONTH_AND_DAY).getTime();
            return Integer.parseInt(String.valueOf(((second - first) / 1000)));
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    public static Date esgStringToDate(String dateStr) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
            formater = new SimpleDateFormat(FORMAT_T);
            try {
                d = formater.parse(dateStr);
            } catch (Exception e2) {
                d = null;
            }
        }
        return d;
    }

    public static Date calculateBeginDate(Date date) {
        if (date == null) {
            return null;
        }

        String datestr;

        datestr = dateToString(date, FORMAT_ONE);
        if (datestr == null) {
            return null;
        }

        return stringToDate(datestr, DateUtil.FORMAT_ONE_DATE);
    }

    public static long calculateTodayTimeOffset(Date date, Date todayBeginDate) {
        if (todayBeginDate == null) {
            todayBeginDate = calculateBeginDate(date);
            if (todayBeginDate == null) {
                return 0;
            }
        }

        return date.getTime() - todayBeginDate.getTime();
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 获取当前时间的指定格式
     *
     * @return
     */
    public static String getCurrDateString() {
        return dateToString(new Date(), FORMAT_ONE);
    }


    /**
     * 获取13位长度当前时间 到毫秒
     *
     * @return
     */
    public static String getCurrDate() {
        return String.valueOf(System.currentTimeMillis());
    }


    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }

    public static Date dateByAddingDays(Date date, int delta) {
        Calendar cal;

        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, delta);
        return cal.getTime();
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }



    /**
     * String 转换 Date
     *
     * @param str
     * @return
     */
    public static Date string2Date(String str) {
        try {
            return new SimpleDateFormat(FORMAT_ONE).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String dateFormatterDate(String time, String original, String destination) {
        SimpleDateFormat sdf = new SimpleDateFormat(original, Locale.getDefault());
        String format = null;
        if (!TextUtils.isEmpty(time) && !TextUtils.isEmpty(original) && !TextUtils.isEmpty(destination)) {
            try {
                Date date = sdf.parse(time);
                sdf = new SimpleDateFormat(destination, Locale.getDefault());
                format = sdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 格式化日期
     * 00:00-00:00
     *
     * @return
     */
    public static String dateFormatter(String startTime, int duration) {
        //        Log.d("Category2Activity", "convert:startTime= "+startTime+",duration"+duration );
        String startTimeStr = DateUtil.dateFormatterDate(startTime, DateUtil.FORMAT_ONE, DateUtil.FORMAT_ONE_TIME);
        if (startTimeStr == null) {
            //盒子返回时间2017-03-06T17:15:10.500629
            startTimeStr = DateUtil.dateFormatterDate(startTime, DateUtil.FORMAT_T, DateUtil.FORMAT_ONE_TIME);
        }
        Date startDate = DateUtil.stringToDate(startTime, DateUtil.FORMAT_ONE);
        if (startDate == null) {
            //盒子返回时间2017-03-06T17:15:10.500629
            startDate = DateUtil.stringToDate(DateUtil.dateFormatterDate(startTime, DateUtil.FORMAT_T, DateUtil.FORMAT_ONE), DateUtil.FORMAT_ONE);
        }
        String endTimeStr = "";
        if (startDate != null) {
            long endTime = startDate.getTime() + duration * 1000;
            endTimeStr = DateUtil.dateToString(new Date(endTime), DateUtil.FORMAT_ONE_TIME);
        }
        //    viewHolder.setText(R.id.tv_program_time, startTimeStr + "-" + endTimeStr);
        return startTimeStr + "-" + endTimeStr;

    }


    public static String dateFormatterLong(long lSysTime1) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONE);
        Date dt = new Date(lSysTime1);
        return sdf.format(dt);
    }

    /**
     * ntp是从1900.1.1 00:00:00开始的，unix是1970.1.1 00:00:00
     * 中间相差70年+闰年多的17天
     */
    private static final long OFFSET_1900_TO_1970 = ((365L * 70L) + 17L) * 24L * 60L * 60L;

    /**
     * ntp格式时间戳转换为时间
     *
     * @param ntpTime ntp时间戳，单位为秒
     * @return
     */
    public static String dateFormatNtpLong(long ntpTime) {
        long unixTime = ntpTime - OFFSET_1900_TO_1970;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONE);
        Date dt = new Date(unixTime * 1000);
        return sdf.format(dt);
    }

    public static String dateFormatNtpLong2(long ntpTime) {
        long unixTime = ntpTime - OFFSET_1900_TO_1970;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONE_DATE);
        Date dt = new Date(unixTime * 1000);
        return sdf.format(dt);
    }


    public static int getCurrTimeFormatInt() {
        long currentTimeMillis = System.currentTimeMillis();
        return (int) (currentTimeMillis / 1000);
    }


    /**
     * 毫秒和秒单位转换
     *
     * @param duration           数据
     * @param second2Millisecond true：秒->毫秒 false：毫秒->秒
     * @return 转换后数据
     */
    public static int switchSecond2Millisecond(int duration, boolean second2Millisecond) {
        if (second2Millisecond) {
            duration = duration * 1000;
        } else {
            duration = duration / 1000;
        }
        return duration;
    }




    /**
     * 得到剩余天数
     *
     * @param endTime 结束时间
     * @return
     */
    public static int getDayLast(String endTime) {
        try {
            long nowTime = System.currentTimeMillis();
            long lastTime = new SimpleDateFormat("yyyy-MM-dd").parse(endTime).getTime();

            long distance = lastTime - nowTime;
            if (distance <= 0) {
                //如果是小于或等于0，则为0
                return 0;
            }

            double rate = distance / (1.0f * 24 * 3600 * 1000);
            int day = (int) (rate + 0.5f);
            return day;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取短时间格式
     *
     * @return
     */
    public static String getShortTime(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();

        String str = "";
        long durTime = curDate.getTime() - date.getTime();

        int dayStatus = calculateDayStatus(date, new Date());

        if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dayStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dayStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", date);
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM-dd", date).toString();
        } else {
            str = DateFormat.format("yyyy-MM", date).toString();
        }
        return str;
    }

    /**
     * 判断是否是同一年
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static boolean isSameYear(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }

    /**
     * 判断是否处于今天还是昨天，0表示今天，-1表示昨天，小于-1则是昨天以前
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static int calculateDayStatus(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }

    /**
     * 将秒数转换成00:00的字符串，如 118秒 -> 01:58
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }


    /**
     * 获取当前月1号  返回格式yyyy-MM-dd (eg: 2007-06-01)
     *
     * @return
     */
    public static String getMonthBegin() {
        String yearMonth = new SimpleDateFormat(
                "yyyy-MM").format(new Date());
        return yearMonth + "-01";
    }


    /**
     * 与当前时间对比
     * @param time
     * @return
     */
    public static long compareTime(String time) {
        long timeLong = 0;
        long curTimeLong = 0;

        try {
           SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            timeLong = sdf.parse(time).getTime();
            curTimeLong = sdf.parse(getCurrentTimeString())
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return curTimeLong - timeLong;// 当前时间减去传入的时间
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss类型的时间字符串
     */
    public static String getCurrentTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//
                .format(new Date());
    }


    public static String getCurrentTimeString(String pattern) {
        return new SimpleDateFormat(pattern)
                .format(new Date());
    }

    /**
     * @param milliseconds 时间值
     * @param isDetail 是否需要显示具体时间段 + 时分和星期 + 时分
     * @return
     */
    public static String getTimeShowString(long milliseconds,boolean isDetail) {
        String dataString = "";
        String timeStringBy24 = "";

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(
                yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        if (isDetail) {//显示具体的时间
            //在聊天界面显示时间，如果是今天则显示当前时间段加上时和分  如上午 9:58
            if (!currentTime.before(todaybegin)) {//如果是今天
                return getTodayTimeBucket(currentTime);//根据时间段分为凌晨 上午 下午等
            } else {
                return dataString + " " + timeStringBy24;//如果是昨天 则是 昨天 9：58 如果是同在一个星期，前天之前的时间则显示 星期一 9：58
            }
        }else{
            //在会话记录界面不需要展示很具体的时间
            if (!currentTime.before(todaybegin)) {//如果是今天
                return timeStringBy24;//直接返回时和分 如 9:58
            }else{
                return dataString;//如果不是今天，不需要展示时和分 如 昨天 前天 星期一
            }
        }
    }


    /**
     * 判断两个日期是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六" };
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm",
                Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm",
                Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * 获取当前时间 yyyy-MM-dd格式
     *
     * @return
     */
    public static String getCurrentTimeYMD() {
        return new SimpleDateFormat("yyyy-MM-dd")//
                .format(new Date());
    }

    /**
     * 将yyyy-MM-dd的字符串转换成Date对象
     */
    public static Date getDateByYMD(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
