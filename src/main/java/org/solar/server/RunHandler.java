package org.solar.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 简单的http 运行远程服务器程序！该服务是不安全，切勿用于正式环境
 * 该方法可以做测试环境的自动部署(需要自己写部署脚本，然后通过git服务器提供的push回调)
 */
public class RunHandler implements HttpHandler {

    public void handle(HttpExchange exchange) throws IOException {
        String shPath="/home/shell/test.sh";
        //打印请求信息
        String query = exchange.getRequestURI().getQuery();
//        System.out.println("query " + query);
        Map<String,String> queryMap=new HashMap();
        if (query!=null&&!"".equals(query)){
            String[] nameVals=query.split("&");
            for (String nameVal:nameVals){
                String[] vars=nameVal.split("=");
                queryMap.put(vars[0],vars[1]);
            }
        }
//        Set keySet=queryMap.keySet();
//        for (Object key:keySet){
//            System.out.println(key+"="+queryMap.get(key));
//        }
        //返回响应信息
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, 0);
        OutputStream response = exchange.getResponseBody();
        //业务逻辑
        if (queryMap.containsKey("shPath")){
            shPath=queryMap.get("shPath");
        }
        //密码校验
//        if(!"solar".equals(queryMap.get("key"))){
//            return;
//        }
        try {
            Process process=Runtime.getRuntime().exec(shPath);
            InputStream in=process.getInputStream();
            byte[] bs=new byte[300];
            int len=-1;
            while ((len=in.read(bs))!=-1){
                response.write(bs,0,len);
            }
        } catch (IOException e) {
            response.write(e.getMessage().getBytes());
            e.printStackTrace();
        }
        response.close();
    }
}
