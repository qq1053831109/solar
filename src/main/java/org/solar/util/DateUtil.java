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
 *  <p>Title: BaseBean.java</p> 
 *  <p>Description:</p> 
 *  <p>Copyright: Copyright (c) 2013</p> 
 *  @author txj 
 *  @date 2013-7-25 上午09:19:55
 *  @version V1.0
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

//    /**
//     *  <p>给指定的日期添加指定的天数</p>
//     *  @author txj  
//     *  @date 2013-8-1 上午10:43:52 
//     *
//     * @param date   日期
//     * @param addDay 添加的天数
//     * @return 返回添加天数后的日期
//     */
//    public static Date addDay(Date date, int addDay) {
//        Assert.notNull(date, "日期不能为空");
//        Assert.notNull(addDay, "添加天数不能为空");
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.DAY_OF_YEAR, addDay); // 小时加1
//        return cal.getTime();
//    }

//    /**
//     *  <p>字符串日期加上指定天数</p>
//     *  @author txj  
//     *  @date 2013-9-4 下午1:23:49 
//     *
//     * @param dateStr   日期字符串
//     * @param addDay    添加的天数
//     * @param formatter 格式化
//     * @return
//     */
//    public static String addDay(String dateStr, int addDay, String formatter) {
//        Assert.notNull(dateStr, "日期不能为空");
//        Assert.notNull(addDay, "添加天数不能为空");
//        Date date = stringToDate(dateStr, formatter);
//        Date d = addDay(date, addDay);
//        return format(d, formatter);
//    }


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


//    /**
//     *  <p>将字符串转换为时间戳类型,如果转换出错则会返回一个null</p>
//     *  @author txj  
//     *  @date 2013-8-1 上午10:46:10 
//     *
//     * @param dateStr   时间字符串
//     * @param formatStr 格式化类型：如"yyyy-MM-dd"
//     * @return 返回转换后的日期
//     */
//    public static Long stringToTimesamp(String dateStr, String formatStr) {
//        Date date = DateUtil.stringToDate(dateStr, formatStr);
//        return date.getTime();
//    }

//    /**
//     *  <p>将字符串转换为时间戳类型,如果转换出错则会返回一个null</p>
//     *  @author txj  
//     *  @date 2013-8-1 上午10:46:10 
//     *
//     * @param timesamp  时间戳
//     * @param formatStr 格式化类型：如"yyyy-MM-dd"
//     * @return 返回转换后的日期
//     */
//    public static String timesampToStr(Long timesamp, String formatStr) {
//        Date date = new Date(timesamp);
//        return DateUtil.format(date, formatStr);
//    }

    public static void main(String args[]) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        try {
            Date date = sdf.parse("08/Jul/2016:17:56:09 +0800");
            System.out.println(date);
            System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            //
            e.printStackTrace();
        }

        //Date date = DateUtil.addDay(new Date(), 10);

        //System.out.println(DateUtil.stringToTimesamp("2014-10-10", "yyyy-MM-dd"));
        //	System.out.println(DateUtil.timesampToStr(1417575465000L, "yyyy-MM-dd HH:mm:ss"));
        //	System.out.println(DateUtil.stringToTimesamp("2079-01-01", "yyyy-MM-dd"));
    }
}
