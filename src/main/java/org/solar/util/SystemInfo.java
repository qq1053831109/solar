package org.solar.util;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;

public class SystemInfo {
    private static final Date initTime=new Date();
    /**
     *  直接通过jdk来获取系统相关状态
     * @throws UnknownHostException
     */
    public static void main (String[] args) throws UnknownHostException{
        String str=getSystemInfo();
        System.out.println(str);
    }
    public static String getSystemInfo() throws UnknownHostException {
        StringBuffer sb=new StringBuffer(200);
        Runtime rt=Runtime.getRuntime();
        String result="initTime="+ DateUtil.format(initTime)+"\r\n";
        result+="服务器时间="+DateUtil.format(new Date())+"\r\n";
        result+="cpu核="+rt.availableProcessors()+"\r\n";
        result+="Jvm空闲内存="+BigDecimalUtil.divide(rt.freeMemory()+"",1024*1024+"")+"m\r\n";
        result+="Jvm最大内存量="+BigDecimalUtil.divide(rt.maxMemory()+"",1024*1024+"")+"m\r\n";
        result+="Jvm已用内存量="+BigDecimalUtil.divide(rt.totalMemory()+"",1024*1024+"")+"m\r\n";

        sb.append(result);
        sb.append("\r\n");
        OperatingSystemMXBean osm = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        sb.append("FreeSwapSpaceSize="+osm.getFreeSwapSpaceSize() / 1024/1024+"m\r\n");
        sb.append("FreePhysicalMemorySize="+osm.getFreePhysicalMemorySize() / 1024/1024+"m\r\n");
        sb.append("TotalPhysicalMemorySize="+osm.getTotalPhysicalMemorySize() / 1024/1024+"m\r\n");
        sb.append("\r\n");
        //获取操纵系统相关信息
        sb.append("操作系统="+osm.getName() +"\r\n");
        sb.append("操作系统版本="+osm.getVersion() +"\r\n");
        sb.append("进程cpu负载="+osm.getProcessCpuLoad() +"\r\n");
        sb.append("系统cpu负载="+osm.getSystemCpuLoad() +"\r\n");
        sb.append("cpu架构="+osm.getArch() +"\r\n");
        sb.append("\r\n");
        //获取整个虚拟机内存使用情况
//        System.out.println("=======================MemoryMXBean============================ ");
//        MemoryMXBean mm = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
//        System.out.println("getHeapMemoryUsage " + mm.getHeapMemoryUsage());
//        System.out.println("getNonHeapMemoryUsage " + mm.getNonHeapMemoryUsage());

        //获取各个线程的各种状态，CPU 占用情况，以及整个系统中的线程状况
//        System.out.println("=======================ThreadMXBean============================ ");
        ThreadMXBean tm = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        sb.append("jvm线程数="+tm.getThreadCount() +"\r\n");
        sb.append("PeakThreadCount="+tm.getPeakThreadCount() +"\r\n");
        sb.append("CurrentThreadCpuTime="+tm.getCurrentThreadCpuTime() +"\r\n");
        sb.append("DaemonThreadCount="+tm.getDaemonThreadCount() +"\r\n");
        sb.append("CurrentThreadUserTime="+tm.getCurrentThreadUserTime() +"\r\n");


        //获取GC的次数以及花费时间之类的信息

//        System.out.println("=======================gcInfo============================ ");
        sb.append("\r\n");
        sb.append("===========================gcInfo============================ \r\n");
        sb.append("\r\n");
        List<GarbageCollectorMXBean> gcmList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcm : gcmList) {
            sb.append("GcName="+gcm.getName() +"\r\n");
            for (String str:gcm.getMemoryPoolNames()){
                sb.append("MemoryPoolNames="+str +"\r\n");
            }
            sb.append("CollectionCount="+gcm.getCollectionCount() +"\r\n");
            sb.append("CollectionTime="+gcm.getCollectionTime() +"\r\n");
        }
        return sb.toString();
    }
}
