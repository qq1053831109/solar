package org.solar.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtil {
    public  static Map parseXml(String xmlString){
        Map map = new HashMap();
        Element element = null;
        DocumentBuilder db = null; // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
        DocumentBuilderFactory dbf = null;
        try {
            dbf = DocumentBuilderFactory.newInstance(); // 返回documentBuilderFactory对象
            db = dbf.newDocumentBuilder();// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
            Document dt = db.parse(new InputSource(new StringReader(xmlString))); // 得到一个DOM并返回给document对象
            element = dt.getDocumentElement();// 得到一个elment根元素
            NodeList childNodes = element.getChildNodes(); // 获得根元素下的子节点
            for (int i = 0; i < childNodes.getLength(); i++) // 遍历这些子节点
            {
                Node node = childNodes.item(i); // childNodes.item(i);
                map.put(node.getNodeName(),nodeToJavaObject(node));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Object nodeToJavaObject(Node node) {
        // 获得每个对应位置i的结点
        if (node.getChildNodes().getLength()>1){
            Map childNodeMap=new HashMap();
            NodeList childNodes =node.getChildNodes();
            for (int j = 0; j <childNodes.getLength(); j++) {
                Node node2 = childNodes.item(j);
                childNodeMap.put(node2.getNodeName(),nodeToJavaObject(node2));

            }
            return childNodeMap;
        }else {
            return node.getTextContent();
        }
    }
    /**
     * 只有一级节点
     * @param
     * @return
     */
    public  static String toXml(Map map){
        StringBuilder xmlString=new StringBuilder();
        xmlString.append("<xml>");
        Set<Map.Entry> set=map.entrySet();
        for (Map.Entry entry:set) {
            xmlString.append("<"+entry.getKey()+"><![CDATA[");
            xmlString.append(entry.getValue());
            xmlString.append("]]></"+entry.getKey()+">");
        }
        xmlString.append("</xml>");

        return xmlString.toString();

    }
}
