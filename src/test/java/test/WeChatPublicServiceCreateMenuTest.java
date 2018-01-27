package test;

import org.solar.wechat.WeChatPublicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatPublicServiceCreateMenuTest {
    public static String appid="wx8469730ef476ed71";
    public static String secret="ec8053d3f4a847f99acc6a91ac3c7321";
    public static String template_id="1GJ7zMHt6wqBdlzYjkhU4j7OcfpnoMMeT1AseQVc7XY";
    public static String open_id="ojNbawHeaQSSJMH024-MF78m6GOA";
    static WeChatPublicService weChatPublicService=new WeChatPublicService(appid,secret);

    public static void main(String[] args) {

       String str= weChatPublicService.createMenu("{\n" +
               "     \"button\":[\n" +
               "     {    \n" +
               "          \"type\":\"click\",\n" +
               "          \"name\":\"今日歌曲\",\n" +
               "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
               "      },\n" +
               "      {\n" +
               "           \"name\":\"菜单\",\n" +
               "           \"sub_button\":[\n" +
               "           {    \n" +
               "               \"type\":\"view\",\n" +
               "               \"name\":\"搜索\",\n" +
               "               \"url\":\"http://www.soso.com/\"\n" +
               "            },\n" +
               "            {\n" +
               "                 \"type\":\"scancode_push\",\n" +
               "                 \"name\":\"扫码推事件\",\n" +
               "                 \"key\":\"scancode_push\"\n"+
               "             },\n" +
               "            {\n" +
               "               \"type\":\"click\",\n" +
               "               \"name\":\"赞一下我们\",\n" +
               "               \"key\":\"V1001_GOOD\"\n" +
               "            }]\n" +
               "       }]\n" +
               " }");
        System.out.println(str);
    }
}
