package org.solar.util;

import org.solar.bean.JsonResult;
import org.solar.bean.Pageable;
import org.solar.exception.SolarRuntimeException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

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

    public static List<Map> objectToMap(List list) {
        if (list==null){
            return null;
        }
        List<Map> listMap=new ArrayList();
        for (Object obj:list) {
            Map map = BeanUtil.objectToMap(obj);
            listMap.add(map);
        }
        return listMap;
    }
    public static List<Map> innerJoin(List li1,List li2) {
        if (li1==null||li2==null){
            return null;
        }
        List list=new ArrayList();
        for (Object obj:li1){
            if (li2.contains(obj)){
                list.add(obj);
            }
        }

        return list;
    }
    public static List<Map> innerJoin(List li1,List li2,String name1,String name2) {
        if (li1==null||li2==null){
            return null;
        }
        li1=objectToMap(li1);
        li2=objectToMap(li2);
        List list=new ArrayList();
        for (Object obj1:li1){
            Object kv1=((Map)obj1).get(name1);
            if (kv1==null){
                continue;
            }
            for (Object obj2:li2){
                Object kv2=((Map)obj2).get(name2);
                if (kv1.equals(kv2)){
                    Map temp=new HashMap();
                    temp.putAll((Map)obj2);
                    temp.putAll((Map)obj1);
                    list.add(temp);
                }

            }
        }
        return list;
    }
    public static List<Map> leftJoin(List li1,List li2,String name1,String name2) {
        return leftJoin(  li1,  li2,  name1,  name2,null);
    }
    public static List<Map> leftJoin(List li1,List li2,String name1,String name2,Object defult) {
        if (li1==null){
            return null;
        }
        if (li2==null){
            return objectToMap(li1);
        }
        li1=objectToMap(li1);
        li2=objectToMap(li2);
        for (Object obj:li1) {
            Map map=(Map)obj;
            Object val=map.get(name1);
            //添加默认对象
            if (defult!=null){
                if (name1.endsWith("Id")){
                    map.put(name1.substring(0,name1.length()-2),defult);
                }else {
                    map.put(name1+"Object",defult);
                }
            }
            if (val==null){
                continue;
            }
            for (Object obj2:li2) {
                Map map2=(Map)obj2;
                Object val2=map2.get(name2);
                if (val.equals(val2)){
                    if (defult==null&&map2!=null){
                        Map tempMap=new HashMap();
                        tempMap.putAll(map2);
                        tempMap.putAll(map);
                        map.clear();
                        map.putAll(tempMap);
                    }else if (name1.endsWith("Id")){
                        map.put(name1.substring(0,name1.length()-2),map2);
                    }else {
                        map.put(name1+"Object",map2);
                    }

                }
            }
        }
        return li1;
    }
    public static List getProperties(List li,String name) {
        if (li==null){
            return null;
        }
        List result=new ArrayList();
        for (Object obj:li) {
            Map map=objectToMap(obj);
            result.add(map.get(name));
        }
        return result;
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
        if (obj.length==1&&obj[0]instanceof Map){
            return (Map)obj[0];
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (int i = 0; i < obj.length; i++) {
                if (obj[i]==null){
                    continue;
                }
                if (obj[i]instanceof Map){
                    map.putAll((Map)obj[i]);
                    continue;
                }
                BeanInfo beanInfo = Introspector.getBeanInfo(obj[i].getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    //javabean规范变量的前两个字母要么全部大写，要么全部小写（暂时不遵循javabean规范）
                    key=StringUtil.toLowerCaseFirstOne(key);
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
