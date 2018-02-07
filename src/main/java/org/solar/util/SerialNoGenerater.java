package org.solar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 1秒钟最多产生9999个号
 * 单号生成为年月日时分秒+今天生成的第几个数(每超过9999将从1重新开始)
 */
public class SerialNoGenerater {
    private   int sequence = 0;
    private   int day = 0;
    private   boolean dayReset = true;
    private   long lastTimestamp = -1L;
    private   SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");

    public SerialNoGenerater( ) {
    }
    public SerialNoGenerater(boolean dayReset) {
        this.dayReset=dayReset;
    }

    public  synchronized String nextNo() {
        int today=Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if (today!=day&&dayReset){
            day=today;
            sequence = 0;
        }
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id for %d milliseconds");
        }
        if (sequence == 9999) {
            sequence = 0;
            if (timestamp < (lastTimestamp + 1000)) {
                timestamp = tilNextSecond(lastTimestamp);
            }
        }
        sequence++;
        lastTimestamp = timestamp;
        return sdf.format(new Date(timestamp)) + convert4Length(sequence);
    }

    private static long tilNextSecond(long lastTimestamp) {
        long needTime = lastTimestamp + 1000;
        long timestamp;
        for (timestamp = timeGen(); timestamp <= needTime; timestamp = timeGen()) {

        }

        return timestamp;
    }

    protected static long timeGen() {
        return System.currentTimeMillis();
    }




    private static String convert4Length(long num) {
        if (num < 10) {
            return "000" + num;
        }
        if (num < 100) {
            return "00" + num;
        }
        if (num < 1000) {
            return "0" + num;
        }
        return num + "";
    }
    public static String getNextNo( ) {
        return serialNoGenerater.nextNo();
    }
    private static SerialNoGenerater serialNoGenerater=new SerialNoGenerater();
}
