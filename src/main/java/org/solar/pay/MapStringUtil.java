package org.solar.pay;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by xianchuanwu on 05/05/2017.
 */
public class MapStringUtil {
    //把map表单转换为url参数形式
    public static String getUrlStringByMap (Map<String, String> sortedParams) {
            StringBuffer content = new StringBuffer();
            ArrayList keys = new ArrayList(sortedParams.keySet());
            Collections.sort(keys);
            int index = 0;

            for (int i = 0; i < keys.size(); ++i) {
                String key = (String) keys.get(i);
                String value = (String) sortedParams.get(key);
                if (StringUtils.areNotEmpty(new String[]{key, value})) {
                    content.append((index == 0 ? "" : "&") + key + "=" + value);
                    ++index;
                }
            }
            return content.toString();
        }
}
