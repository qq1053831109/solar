package org.solar.seo;

import org.solar.util.JdkHttpUtil;

import java.util.ArrayList;
import java.util.List;

public class BaiDuUrlSubmit {
    public static String submitUrls(String site, String token, List<String> urls) {
        if (urls == null) {
            return null;
        }
        String submitUrl = "http://data.zz.baidu.com/urls?site=" + site + "&token=" + token;
        StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(url + "\r\n");
        }
        String result = JdkHttpUtil.post(submitUrl, sb.toString());

        return result;
    }

    public static void main(String[] args) {
        List li = new ArrayList();
        li.add("www.xxx.com/index");
        System.out.println(submitUrls("www.xxx.com", "xxxxx", li));
    }


}
