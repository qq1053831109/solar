package org.solar.util;


import org.solar.bean.Tree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeUtil {

    /**
     * 获取根:可能含有多个根
     *
     * @param <T>
     * @return
     */
    public static <T> List<T> getRoot(List<T> list) {
        try {
            if (list != null && !list.isEmpty()) {
                List returnList = new ArrayList();
                for (T t : list) {
                    //获取Bean/VO的parentId
                    Method method = t.getClass().getMethod("getParentId");
                    String pid = (String) method.invoke(t);
                    if (pid == null || "".equals(pid)) {
                        returnList.add(t);
                    }
                }
                return returnList;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递归方式。效率是个问题啊！
     *
     * @param list
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T getChild(List<T> list, T obj) {
        try {
            if (list != null && !list.isEmpty() && obj != null) {
                for (T t : list) {
                    Method pid = t.getClass().getMethod("getParentId");
                    String parentID = (String) pid.invoke(t);
                    String objID = (String) obj.getClass().getMethod("getId").invoke(obj);
                    if (objID.equals(parentID)) {  //当前ID 与PID相同
                        Method setNodes = obj.getClass().getMethod("addChildren", obj.getClass());
                        setNodes.invoke(obj, (Object) t);
                        getChild(list, t);
                    }
                }
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map> convertToTreeByParentId(List list) {
        List<Map> listMap=new ArrayList();
        for (Object obj:list) {
            Map map = BeanUtil.objectToMap(obj);
            listMap.add(map);
        }
        List treeList = new ArrayList();
        Iterator<Map> iterator=listMap.iterator();
        while (iterator.hasNext()){
            Map map=iterator.next();
            String parentId = (String) map.get("parentId");
            if (parentId == null || "".equals(parentId)) {
                treeList.add(map);
                List childList= getChildListByParentId(listMap, (String) map.get("id"));
                if (childList!=null&&childList.size()>0){
                    map.put("childList", childList);
                }
            }
        }
        return treeList;
    }

    public static List<Map> getChildListByParentId(List<Map> list, String id) {
        List childList = new ArrayList();
        Iterator<Map> iterator=list.iterator();
        while (iterator.hasNext()){
            Map map=iterator.next();
            String parentId = (String) map.get("parentId");
            if (id.equals(parentId)) {
                childList.add(map);
                List childList2= getChildListByParentId(list, (String) map.get("id"));
                if (childList2!=null&&childList2.size()>0){
                    map.put("childList", childList2);
                }
            }
        }
        return childList;
    }

    public static void main(String[] args) {

    }
}
