package org.solar.pay;


import java.security.MessageDigest;
import java.util.*;


/**
 * Created by xianchuanwu on 27/05/2017.
 */
public class PaySHA1Util {
    public static String getSHA1SignWithSecretKey(Map<String, String> map) {
        String secret_key = "secret_key";
        SortedMap parameters = new TreeMap(map);
        StringBuffer sb = new StringBuffer(secret_key);
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k =(String)entry.getKey();
            String v =(String)entry.getValue();
            if (!"sign".equals(k)) {
                sb.append(k + v );
            }
        }
        sb.append(secret_key);
        System.out.println(sb.toString());
        String sign = MD5Encode(sb.toString(), "UTF-8").toUpperCase();


        return sign;
    }
    private final static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }
    private static String byteToHexString(byte b) {
        int d1 = (b >> 4) & 0x0f;
        int d2 = b & 0x0f;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };


    public static void  main(String[] b) {
        Map map=new HashMap();
        map.put("wuxianchuan","wuxianchuan");
    }
}
