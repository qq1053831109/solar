package org.solar.wechat;

import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.coder.Md5Util;
import org.solar.coder.ShaUtil;
import org.solar.util.JdkHttpUtil;
import org.solar.util.JsonUtil;

import java.util.*;

public class WeChatPublicService {
    //POST
    public final static String sendTemplateMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    public final static String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create";
    //GET
    public final static String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    public final static String getUserListUrl = "https://api.weixin.qq.com/cgi-bin/user/get";
    public final static String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
    //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
    public final static String getticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public String appid;
    public String secret;

    public WeChatPublicService(String appid,String secret) {
        this.appid = appid;
        this.secret = secret;
    }

    /*
            发送模版消息
            https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
            touser	是	接收者openid
            template_id	是	模板ID
            url	否	模板跳转链接
            data	是	模板数据
             "key": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
              "key2": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   }
            color	否	模板内容字体颜色，不填默认为黑色
           返回示例{
           "errcode":0,
           "errmsg":"ok",
           "msgid":200228332
       }
         */
    public String sendTemplateMessage(String touser, String template_id, String url, Map data) {
        Map requetMap = new HashMap();
        requetMap.put("touser", touser);
        requetMap.put("template_id", template_id);
        if (url != null) {
            requetMap.put("url", url);
        }
        Set keySet=data.keySet();
        for (Object key:keySet) {
            Object val=data.get(key);
            if (val instanceof String||val instanceof Integer||val instanceof Long||val==null){
                Map dataValMap=new HashMap();
                dataValMap.put("value",val);
//                dataValMap.put("color","#173177");
                data.put(key,dataValMap);
            }
        }
        requetMap.put("data", data);
        String requetBody = JsonUtil.toJSONString(requetMap);
        return JdkHttpUtil.post(sendTemplateMessageUrl + "?access_token=" + getAccessToken(), requetBody);
    }
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
    public String createMenu(Object obj) {
        String requetBody = null;
        if (obj instanceof String){
            requetBody=(String)obj;
        }else {
              requetBody = JsonUtil.toJSONString(obj);
        }

        return JdkHttpUtil.post(createMenuUrl + "?access_token=" + getAccessToken(), requetBody);
    }

    /**
     * grant_type	是	获取access_token填写client_credential
     appid	是	第三方用户唯一凭证
     secret	是	第三方用户唯一凭证密钥，即appsecret
     * @return{"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    public synchronized String getAccessToken() {
        String accessToken=cache.get("WeChatPublicService:"+appid+":accessToken");
        if (accessToken==null){
            String result=JdkHttpUtil.get(getAccessTokenUrl+"?grant_type=client_credential&appid="+appid+"&secret="+secret);
            Map resultMap=JsonUtil.parseObject(result);
            accessToken=(String)resultMap.get("access_token");
            try{
                long expires_in=Long.valueOf(String.valueOf(resultMap.get("expires_in")));
                cache.put("WeChatPublicService:"+appid+":accessToken",accessToken,(expires_in-60)*1000);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("resultMap:");
                System.out.println(resultMap);
            }

        }
        return accessToken;
    }

    /**
     * next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return
     *
     total	关注该公众账号的总用户数
    count	拉取的OPENID个数，最大值为10000
    data	列表数据，OPENID的列表
    next_openid	拉取列表的最后一个用户的OPENID
     */
    public String getUserList( ) {
       return getUserList(null);
    }
    public   String getUserList(String next_openid) {
        //?access_token=ACCESS_TOKEN&=NEXT_OPENID
            String param="access_token=" + getAccessToken();
            if (next_openid!=null){
                param=param+"&next_openid="+next_openid;
            }
            String result=JdkHttpUtil.get(getUserListUrl,param);
            return  result;
    }
    /**
     subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     openid	用户的标识，对当前公众号唯一
     nickname	用户的昵称
     sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     city	用户所在城市
     country	用户所在国家
     province	用户所在省份
     language	用户的语言，简体中文为zh_CN
     headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     groupid	用户所在的分组ID（兼容旧的用户分组接口）
     tagid_list	用户被打上的标签ID列表
        （Json格式）
     */
    public   String getUserInfo(String openid) {
            String param="access_token=" + getAccessToken()+"&openid="+openid;
            String result=JdkHttpUtil.get(getUserInfoUrl,param);
            return  result;
    }
    public synchronized String getJsapiTicket() {
        String jsapiTicket=cache.get("WeChatPublicService:"+appid+":ticket:jsapi");
        if (jsapiTicket==null){
            String result=JdkHttpUtil.get(getticketUrl+"?access_token=" + getAccessToken()+"&type=jsapi");
            Map resultMap=JsonUtil.parseObject(result);
            jsapiTicket=(String)resultMap.get("ticket");
            long expires_in=Long.valueOf(String.valueOf(resultMap.get("expires_in")));
            cache.put("WeChatPublicService:"+appid+":ticket:jsapi",jsapiTicket,(expires_in-60)*1000);
        }
        return jsapiTicket;
    }
    public  Map getJsSdkConfig(String url) {
         Map map=new HashMap();
         map.put("noncestr", UUID.randomUUID().toString().replace("-", ""));
         map.put("jsapi_ticket",getJsapiTicket());
         map.put("timestamp",System.currentTimeMillis()/1000);
        if (url.contains("#")){
            url=url.substring(0,url.indexOf("#"));
        }
         map.put("url",url);
         map.put("signature",generateSignature(map));
         map.put("appid",appid);
         return map;
    }
    public static String generateSignature(final Map<String, String> data)  {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
                sb.append(k).append("=").append(String.valueOf(data.get(k)).trim()).append("&");
        }
        String str=sb.toString().substring(0,sb.length()-1);
        return ShaUtil.SHA1(str);
    }
    private Cache cache=new CacheImpl();
}
