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

        try {
            synchronized (sdf) {
                return sdf.parse(dateStr);
            }
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



    public static void main(String args[]) {

      System.out.println(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
    }
}
