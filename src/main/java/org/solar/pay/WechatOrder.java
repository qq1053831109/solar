package org.solar.pay;

public class WechatOrder {
    private String appid = "";
    private String mch_id = "";
    private String device_info = WXConfig.getDevice_info();
    private String nonce_str = WXConfig.getNonce_str();
    private String body;
    private String sign;

    private String notify_url = "";
    private String trade_type = "NATIVE";
    // 商户订单号
    private String out_trade_no;
    private String total_fee = "1";
    private String spbill_create_ip;
    private String attach;

    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String toXMLString() {
        return "<xml>"
                + " <appid><![CDATA[" + appid + "]]></appid>"
                + " <mch_id><![CDATA[" + mch_id + "]]></mch_id>"
                + "<device_info><![CDATA[" + device_info + "]]></device_info>"
                + "<nonce_str><![CDATA[" + nonce_str + "]]></nonce_str>"
                + "<spbill_create_ip><![CDATA[" + spbill_create_ip
                + "]]></spbill_create_ip>" + "<body><![CDATA[" + body
                + "]]></body>" + "<sign><![CDATA[" + WeChatMd5Util.getWX_Sign(this) + "]]></sign>"
                + " <total_fee><![CDATA[" + total_fee + "]]></total_fee>"
                + " <notify_url><![CDATA[" + notify_url + "]]></notify_url>"
                + " <trade_type><![CDATA[" + trade_type + "]]></trade_type>"
                + " <attach><![CDATA[" + attach + "]]></attach>"
                + " <out_trade_no><![CDATA[" + out_trade_no
                + "]]></out_trade_no>" + "</xml>";
    }

}
