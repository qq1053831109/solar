package org.solar.wechat;

import org.solar.util.XMLUtil;

import java.util.HashMap;
import java.util.Map;

public class WeChatPublicServiceListener {

    /**
     CreateTime=1516947697
     EventKey=
     Event=subscribe
     ToUserName=gh_87e09a2ad8fa
     FromUserName=ojNbawHeaQSSJMH024-MF78m6GOA
     MsgType=event
     * @param map
     */
    public  String onSubscribe(Map map) {
        Map result=new HashMap();
        result.put("ToUserName",map.get("FromUserName"));
        result.put("FromUserName",map.get("ToUserName"));
        result.put("CreateTime",System.currentTimeMillis()/1000);
        result.put("MsgType","text");
        result.put("Content","终于等到你了!感谢关注!");
        return XMLUtil.toXml(result);
    }


    /**
     Status=success
     CreateTime=1516947540
     Event=TEMPLATESENDJOBFINISH
     ToUserName=gh_87e09a2ad8fa
     FromUserName=ojNbawHeaQSSJMH024-MF78m6GOA
     MsgType=event
     MsgID=123400940492570624
     * @param map
     */
    public  String onTEMPLATESENDJOBFINISH(Map map) {
        return null;
    }


    /**
     Content=111
     CreateTime=1516950350
     ToUserName=gh_87e09a2ad8fa
     FromUserName=ojNbawHeaQSSJMH024-MF78m6GOA
     MsgType=text
     MsgId=6515252143325744514
     * @param map
     */
    public  String onMsg(Map<String,String> map) {
        String msgType=map.get("MsgType");
        switch (msgType){
            case "event":
                return onEvent(map);
            case "text":
                return onText(map);
        }

        return null;
    }
    private  String onEvent(Map<String,String> map) {
        String event=map.get("Event");
        switch (event){
            case "TEMPLATESENDJOBFINISH":
                return onTEMPLATESENDJOBFINISH(map);
            case "subscribe":
                return onSubscribe(map);
        }

        return null;
    }
    /**
     Content=111
     CreateTime=1516950350
     ToUserName=gh_87e09a2ad8fa
     FromUserName=ojNbawHeaQSSJMH024-MF78m6GOA
     MsgType=text
     MsgId=6515252143325744514
     * @param map
     */
    public  String onText(Map<String,String> map) {
        Map result=new HashMap();
        result.put("ToUserName",map.get("FromUserName"));
        result.put("FromUserName",map.get("ToUserName"));
        result.put("CreateTime",System.currentTimeMillis()/1000+60*60*24);
        result.put("MsgType","text");
        String content="你的消息:\r\n";
          content=content+map.get("Content");
          content=content+"\r\n";
          content=content+"已收到!";
        result.put("Content",content);
        return XMLUtil.toXml(result);
    }
}
