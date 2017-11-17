package org.solar.util;

import java.io.*;

/**
 * Created by xianchuanwu on 2017/9/22.
 */
public class FileUtil {
    /**
     * 该方法最大支持2G的本地文件
     */
    public static byte[] getBytesFromFile(String filePath) {
        File file = new File(filePath);
        try {
            InputStream io = new FileInputStream(file);
            int len = io.available();
            byte[] bytes = new byte[len];
            io.read(bytes);
            io.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] getBytesFromFileIo(InputStream io) {
        try {
            int len = io.available();
            byte[] bytes = new byte[len];
            io.read(bytes);
            io.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static boolean mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            return true;
        }
        return false;
    }

    public final static boolean writeToFile(byte[] bayes, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bayes);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
