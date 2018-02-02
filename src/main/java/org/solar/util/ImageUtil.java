package org.solar.util;

import org.solar.exception.SolarRuntimeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

public class ImageUtil {
    public static BufferedImage changeSize(BufferedImage input, int width, int height) {
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) out.getGraphics();
        g.drawImage(input, 0, 0, width, height, null); //画图
        g.dispose();
        out.flush();
        return out;
    }

    public static BufferedImage multiplyImage(BufferedImage backgroundImage,
                                              BufferedImage topImage){
        Graphics2D g2 = backgroundImage.createGraphics();
        int matrixWidth = backgroundImage.getWidth();
        int matrixHeigh = backgroundImage.getHeight();
         //开始绘制图片
        g2.drawImage(topImage, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形
        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形
        g2.dispose();
        backgroundImage.flush();

        return backgroundImage;
    }


    public static BufferedImage multiplyImage(BufferedImage backgroundImage,
                                              BufferedImage topImage, int x, int y)  {
        return multiplyImage(backgroundImage,topImage,x,y,topImage.getWidth(),topImage.getHeight(),null,1f);
    }

    public static BufferedImage multiplyImage(BufferedImage backgroundImage,
                                              BufferedImage topImage, int x, int y, int width, int height){
        return multiplyImage(backgroundImage,topImage,x,y,width,height,null,1f);
    }

    public static BufferedImage multiplyImage(BufferedImage backgroundImage, BufferedImage topImage,
                                              int x, int y, int width, int height, Integer degree, float alpha) {
        // 得到画笔对象
        Graphics2D g = backgroundImage.createGraphics();
        // 设置对线段的锯齿状边缘处理
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(backgroundImage.getScaledInstance(backgroundImage.getWidth(null), backgroundImage
                .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
        if (null != degree) {
            // 设置水印旋转
            g.rotate(Math.toRadians(degree), x + (width / 2), y + (height / 2));
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        // 表示水印图片的位置
        g.drawImage(topImage, x, y, width, height, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g.dispose();
        backgroundImage.flush();
        return backgroundImage;
    }

    public static BufferedImage read(File o) {
        try {
            return ImageIO.read(o);
        } catch (IOException e) {
            throw new SolarRuntimeException(e);
        }
    }
    public static BufferedImage read(InputStream o) {
        try {
            return ImageIO.read(o);
        } catch (IOException e) {
            throw new SolarRuntimeException(e);
        }
    }
    public static boolean write(RenderedImage img,String formatName,OutputStream os) {
        try {
            return ImageIO.write(img,formatName,os);
        } catch (IOException e) {
            throw new SolarRuntimeException(e);
        }
    }
    public static boolean write(RenderedImage img,OutputStream os) {
        try {
            return ImageIO.write(img,"jpg",os);
        } catch (IOException e) {
            throw new SolarRuntimeException(e);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedImage img =  read(new File("/Users/xianchuanwu/Desktop/8.jpg"));
        BufferedImage logo =  read(new File("/Users/xianchuanwu/Desktop/WechatIMG50.jpeg"));
        BufferedImage out = multiplyImage(img, logo, 1000, 1000, 300, 300, 45, 0.3f);
         write(out,   new FileOutputStream("/Users/xianchuanwu/Desktop/out.jpg"));
    }
}
