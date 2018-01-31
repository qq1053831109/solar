package org.solar.util;

import java.io.*;

/**
 * Created by xianchuanwu on 2017/9/22.
 */
public class IoUtil {
    /**
     * 该方法最大支持2G的本地文件
     */
    public static byte[] getBytes(InputStream inputStream) {
        try{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    public static String toString(InputStream inputStream,String charset) {
        try{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(charset);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String toString(InputStream inputStream) {
        return toString(inputStream,"UTF-8");
    }

}
