package org.solar.util;


import java.net.InetAddress;
import java.net.NetworkInterface;

public class IDGenerater {
    private static String hexStr = "0123456789ABCDEF";
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 28L;
    private final long datacenterIdBits = 24L;
    private final long maxWorkerId = 268435455L;
    private final long maxDatacenterId = 16777215L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 40L;
    private final long timestampLeftShift = 26L;
    private final long sequenceMask = 4095L;
    private long workerId = this.getMACAddress();
    private long datacenterId = this.getThreadId();
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IDGenerater() {
        if(this.workerId <= 268435455L && this.workerId >= 0L) {
            if(this.datacenterId <= 16777215L && this.datacenterId >= 0L) {
                this.workerId = this.workerId;
                this.datacenterId = this.datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can\'t be greater than %d or less than 0", new Object[]{Long.valueOf(16777215L)}));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can\'t be greater than %d or less than 0", new Object[]{Long.valueOf(268435455L)}));
        }
    }

    public synchronized String nextId() {
        long timestamp = this.timeGen();
        if(timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[]{Long.valueOf(this.lastTimestamp - timestamp)}));
        } else {
            if(this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if(this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return this.getResult(Long.toBinaryString(timestamp - 1288834974657L << 26) + Long.toBinaryString(this.datacenterId << (int)(40L + (24L - (long)Long.toBinaryString(this.datacenterId).length())) | this.workerId << 12 | this.sequence));
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
            ;
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private long getMACAddress() {
        try {
            InetAddress e = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(e).getHardwareAddress();
            long macSum = 0L;

            for(int i = 0; i < mac.length; ++i) {
                macSum += (long)(mac[i] & 255);
            }

            return macSum;
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

    private long getThreadId() {
        return Thread.currentThread().getId();
    }

    private String getResult(String ResultBinary) {
        String str1 = "";
        char[] arr = new char[ResultBinary.length() / 4];

        for(int i = 0; i < ResultBinary.length(); ++i) {
            str1 = str1 + ResultBinary.charAt(i);
            if((i + 1) % 4 == 0) {
                arr[i / 4] = this.bin2HexStr(str1);
                str1 = "";
            }
        }

        return String.valueOf(arr);
    }

    private char bin2HexStr(String str) {
        int sum = Integer.parseInt(str.charAt(0) + "") * 8 + Integer.parseInt(str.charAt(1) + "") * 4 + Integer.parseInt(str.charAt(2) + "") * 2 + Integer.parseInt(str.charAt(3) + "") * 1;
        return hexStr.charAt(sum);
    }

    public static String getNextId( ) {
        return iDGenerater.nextId();
    }
    private static IDGenerater iDGenerater=new IDGenerater();
}
