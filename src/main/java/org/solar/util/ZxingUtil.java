package org.solar.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class ZxingUtil {
    public static BufferedImage generateCodeImg(String contents,int width,int height) throws Exception {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);//设置二维码边的空度，非负数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height,
                hints);//生成条形码时的一些配置,此项可选
        // 生成二维码
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF));
            }
        }
         return image;
    }
}
