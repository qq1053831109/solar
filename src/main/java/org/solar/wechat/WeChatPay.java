package org.solar.wechat;

import org.solar.coder.Md5Util;
import org.solar.exception.SolarRuntimeException;
import org.solar.util.JdkHttpUtil;
import org.solar.util.XMLUtil;

import java.util.*;

public class WeChatPay {
    public static final String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static final String transfersUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    public static final String getTransfersInfoUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
    //JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
    public static final String tradeType_JSAPI = "JSAPI";
    public static final String tradeType_NATIVE = "NATIVE";
    public static final String tradeType_APP = "APP";
    private String appid;
    private String mch_id;
    private String key;
    private String notify_url;
    private String mch_appid;
    private String spbill_create_ip;
    private HttpsWithCert httpsWithCert;

    public WeChatPay(String appid, String mch_id, String key, String notify_url) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.notify_url = notify_url;
        this.key = key;
    }

    public WeChatPay(String appid, String mch_id, String key, String notify_url,
                     String mch_appid, String spbill_create_ip, String keyStore, String keyStorePass) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.notify_url = notify_url;
        this.key = key;
        this.mch_appid = mch_appid;
        this.spbill_create_ip = spbill_create_ip;
        httpsWithCert = new HttpsWithCert(keyStore, keyStorePass);
    }


    /**
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[OK]]></return_msg>
     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     * <mch_id><![CDATA[10000100]]></mch_id>
     * <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
     * <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>
     * <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
     * <trade_type><![CDATA[JSAPI]]></trade_type>
     * return 根据交易类型 返回code_url 或prepay_id
     */
    public String unifiedOrderTradeType_JSAPI(String out_trade_no, String body, String total_fee, String ip, String openid) {
        Map reqData = new HashMap();
        reqData.put("appid", appid);
        reqData.put("out_trade_no", out_trade_no);
        reqData.put("body", body);
        reqData.put("total_fee", total_fee);
        reqData.put("openid", openid);
        reqData.put("trade_type", tradeType_JSAPI);
        reqData.put("spbill_create_ip", ip);
        reqData.put("mch_id", mch_id);
        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("notify_url", notify_url);
        reqData.put("sign_type", "MD5");
        reqData.put("sign", generateSignature(reqData, key));
        String result = JdkHttpUtil.post(unifiedOrderUrl, XMLUtil.toXml(reqData));
        Map<String, String> resultMap = XMLUtil.parseXml(result);
        if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
            if (!isSignatureValid(resultMap)) {
                throw new SolarRuntimeException("微信返回数据的签名不正确!");
            }
                return resultMap.get("prepay_id");
        }
        return result;
    }

    public String unifiedOrder(String out_trade_no, String body, String total_fee, String ip) {
        Map reqData = new HashMap();
        reqData.put("appid", appid);
        reqData.put("out_trade_no", out_trade_no);
        reqData.put("body", body);
        reqData.put("total_fee", total_fee);
        reqData.put("trade_type", tradeType_NATIVE);
        reqData.put("spbill_create_ip", ip);
        reqData.put("mch_id", mch_id);
        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("notify_url", notify_url);
        reqData.put("sign_type", "MD5");
        reqData.put("sign", generateSignature(reqData, key));
        String result = JdkHttpUtil.post(unifiedOrderUrl, XMLUtil.toXml(reqData));
        Map<String, String> resultMap = XMLUtil.parseXml(result);
        if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
            if (!isSignatureValid(resultMap)) {
                throw new SolarRuntimeException("微信返回数据的签名不正确!");
            }
            return resultMap.get("code_url");
        }

        return result;
    }

    /**
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <mch_id>10000100</mch_id>
     * <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
     * <out_trade_no>1008450740201411110005820873</out_trade_no> 要求32个字符内，只能是数字、大小写字母
     * <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
     * </xml>
     *
     * @return <xml>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[OK]]></return_msg>
     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     * <mch_id><![CDATA[10000100]]></mch_id>
     * <device_info><![CDATA[1000]]></device_info>
     * <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>
     * <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>
     * <is_subscribe><![CDATA[Y]]></is_subscribe>
     * <trade_type><![CDATA[MICROPAY]]></trade_type>
     * <bank_type><![CDATA[CCB_DEBIT]]></bank_type>
     * <total_fee>1</total_fee>
     * <fee_type><![CDATA[CNY]]></fee_type>
     * <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>
     * <out_trade_no><![CDATA[1415757673]]></out_trade_no>
     * <attach><![CDATA[订单额外描述]]></attach>
     * <time_end><![CDATA[20141111170043]]></time_end>
     * <trade_state><![CDATA[SUCCESS]]></trade_state>
     * </xml>
     */
    public String orderQuery(String out_trade_no) {
        Map reqData = new HashMap();
        reqData.put("appid", appid);
        reqData.put("mch_id", mch_id);
        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("out_trade_no", out_trade_no);
        reqData.put("sign_type", "MD5");
        reqData.put("sign", generateSignature(reqData, key));
        String result = JdkHttpUtil.post(unifiedOrderUrl, XMLUtil.toXml(reqData));
        Map<String, String> resultMap = XMLUtil.parseXml(result);


        return result;
    }

    /**
     * <xml>
     * <attach><![CDATA[支付测试]]></attach>
     * <bank_type><![CDATA[CFT]]></bank_type>
     * <fee_type><![CDATA[CNY]]></fee_type>
     * <is_subscribe><![CDATA[Y]]></is_subscribe>
     * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
     * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
     * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
     * <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
     * <time_end><![CDATA[20140903131540]]></time_end>
     * <total_fee>1</total_fee>
     * <coupon_fee><![CDATA[10]]></coupon_fee>
     * <coupon_count><![CDATA[1]]></coupon_count>
     * <coupon_type><![CDATA[CASH]]></coupon_type>
     * <coupon_id><![CDATA[10000]]></coupon_id>
     * <coupon_fee><![CDATA[100]]></coupon_fee>
     * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
     * </xml>
     * return 处理完需要返回:
     * <xml>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[OK]]></return_msg>
     * </xml>
     */
    public Map onPayResultNotify(String data) {
        Map<String, String> dataMap = XMLUtil.parseXml(data);
        if (!"SUCCESS".equals(dataMap.get("return_code"))) {
            return null;
        }
        if (!"SUCCESS".equals(dataMap.get("result_code"))) {
            return null;
        }
        if (!isSignatureValid(dataMap)) {
            throw new SolarRuntimeException("微信返回数据的签名不正确!");
        }
        if (!appid.equals(dataMap.get("appid"))) {
            throw new SolarRuntimeException("appid不匹配!");
        }
        if (!mch_id.equals(dataMap.get("mch_id"))) {
            throw new SolarRuntimeException("mch_id不匹配!");
        }
        return dataMap;
    }

    /**
     * "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入
     * "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数
     * "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串
     * "package":"prepay_id=u802345jgfjsdfgsdg888",
     * "signType":"MD5",         //微信签名方式：
     * "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
     */
    public Map getBrandWCPayRequestParam(String prepay_id) {
//        Map<String, String> reqData = new HashMap();
//        reqData.put("appid", appid);
//        reqData.put("timeStamp", System.currentTimeMillis() / 1000 + "");
//        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
//        reqData.put("package", "prepay_id=" + prepay_id);
//        reqData.put("sign", generateSignature(reqData, key));
//        reqData.put("sign_type", "MD5");
        Map<String, String> reqData = new HashMap();
        reqData.put("appId", appid);
        reqData.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        reqData.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("package", "prepay_id=" + prepay_id);
        reqData.put("signType", "MD5");
        reqData.put("paySign", generateSignature(reqData, key));

        return reqData;
    }
    /**
     <xml>
     <mch_appid>wxe062425f740c30d8</mch_appid>
     <mchid>10000098</mchid>
     <nonce_str>3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS</nonce_str>
     <partner_trade_no>100000982014120919616</partner_trade_no>
     <openid>ohO4Gt7wVPxIT1A9GjFaMYMiZY1s</openid>
     <check_name>FORCE_CHECK</check_name>
     <re_user_name>张三</re_user_name>
     <amount>100</amount>
     <desc>节日快乐!</desc>
     <spbill_create_ip>10.2.3.10</spbill_create_ip>
     </xml>
     商户账号appid	mch_appid	是	wx8888888888888888	String	微信分配的账号ID（企业号corpid即为此appId）
     商户号	mchid	是	1900000109	String(32)	微信支付分配的商户号
     设备号	device_info	否	013467007045764	String(32)	微信支付分配的终端设备号
     随机字符串	nonce_str	是	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	String(32)	随机字符串，不长于32位
     签名	sign	是	C380BEC2BFD727A4B6845133519F3AD6	String(32)	签名，详见签名算法
     商户订单号	partner_trade_no	是	10000098201411111234567890	String	商户订单号，需保持唯一性
     (只能是字母或者数字，不能包含有符号)
     用户openid	openid	是	oxTWIuGaIt6gTKsQRLau2M0yL16E	String	商户appid下，某用户的openid
     校验用户姓名选项	check_name	是	FORCE_CHECK	String	NO_CHECK：不校验真实姓名
     FORCE_CHECK：强校验真实姓名
     收款用户姓名	re_user_name	可选	王小王	String	收款用户真实姓名。  如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     金额	amount	是	10099	int	企业付款金额，单位为分
     企业付款描述信息	desc	是	理赔	String	企业付款操作说明信息。必填。
     Ip地址	spbill_create_ip	是	192.168.0.1	String(32)	调用接口的机器Ip地址
     */
    /**
     * <xml>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[]]></return_msg>
     * <mch_appid><![CDATA[wxec38b8ff840bd989]]></mch_appid>
     * <mchid><![CDATA[10013274]]></mchid>
     * <device_info><![CDATA[]]></device_info>
     * <nonce_str><![CDATA[lxuDzMnRjpcXzxLx0q]]></nonce_str>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <partner_trade_no><![CDATA[10013574201505191526582441]]></partner_trade_no>
     * <payment_no><![CDATA[1000018301201505190181489473]]></payment_no>
     * <payment_time><![CDATA[2015-05-19 15：26：59]]></payment_time>
     * </xml>
     */
    public String transfer(String partner_trade_no, String openid, String amount, String desc) {
        Map<String, String> reqData = new HashMap();
        reqData.put("mch_appid", mch_appid);
        reqData.put("mchid", mch_id);
        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("partner_trade_no", partner_trade_no);
        reqData.put("openid", openid);
        reqData.put("check_name", "NO_CHECK");
        reqData.put("desc", desc);
        reqData.put("spbill_create_ip", spbill_create_ip);
        reqData.put("amount", amount);
        reqData.put("sign", generateSignature(reqData, key));
        return httpsWithCert.post(transfersUrl, XMLUtil.toXml(reqData));
    }

    /**
     商户订单号	partner_trade_no	是	10000098201411111234567890	String(28)	商户调用企业付款API时使用的商户订单号

     */
    /**
     * <xml> // 按照格式补充
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[获取成功]]></return_msg>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <mch_id>10000098</mch_id>
     * <appid><![CDATA[wxe062425f740c30d8]]></appid>
     * <detail_id><![CDATA[1000000000201503283103439304]]></detail_id>
     * <partner_trade_no><![CDATA[1000005901201407261446939628]]></partner_trade_no>
     * <status><![CDATA[SUCCESS]]></status>
     * <payment_amount>650</payment_amount >
     * <openid ><![CDATA[oxTWIuGaIt6gTKsQRLau2M0yL16E]]></openid>
     * <transfer_time><![CDATA[2015-04-21 20:00:00]]></transfer_time>
     * <transfer_name ><![CDATA[测试]]></transfer_name >
     * <desc><![CDATA[福利测试]]></desc>
     * </xml>
     * 返回状态码	return_code	是	SUCCESS	String(16)
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     * 返回信息	return_msg	否	签名失败	String(128)
     * <p>
     * 以下字段在return_code为SUCCESS的时候有返回
     * 业务结果	result_code	是	SUCCESS	String(16)	SUCCESS/FAIL
     * 错误代码	err_code	否	SYSTEMERROR	String(32)	错误码信息
     * 错误代码描述	err_code_des	否	系统错误	String(128)	结果信息描述
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     * 商户单号	partner_trade_no	是	10000098201411111234567890	String(28)	商户使用查询API填写的单号的原路返回.
     * 商户号	mch_id	是	10000098	String(32)	微信支付分配的商户号
     * 付款单号	detail_id	是	1000000000201503283103439304	String(32)	调用企业付款API时，微信系统内部产生的单号
     * 转账状态	status	是	SUCCESS	string(16)
     * SUCCESS:转账成功
     * FAILED:转账失败
     * PROCESSING:处理中
     * 失败原因	reason	否	余额不足	String	如果失败则有失败原因
     * 收款用户openid	openid	是	oxTWIuGaIt6gTKsQRLau2M0yL16E	 	转账的openid
     * 收款用户姓名	transfer_name	否	马华	String	收款用户姓名
     * 付款金额	payment_amount	是	5000	int	付款金额单位分）
     * 转账时间	transfer_time	是	2015-04-21 20:00:00	String	发起转账的时间
     * 付款描述	desc	是	车险理赔	String	付款时候的描述
     */
    public String getTransfersInfo(String partner_trade_no) {
        Map<String, String> reqData = new HashMap();
        reqData.put("appid", appid);
        reqData.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        reqData.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        reqData.put("partner_trade_no", partner_trade_no);
        reqData.put("sign", generateSignature(reqData, key));
        return httpsWithCert.post(getTransfersInfoUrl, XMLUtil.toXml(reqData));
    }

    public boolean isSignatureValid(Map<String, String> data) {
        String sign = data.get("sign");
        return generateSignature(data, key).equals(sign);
    }

    public static String generateSignature(final Map<String, String> data, String key) {
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
