package test;

import org.solar.wechat.WeChatPublicService;
import org.solar.wechat.WeChatWebOAuth;

public class WeChatWebOAuthTest {
    public static String appid="wx8469730ef476ed71";
    public static String secret="ec8053d3f4a847f99acc6a91ac3c7321";
    static WeChatWebOAuth weChatWebOAuth=new WeChatWebOAuth(
            appid,secret);

    public static void main(String[] args) {

        System.out.println(weChatWebOAuth.getOauthBaseUrl("www.heduim.com","123"));
        System.out.println(weChatWebOAuth.getOauthUserInfoUrl("www.heduim.com","123"));
    }
}
