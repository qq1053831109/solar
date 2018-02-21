package org.solar.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  
 *  <p>类功能说明: 日期工具类</p>
 */
public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *  <p>给指定的时间和格式返回该时间的字符串</p>
     *  @author txj  
     *  @date 2013-8-1 上午10:47:05 
     *
     * @param date      日期
     * @param formatter 要返回时间字符串的格式
     * @return 返回转换后的日期字符串
     */
    public static String format(Date date, String formatter) {
        Assert.notNull(date, "日期不能为空");
        Assert.notNull(formatter, "格式化参数不能为空");
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(date);
    }

    public static String format(Date date) {
        synchronized (sdf) {
            return sdf.format(date);
        }
    }

    public static Date parse(String dateStr) {
        SimpleDateFormat sdf;
        if (dateStr.length() > 13) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  <p>将字符串转换为时间Date类型,如果转换出错则会返回一个null</p>
     *  @author txj  
     *  @date 2013-8-1 上午10:46:10 
     *
     * @param dateStr   时间字符串
     * @param formatStr 格式化类型：如"yyyy-MM-dd"
     * @return 返回转换后的日期
     */
    public static Date parse(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * add day
     *
     * @return
     */
    public static Date addDay(Date date, int day) {
        long time = 1000 * 60 * 60 * 24;
        time = time * day;
        return new Date(date.getTime() + time);
    }


    public static String getNowTime() {
        return DateUtil.format(new Date(), "yyyyMMddHHmmss");
    }

    public static String getTodayString() {
        return DateUtil.format(new Date(), "yyyy-MM-dd");
    }

    public static String getYesterdayString() {
        long yesterdayTime = System.currentTimeMillis() - (1000 * 60 * 60 * 24);
        return DateUtil.format(new Date(yesterdayTime), "yyyy-MM-dd");
    }

    public static boolean inTimeRange(String beginTime, String endTime) {
        return inTimeRange(beginTime, endTime, new Date());
    }

    public static boolean inTimeRange(String beginTimeStr, String endTimeStr, Date nowTime) {
        if (StringUtil.isEmpty(beginTimeStr) || StringUtil.isEmpty(endTimeStr)) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(nowTime));
            beginTime = df.parse(beginTimeStr);
            endTime = df.parse(endTimeStr);
            if (beginTime.getTime() <= now.getTime() && now.getTime() <= endTime.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.inTimeRange("11:19",
                "23:59"));
    }
}
