package org.solar.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class Captcha  {
    static char[] ch = "abcdefghjkmnpqrstuvwxyz23456789".toCharArray(); // 随即产生的字符串 不包�?i l(小写L) o（小写O�?1（数�?�?(数字0)


    static Font font=new Font("Ravie", Font.PLAIN, 24);

    public static byte[]  generateImage(String sRand)   throws  IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int width = 83, height = 30;  
        // 建立指定宽�?高和BufferedImage对象  
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
        Graphics g = image.getGraphics(); // 该画笔画在image�? 
        Color c = g.getColor(); // 保存当前画笔的颜色，用完画笔后要回复现场  
        g.fillRect(0, 0, width, height);  
        // 随即字符串的长度
         // 保存随即产生的字符串
        Random random = new Random();  
        for (int i = 0; i < sRand.length(); i++) {
            // 设置字体  
            g.setFont(font);
            // 随即生成0-9的数�?
            // 设置随机颜色  
            g.setColor(new Color(random.nextInt(180), random.nextInt(180), random.nextInt(180)));  
            g.drawString(sRand.charAt(i)+"", 20 * i + 6, 25);
        }  
        //产生随即干扰�? 
        for (int i = 0; i <10; i++) {  
            int x1 = random.nextInt(width);  
            int y1 = random.nextInt(height);  
            g.drawOval(x1, y1, 2, 2);  
        }  
        g.setColor(c); // 将画笔的颜色再设置回�? 
        g.dispose();  

        // 输出图像到页�? 
        ImageIO.write(image, "JPEG", os);
        return os.toByteArray();
  
    }  
  
    public static String  randomString()  {
        int length = ch.length;
        String sRand = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            // 随即生成0-9的数�?
            String rand = new Character(ch[random.nextInt(length)]).toString();
            sRand += rand;
        }
        return sRand;
    }  
  
}  