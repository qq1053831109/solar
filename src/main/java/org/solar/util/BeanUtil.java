package org.solar.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xianchuanwu on 2017/9/23.
 */
public class BeanUtil {
    public static void printMap(Map map) {
        if (map == null) {
            System.err.println("BeanUtil.printMap():map=null!");
        }
        Set<Map.Entry> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)     {
        if (map == null)
            return null;
        try {
            Object obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
