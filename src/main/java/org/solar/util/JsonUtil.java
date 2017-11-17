package org.solar.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * Created by xianchuanwu on 2017/9/23.
 */
public class JsonUtil {
    public static void main(String[] args) {

    }

    public static <T> T toJavaObject(Map map, Class<T> clazz) {
        return JSONObject.toJavaObject(new JSONObject(map), clazz);
    }

    public static String toJSONString(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public static String toJSONString(Object obj, SerializerFeature... features) {
        return JSONObject.toJSONString(obj, features);
    }

    public static Map parseObject(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }

    public static List parseArray(String jsonStr) {
        return JSONObject.parseArray(jsonStr);
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }

}
