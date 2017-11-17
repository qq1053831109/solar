package org.solar.util;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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



}
