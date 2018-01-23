package org.solar.pay;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeChatMd5Util {
	private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

private static String byteArrayToHexString(byte b[]) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++)  
            resultSb.append(byteToHexString(b[i]));  
  
        return resultSb.toString();  
    }  
  
    private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0)  
            n += 256;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
  
    public static String MD5Encode(String origin, String charsetname) {  
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
  
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  
    private static String Key = "";
    
    /** 
     * @param args 
     */  
    //微信api提供的参数  
    private static String appid ="";
    private static  String mch_id = "";
    private static String device_info = WXConfig.getDevice_info();  
    //private static  String body = WXConfig.getBody();  
    private static  String nonce_str =WXConfig.getNonce_str();
    
    public static String getWX_Sign(WechatOrder wechatOrder) {  
        System.out.println(">>>模拟微信支付<<<");    
          
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("appid", appid);  
        parameters.put("mch_id", mch_id);  
        parameters.put("device_info", device_info);  
        parameters.put("body", wechatOrder.getBody());  
        parameters.put("nonce_str", nonce_str);   
        parameters.put("spbill_create_ip", wechatOrder.getSpbill_create_ip());  
        parameters.put("total_fee", wechatOrder.getTotal_fee());  
        parameters.put("notify_url", wechatOrder.getNotify_url());  
        parameters.put("trade_type", wechatOrder.getTrade_type());  
        parameters.put("out_trade_no", wechatOrder.getOut_trade_no());
        parameters.put("attach", wechatOrder.getAttach());

        String characterEncoding = "UTF-8";    
        String mySign = createSign(characterEncoding,parameters);   
       
          
        String userAgent = "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,like Geocko) Mobile/9B206 MicroMessenger/5.0";  
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15); 
        return mySign;
    }
    public static String getWX_Sign(Map<String,String> map) {
          
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        Set<String> set=map.keySet();
        for (String str : set) {
        	if ("sign".equals(str)) {
				continue;
			}
			String value=map.get(str);
			parameters.put(str, value); 
			
		} 
          
        String characterEncoding = "UTF-8";    
        String mySign = createSign(characterEncoding,parameters);   
       
          
        String userAgent = "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,like Geocko) Mobile/9B206 MicroMessenger/5.0";  
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15); 
        return mySign;
    } 
  
    /** 
     * 微信支付签名算法sign 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + Key);  
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
        return sign;  
    }  
    @SuppressWarnings("unchecked")  
    public static String createSign2(String characterEncoding,SortedMap<Object,Object> parameters){  
    	StringBuffer sb = new StringBuffer();  
    	Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
    	Iterator it = es.iterator();  
    	while(it.hasNext()) {  
    		Map.Entry entry = (Map.Entry)it.next();  
    		String k = (String)entry.getKey();  
    		Object v = entry.getValue();  
    		if(null != v && !"".equals(v)   
    				&& !"sign".equals(k) && !"key".equals(k)) {  
    			sb.append(k + "=" + v + "&");  
    		}  
    	}  
    	sb.append("key=SenichinaT9cloudAPPEncryptionKey");  
    	String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
    	System.out.println(sign);
    	return sign;  
    }  
}
