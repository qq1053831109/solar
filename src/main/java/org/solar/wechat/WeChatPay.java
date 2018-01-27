package org.solar.wechat;

import org.solar.coder.Md5Util;
import org.solar.exception.SolarRuntimeException;
import org.solar.util.JdkHttpUtil;
import org.solar.util.XMLUtil;

import java.util.*;

public class WeChatPay {
    public static final String unifiedOrderUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String orderQueryUrl="https://api.mch.weixin.qq.com/pay/orderquery";
    //JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
    public static final String tradeType_JSAPI="JSAPI";
    public static final String tradeType_NATIVE="NATIVE";
    public static final String tradeType_APP="APP";
    private String appid;
    private String mch_id;
    private String notify_url;
    private String key;

    public WeChatPay(String appid, String mch_id, String notify_url, String key) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.notify_url = notify_url;
        this.key = key;
    }

    /**
     <return_code><![CDATA[SUCCESS]]></return_code>
     <return_msg><![CDATA[OK]]></return_msg>
     <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     <mch_id><![CDATA[10000100]]></mch_id>
     <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
     <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>
     <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
     <result_code><![CDATA[SUCCESS]]></result_code>
     <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
     <trade_type><![CDATA[JSAPI]]></trade_type>
     return 根据交易类型 返回code_url 或prepay_id
     */

    public String unifiedOrder(String out_trade_no,String  body,String  total_fee,String ip,String trade_type){
        Map reqData=new HashMap();
        reqData.put("appid",appid);
        reqData.put("out_trade_no",out_trade_no);
        reqData.put("body",body);
        reqData.put("total_fee",total_fee);
        reqData.put("trade_type",trade_type);
        reqData.put("spbill_create_ip",ip);
        reqData.put("mch_id",mch_id);
        reqData.put("nonce_str",UUID.randomUUID().toString().replace("-", ""));
        reqData.put("notify_url",notify_url);
        reqData.put("sign_type","MD5");
        reqData.put("sign",generateSignature(reqData,key));
        String result=JdkHttpUtil.post(unifiedOrderUrl, XMLUtil.toXml(reqData));
        Map<String,String> resultMap=XMLUtil.parseXml(result);
        if ("SUCCESS".equals(resultMap.get("return_code"))&&"SUCCESS".equals(resultMap.get("result_code"))){
            if (!isSignatureValid(resultMap)){
                throw new SolarRuntimeException("微信返回数据的签名不正确!");
            }
            if (trade_type.equals(tradeType_NATIVE)){
                return resultMap.get("code_url");
            }
            if (trade_type.equals(tradeType_JSAPI)){
                return resultMap.get("prepay_id");
            }
        }

        return result;
    }

    /**
     <xml>
     <appid>wx2421b1c4370ec43b</appid>
     <mch_id>10000100</mch_id>
     <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
     <out_trade_no>1008450740201411110005820873</out_trade_no> 要求32个字符内，只能是数字、大小写字母
     <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
     </xml>
     * @return
     */
    public String orderQuery(String out_trade_no){
        Map reqData=new HashMap();
        reqData.put("appid",appid);
        reqData.put("mch_id",mch_id);
        reqData.put("nonce_str",UUID.randomUUID().toString().replace("-", ""));
        reqData.put("out_trade_no",out_trade_no);
        reqData.put("sign_type","MD5");
        reqData.put("sign",generateSignature(reqData,key));
        String result=JdkHttpUtil.post(unifiedOrderUrl, XMLUtil.toXml(reqData));
        Map<String,String> resultMap=XMLUtil.parseXml(result);


        return result;
    }

    /**
     <xml>
     <attach><![CDATA[支付测试]]></attach>
     <bank_type><![CDATA[CFT]]></bank_type>
     <fee_type><![CDATA[CNY]]></fee_type>
     <is_subscribe><![CDATA[Y]]></is_subscribe>
     <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
     <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
     <out_trade_no><![CDATA[1409811653]]></out_trade_no>
     <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
     <time_end><![CDATA[20140903131540]]></time_end>
     <total_fee>1</total_fee>
     <coupon_fee><![CDATA[10]]></coupon_fee>
     <coupon_count><![CDATA[1]]></coupon_count>
     <coupon_type><![CDATA[CASH]]></coupon_type>
     <coupon_id><![CDATA[10000]]></coupon_id>
     <coupon_fee><![CDATA[100]]></coupon_fee>
     <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
     </xml>
     return 处理完需要返回:
     <xml>
     <return_code><![CDATA[SUCCESS]]></return_code>
     <return_msg><![CDATA[OK]]></return_msg>
     </xml>
     */
    public Map onPayResultNotify(String data){
        Map<String,String> dataMap=XMLUtil.parseXml(data);
        if (!"SUCCESS".equals(dataMap.get("return_code"))){
            return null;
        }
        if (!"SUCCESS".equals(dataMap.get("result_code"))){
            return null;
        }
        if (!isSignatureValid(dataMap)){
            throw new SolarRuntimeException("微信返回数据的签名不正确!");
        }
        if(!appid.equals(dataMap.get("appid"))){
            throw new SolarRuntimeException("appid不匹配!");
        }
        if(!mch_id.equals(dataMap.get("mch_id"))){
            throw new SolarRuntimeException("mch_id不匹配!");
        }
        return dataMap;
    }

    /**
     "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入
     "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数
     "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串
     "package":"prepay_id=u802345jgfjsdfgsdg888",
     "signType":"MD5",         //微信签名方式：
     "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
     */
    public Map getBrandWCPayRequestParam(String prepay_id){
        Map<String, String> reqData=new HashMap();
        reqData.put("appid",appid);
        reqData.put("timeStamp",System.currentTimeMillis()/1000+"");
        reqData.put("nonce_str",UUID.randomUUID().toString().replace("-", ""));
        reqData.put("package","prepay_id="+prepay_id);
        reqData.put("sign_type","MD5");
        reqData.put("sign",generateSignature(reqData,key));
        return reqData;
    }

    public  boolean isSignatureValid(Map<String, String> data) {
        String sign=data.get("sign");
        return generateSignature(data, key).equals(sign);
    }
    public static String generateSignature(final Map<String, String> data, String key)  {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(String.valueOf(data.get(k)).trim()).append("&");
        }
        sb.append("key=").append(key);
        return Md5Util.getMd5Hex(sb.toString()).toUpperCase();
    }

}
