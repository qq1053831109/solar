package test;

import org.solar.wechat.WeChatPublicService;

import java.util.HashMap;
import java.util.Map;

public class WeChatPublicServiceTest {
    public static String appid="wx8469730ef476ed71";
    public static String secret="ec8053d3f4a847f99acc6a91ac3c7321";
    public static String template_id="1GJ7zMHt6wqBdlzYjkhU4j7OcfpnoMMeT1AseQVc7XY";
    public static String open_id="ojNbawHeaQSSJMH024-MF78m6GOA";
    static WeChatPublicService weChatPublicService=new WeChatPublicService(appid,secret);
    public static void sendTemplateMessage() {
        Map data=new HashMap();
        Map name=new HashMap();
        Map msg=new HashMap();
        name.put("value","333");
        msg.put("value","444啊");
        data.put("name", name);
        data.put("msg",msg);
        String str=weChatPublicService.sendTemplateMessage(open_id, template_id,"www.baidu.com",data);
        System.out.println(str);
    }

    public static void getUserList(String[] args) {
        String str=weChatPublicService.getUserList(null);
        System.out.println(str);
    }
    public static void getUserInfo(String[] args) {
        String str=weChatPublicService.getUserInfo(open_id);
        System.out.println(str);
    }

    public static void main(String[] args) {
        sendTemplateMessage();
    }
}
