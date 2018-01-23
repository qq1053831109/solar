package org.solar.pay;


/**
 * User: wuxianchuan
 * 这里放置各种配置数据
 */
public class WXConfig {
//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改


    //受理模式下给子商户分配的子商户号
    private static String subMchID = "";

    //HTTPS证书的本地路径
    private static String certLocalPath = "";

    //HTTPS证书密码，默认密码等于商户号MCHID
    private static String certPassword = "";

    //是否使用异步线程的方式来上报API测速，默认为异步模式
    private static boolean useThreadToDoReport = true;
    //机器IP
    private static String ip = "";

    private static String nonce_str = "ibuaiVcKdpRxkhJA";
    private static String device_info = "1000";

    public static String getDevice_info() {
        return device_info;
    }

    public static void setDevice_info(String device_info) {
        WXConfig.device_info = device_info;
    }

    public static String getBody() {
        return body;
    }

    public static void setBody(String body) {
        WXConfig.body = body;
    }


    private static String body = "{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\"," +
            "\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"" +
            "},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1," +
            "\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}";
    //以下是几个API的路径：

    public static String getNonce_str() {
        return nonce_str;
    }

    public static String UnifiedOrder_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //1）被扫支付API
    public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    //2）被扫支付查询API
    public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

    //3）退款API
    public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //4）退款查询API
    public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

    //5）撤销API
    public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    //6）下载对账单API
    public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

    //7) 统计上报API
    public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

    public static boolean isUseThreadToDoReport() {
        return useThreadToDoReport;
    }

    public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
        WXConfig.useThreadToDoReport = useThreadToDoReport;
    }

    public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";


    public static void setSubMchID(String subMchID) {
        WXConfig.subMchID = subMchID;
    }

    public static void setCertLocalPath(String certLocalPath) {
        WXConfig.certLocalPath = certLocalPath;
    }

    public static void setCertPassword(String certPassword) {
        WXConfig.certPassword = certPassword;
    }

    public static void setIp(String ip) {
        WXConfig.ip = ip;
    }

    public static String getSubMchid() {
        return subMchID;
    }

    public static String getCertLocalPath() {
        return certLocalPath;
    }

    public static String getCertPassword() {
        return certPassword;
    }

    public static String getIP() {
        return ip;
    }

    public static void setHttpsRequestClassName(String name) {
        HttpsRequestClassName = name;
    }

}
