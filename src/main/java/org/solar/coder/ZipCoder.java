package org.solar.coder;

import org.solar.util.FileUtil;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCoder {
    ByteArrayOutputStream bytesOs = new ByteArrayOutputStream();
    //创建zip输出流
    ZipOutputStream out = new ZipOutputStream(bytesOs);

    public void putNextEntry( String path)  {

        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void compress(byte[] bs,String path)  {

        try {
            out.putNextEntry(new ZipEntry(path));
            //将源文件写入到zip文件中
            if (bs!=null){
                out.write(bs);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] toByteArray() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bytesOs.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        ZipCoder zipCoder=new ZipCoder();
        String file="/Users/xianchuanwu/Desktop/haidilao.jpg";
        zipCoder.putNextEntry("testZip/");
        byte[] fbs=FileUtil.getBytesFromFile(file);
        zipCoder.compress(fbs,"testZip/h.jpg");
        zipCoder.compress(fbs,"h1.jpg");
        zipCoder.compress(fbs,"testZip/2/h2.jpg");
        byte[] zbs=zipCoder.toByteArray();
        FileOutputStream fos=new FileOutputStream("/Users/xianchuanwu/Desktop/test.zip");
        fos.write(zbs);
        System.out.println(zbs.length);
    }
}
