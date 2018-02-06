//package org.solar.alipay;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.domain.AlipayTradeQueryModel;
//import com.alipay.api.domain.AlipayTradeWapPayModel;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.*;
//import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
//import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
//import com.alipay.api.response.AlipaySystemOauthTokenResponse;
//import com.alipay.api.response.AlipayTradeQueryResponse;
//import org.solar.util.JsonUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 依赖 alipay-sdk-java20180104135026.jar
// */
//public class Alipay {
//    public static final String payeeType_ALIPAY_USERID="ALIPAY_USERID";
//    public static final String payeeType_ALIPAY_LOGONID="ALIPAY_LOGONID";
//    public final static String scope_auth_base="auth_base";//静默授权
//    public final static String scope_auth_user="auth_user";//获取用户信息、网站支付宝登录
//
//    private String gatewayUrl="https://openapi.alipay.com/gateway.do";
//    private String appid;
//    private String rsaPrivateKey;
//    private String format="json";
//    private String alipayPublicKey;
//    private String signtype="RSA2";
//    private String notify_url;
//    private String return_url;
//    private  AlipayClient client;
//    public Alipay(String appid, String alipayPublicKey, String rsaPrivateKey, String notify_url, String return_url) {
//        this.appid = appid;
//        this.notify_url = notify_url;
//        this.alipayPublicKey = alipayPublicKey;
//        this.rsaPrivateKey = rsaPrivateKey;
//        this.return_url = return_url;
//        //AlipayClient的实现类都是线程安全的，所以没有必要每次API请求都新建一个AlipayClient实现类；
//        client = new DefaultAlipayClient(gatewayUrl, appid, rsaPrivateKey, format, "UTF-8", alipayPublicKey,signtype);
//    }
//
//    /**
//     total_amount	订单总金额，单位为元
//     body 描述 可空
//     * @return 创建支付宝订单并返回手机网站支付的html页面
//     */
//    public String mobileWebPay(String out_trade_no,String  subject,String  total_amount){
////         // 销售产品码
//        /**********************/
//        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
//        // 封装请求支付信息
//        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
//        model.setOutTradeNo(out_trade_no);
//        model.setSubject(subject);
//        model.setTotalAmount(total_amount);
////        model.setBody(body);
////        model.setTimeoutExpress(timeout_express);
////        model.setProductCode(product_code);
//        alipay_request.setBizModel(model);
//        // 设置异步通知地址
//        alipay_request.setNotifyUrl(notify_url);
//        // 设置同步地址
//        alipay_request.setReturnUrl(return_url);
//        // form表单生产
//        String htmlForm = null;
//        try {
//            // 调用SDK生成表单
//            htmlForm = client.pageExecute(alipay_request).getBody();
//            return htmlForm;
//        } catch (AlipayApiException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return htmlForm;
//    }
//
//    /**
//     {
//     "alipay_trade_query_response": {
//     "code": "10000",
//     "msg": "Success",
//     "buyer_logon_id": "105***@qq.com", 买家支付宝账号
//     "buyer_pay_amount": "0.00",
//     "buyer_user_id": "2088012612282655",必填 买家在支付宝的用户id
//     "invoice_amount": "0.00",
//     "out_trade_no": "2018128144652950", 商家订单号
//     "point_amount": "0.00",
//     "receipt_amount": "0.00",
//     "send_pay_date": "2018-01-28 14:47:01", 本次交易打款给卖家的时间
//     "total_amount": "0.01", 该参数的值为支付时传入的total_amount
//     "trade_no": "2018012821001004650511526656", 支付宝交易号
//     "trade_status": "TRADE_SUCCESS"  交易状态
//     },
//     "sign": "signtext"
//     }
//     交易状态：
//     WAIT_BUYER_PAY（交易创建，等待买家付款）、
//     TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
//     TRADE_SUCCESS（交易支付成功）、
//     TRADE_FINISHED（交易结束，不可退款）
//     TRADE_CLOSED
//     */
//    public Map orderQuery(String out_trade_no){
//        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
//        AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
//
//        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
//        model.setOutTradeNo(out_trade_no);
//        alipay_request.setBizModel(model);
//
//        AlipayTradeQueryResponse alipay_response = null;
//        try {
//            alipay_response = client.execute(alipay_request);
//            return JsonUtil.parseObject(alipay_response.getBody());
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     out_biz_no	String	必选	64	商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。
//     不同来源方给出的ID可以重复，同一个来源方必须保证其ID的唯一性。
//     只支持半角英文、数字，及“-”、“_”。	3142321423432
//     payee_type	String	必选	20	收款方账户类型。可取值：
//     1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
//     2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。	ALIPAY_LOGONID
//     payee_account	String	必选	100	收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。	abc@sina.com
//     amount	String	必选	16	转账金额，单位：元。
//     只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。
//     最大转账金额以实际签约的限额为准。	12.23
//     payer_show_name	String	可选	100	付款方姓名（最长支持100个英文/50个汉字）。显示在收款方的账单详情页。如果该字段不传，则默认显示付款方的支付宝认证姓名或单位名称。	上海交通卡退款
//     payee_real_name	String	可选	100	收款方真实姓名（最长支持100个英文/50个汉字）。
//     如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。	张三
//     remark	String	可选	200	转账备注（支持200个英文/100个汉字）。
//     */
//    public String transfer(String out_biz_no,String payee_type,String payee_account,String amount,String remark){
//        Map map=new HashMap();
//        map.put("out_biz_no",out_biz_no);
//        map.put("payee_type",payee_type);
//        map.put("payee_account",payee_account);
//        map.put("amount",amount);//单位元
//        map.put("remark",remark);//单位元
//        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//        request.setBizContent(JsonUtil.toJSONString(map));
//        AlipayFundTransToaccountTransferResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//        return response.getMsg();
//    }
//
//    /**
//
//     参数	类型	是否必填	最大长度	描述	示例值
//     out_biz_no	String	可选	64	商户转账唯一订单号：发起转账来源方定义的转账单据ID。
//     和支付宝转账单据号不能同时为空。当和支付宝转账单据号同时提供时，将用支付宝转账单据号进行查询，忽略本参数。	3142321423432
//     order_id	String	可选	64	支付宝转账单据号：和商户转账唯一订单号不能同时为空。当和商户转账唯一订单号同时提供时，将用本参数进行查询，忽略商户转账唯一订单号。
//     */
//    /**
//     order_id	String	选填	64	支付宝转账单据号，查询失败不返回。	2912381923
//     status	String	选填	10	转账单据状态。
//     SUCCESS：成功（配合"单笔转账到银行账户接口"产品使用时, 同一笔单据多次查询有可能从成功变成退票状态）；
//     FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
//     INIT：等待处理；
//     DEALING：处理中；
//     REFUND：退票（仅配合"单笔转账到银行账户接口"产品使用时会涉及, 具体退票原因请参见fail_reason返回值）；
//     UNKNOWN：状态未知。	SUCCESS
//     pay_date	String	选填	20	支付时间，格式为yyyy-MM-dd HH:mm:ss，转账失败不返回。	2013-01-01 08:08:08
//     arrival_time_end	String	选填	20	预计到账时间，转账到银行卡专用，格式为yyyy-MM-dd HH:mm:ss，转账受理失败不返回。
//     注意：
//     此参数为预计时间，可能与实际到账时间有较大误差，不能作为实际到账时间使用，仅供参考用途。	2013-01-01 08:08:08
//     order_fee	String	选填	20	预计收费金额（元），转账到银行卡专用，数字格式，精确到小数点后2位，转账失败或转账受理失败不返回。	0.02
//     fail_reason	String	选填	100	查询到的订单状态为FAIL失败或REFUND退票时，返回具体的原因。	单笔额度超限
//     out_biz_no	String	选填	64	发起转账来源方定义的转账单据号。
//     该参数的赋值均以查询结果中 的 out_biz_no 为准。
//     如果查询失败，不返回该参数。	3142321423432
//     error_code	String	选填	100	查询失败时，本参数为错误代 码。
//     查询成功不返回。 对于退票订单，不返回该参数。
//     */
//    /**
//     {
//     "alipay_fund_trans_order_query_response": {
//     "code": "10000",
//     "msg": "Success",
//     "order_id": "2912381923",
//     "status": "SUCCESS",
//     "pay_date": "2013-01-01 08:08:08",
//     "arrival_time_end": "2013-01-01 08:08:08",
//     "order_fee": "0.02",
//     "fail_reason": "单笔额度超限",
//     "out_biz_no": "3142321423432",
//     "error_code": "ORDER_NOT_EXIST"
//     },
//     "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
//     }
//     */
//    public String transferOrderQuery(String out_biz_no){
//        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
//        request.setBizContent("{" +
//                "    \"out_biz_no\":\""+out_biz_no+"\"" +
//                "  }");
//        AlipayFundTransOrderQueryResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//        return response.getMsg();
//    }
//    public String getOauthUrl(String redirect_uri, String state) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=" + appid);
//        sb.append("&scope="+scope_auth_base);
//        sb.append("&redirect_uri="+redirect_uri);
//        if (state != null) {
//            sb.append("&state=" + state);
//        }
//        return sb.toString();
//    }
//
//
//    public String getUserIdByAuthCode(String auth_code) {
//        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
//        request.setCode(auth_code);
//        request.setGrantType("authorization_code");
//        AlipaySystemOauthTokenResponse oauthTokenResponse=null;
//        try {
//            oauthTokenResponse = client.execute(request);
//        } catch (AlipayApiException e) {
//            //处理异常
//            e.printStackTrace();
//        }
//        return oauthTokenResponse.getUserId();
//    }
//
//
//
//
//    public Map onPayResultNotify(Map<String,String> params){
//        //获取支付宝POST过来反馈信息
////        Map requestParams = request.getParameterMap();
////        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
////            String name = (String) iter.next();
////            String[] values = (String[]) requestParams.get(name);
////            String valueStr = "";
////            for (int i = 0; i < values.length; i++) {
////                valueStr = (i == values.length - 1) ? valueStr + values[i]
////                        : valueStr + values[i] + ",";
////            }
////            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
////            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
////            params.put(name, valueStr);
////        }
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//        //商户订单号
//
//        String out_trade_no =  params.get("out_trade_no");
//        //支付宝交易号
//
//        String trade_no =  params.get("trade_no");
//
//        //交易状态
//        String trade_status = params.get("trade_status");
//
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//        //计算得出通知验证结果
//        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//        boolean verify_result = false;
//        try {
//            verify_result = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//
//        if(verify_result){//验证成功
//            //////////////////////////////////////////////////////////////////////////////////////////
//            //请在这里加上商户的业务逻辑程序代码
//
//            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//
//            if(trade_status.equals("TRADE_FINISHED")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
//            } else if (trade_status.equals("TRADE_SUCCESS")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
//            }
//
//            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//            // out.println("success");	//请不要修改或删除
//
//            //////////////////////////////////////////////////////////////////////////////////////////
//        }
//
//
//        return params;
//    }
//    public boolean checkSignature(Map params){
//        boolean verify_result = false;
//        try {
//            verify_result = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return verify_result;
//    }
//
//
//}
