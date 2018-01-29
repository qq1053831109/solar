package org.solar.util;

import org.solar.bean.JsonResult;
import org.solar.bean.Pageable;
import org.solar.exception.SolarRuntimeException;

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

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
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

            return (T)obj;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new SolarRuntimeException("数据类型错误",e);
        }catch ( Exception e) {
            e.printStackTrace();
            throw new SolarRuntimeException( e);
        }
    }

    public static Map<String, Object> objectToMap(Object... obj) {
        if (obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (int i = 0; i < obj.length; i++) {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj[i].getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    if (key.compareToIgnoreCase("class") == 0) {
                        continue;
                    }
                    Method getter = property.getReadMethod();
                    Object value = getter != null ? getter.invoke(obj[i]) : null;
                    map.put(key, value);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
