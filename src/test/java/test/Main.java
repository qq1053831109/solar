package test;

import org.solar.util.BeanUtil;
import org.solar.util.XMLUtil;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String xmlStr="<xml><ToUserName><![CDATA[gh_87e09a2ad8fa]]></ToUserName><FromUserName><![CDATA[ojNbawHeaQSSJMH024-MF78m6GOA]]></FromUserName><CreateTime>1517018854</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[V1001_GOOD]]></EventKey></xml>";
        Map map= XMLUtil.parseXml(xmlStr);
        BeanUtil.printMap(map);
    }
}
