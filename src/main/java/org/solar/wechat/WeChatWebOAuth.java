package org.solar.wechat;

import org.solar.cache.Cache;
import org.solar.cache.CacheImpl;
import org.solar.util.JdkHttpUtil;
import org.solar.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class WeChatWebOAuth {
    public String appid;
    public String secret;

    public WeChatWebOAuth(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }

    public final static String getAccessTokenByCodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public final static String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    public final static String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";


    /**
     * scope为snsapi_base
     * appid= &redirect_uri= &response_type=code&scope=snsapi_base&state=123
     * scope为snsapi_userinfo
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
     *
     * @return 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     */
    public String getOauthBaseUrl(String redirect_uri, String state) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid);
        sb.append("&redirect_uri=" + redirect_uri);
        sb.append("&response_type=code");
        sb.append("&scope=snsapi_base");
        if (state != null) {
            sb.append("&state=" + state);
        }
        sb.append("#wechat_redirect");
        return sb.toString();
    }

    public String getOauthUserInfoUrl(String redirect_uri, String state) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid);
        sb.append("&redirect_uri=" + redirect_uri);
        sb.append("&response_type=code");
        sb.append("&scope=snsapi_userinfo");
        if (state != null) {
            sb.append("&state=" + state);
        }
        sb.append("#wechat_redirect");
        return sb.toString();
    }

    /*
        { "access_token":"ACCESS_TOKEN",
    "expires_in":7200,
    "refresh_token":"REFRESH_TOKEN",
    "openid":"OPENID",
    "scope":"SCOPE" }
     */
    public String getAccessTokenByCode(String code) {
        String param = "appid=" + appid;
        param = param + "&secret=" + secret;
        param = param + "&code=" + code;
        param = param + "&grant_type=authorization_code";
        return JdkHttpUtil.get(getAccessTokenByCodeUrl, param);
    }

    /*
        { "access_token":"ACCESS_TOKEN",
    "expires_in":7200,
    "refresh_token":"REFRESH_TOKEN",
    "openid":"OPENID",
    "scope":"SCOPE" }
     */
    public String refreshToken(String refresh_token) {
        String param = "appid=" + appid;
        param = param + "&grant_type=refresh_token";
        param = param + "&refresh_token=" + refresh_token;
        return JdkHttpUtil.get(refreshTokenUrl, param);
    }
    /**
        {    "openid":" OPENID",
        " nickname": NICKNAME,
        "sex":"1",
        "province":"PROVINCE"
        "city":"CITY",
        "country":"COUNTRY",
        "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
        "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
        "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
        }
     */
    //需scope为 snsapi_userinfo
    public String getUserInfo(String access_token,String openid) {
        String param = "access_token=" + access_token;
        param = param + "&openid="+openid;
        param = param + "&lang=zh_CN";
        return JdkHttpUtil.get(getUserInfoUrl, param);
    }
}
