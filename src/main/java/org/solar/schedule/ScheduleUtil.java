package org.solar.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleUtil {
    public static void main(String[] args) throws ParseException {
        Runnable runnable = new Runnable() {
            public void run() {
                // task to run goes here
                System.out.println("Hello !!");
                System.out.println(Thread.currentThread().getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    int a = 1 / 0;
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                System.out.println("a=");
            }
        };
        String time = "12:18";
        everydayTask(runnable, time);
        everydayTask(runnable, time);
        everydayTask(runnable, time);
        everydayTask(runnable, time);
        everydayTask(runnable, time);

    }

    //ScheduleAtFixedRate 是基于固定时间间隔进行任务调度，ScheduleWithFixedDelay 取决于每次任务执行的时间长短
    public static ScheduledFuture everydayTask(Runnable task, String HH_mm) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(now);
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        Date runTaskTime = sdf.parse(str + " " + HH_mm);
        long initialDelay = runTaskTime.getTime() - now.getTime();
        if (initialDelay < 0) {
            initialDelay = initialDelay + 60 * 60 * 24 * 1000;
        }
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        return Executors
                .newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(task, initialDelay, 60 * 60 * 24 * 1000, TimeUnit.MILLISECONDS);
    }
}
