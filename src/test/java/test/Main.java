package test;

import org.solar.util.BeanUtil;
import org.solar.util.XMLUtil;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String xmlStr="<xml><Content><![CDATA[111]]></Content><CreateTime><![CDATA[1516950350]]></CreateTime><ToUserName><![CDATA[gh_87e09a2ad8fa]]></ToUserName><FromUserName><![CDATA[ojNbawHeaQSSJMH024-MF78m6GOA]]></FromUserName><MsgType><![CDATA[text]]></MsgType><MsgId><![CDATA[6515252143325744514]]></MsgId></xml>";
        Map map= XMLUtil.parseXml(xmlStr);
        BeanUtil.printMap(map);
//        System.out.println(XMLUtil.toXml(map));
    }
}
