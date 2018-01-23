//package org.solar.pay;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//public class XMLToMapUtil {
//	  public static Map<String, String> parseXml(String str){
//	        // 将解析结果存储在HashMap中
//	        Map<String, String> map = new HashMap<String, String>();
//	        // 从request中取得输入流
//	       // InputStream inputStream = request.getInputStream();
//	        // 读取输入流
//
//	        SAXReader reader = new SAXReader();
//		  Document document = null;
//		  try {
//			  document = reader.read(new ByteArrayInputStream(str.getBytes()));
//		  } catch (DocumentException e) {
//			  e.printStackTrace();
//		  }
//		  // 得到xml根元素
//	        Element root = document.getRootElement();
//	        // 得到根元素的所有子节点
//	        @SuppressWarnings("unchecked")
//			List<Element> elementList = root.elements();
//	        // 遍历所有子节点
//	        for (Element e : elementList) {
//	            map.put(e.getName(), e.getText());
//	        }
//	        // 释放资源
////	        inputStream.close();
////	        inputStream = null;
//	        return map;
//	    }
//	  public static Map<String, String> parseXml(InputStream inputStream) throws Exception {
//	        // 将解析结果存储在HashMap中
//	        Map<String, String> map = new HashMap<String, String>();
//	        // 从request中取得输入流
//	       // InputStream inputStream = request.getInputStream();
//	        // 读取输入流
//
//	        SAXReader reader = new SAXReader();
//	        Document document = reader.read(inputStream);
//	        // 得到xml根元素
//	        Element root = document.getRootElement();
//	        // 得到根元素的所有子节点
//	        @SuppressWarnings("unchecked")
//			List<Element> elementList = root.elements();
//	        // 遍历所有子节点
//	        for (Element e : elementList) {
//	            map.put(e.getName(), e.getText());
//	        }
//	        // 释放资源
////	        inputStream.close();
////	        inputStream = null;
//	        return map;
//	    }
//}
