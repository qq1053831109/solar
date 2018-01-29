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

    /**
     {"button":[{"name":"????","sub_button":[{"type":"view","name":"????","url":"http://wx.baozhenche.com/wechatLogin?uri=ppxc"},{"type":"view","name":"???","url":"http://wx.baozhenche.com/wechatLogin?uri=yqt"}]},{"type":"view","name":"????","url":"http://wx.baozhenche.com/wechatLogin?uri=zxyh"},{"name":"??","sub_button":[{"type":"view","name":"??","url":"http://wx.baozhenche.com/wechatLogin?uri=dd"},{"type":"view","name":"??","url":"http://wx.baozhenche.com/wechatLogin?uri=rz"},{"type":"click","name":"????","key":"lxkf"},{"type":"click","name":"??","key":"wq"}]}]}

     */
    public static void main(String[] args) {

       String str= weChatPublicService.createMenu("");
       System.out.println(str);
    }
}
