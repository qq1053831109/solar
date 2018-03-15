package org.solar.util;

import java.util.List;

public class ListUtils {

    public static boolean isEmpty(List bean) {
        if (bean==null||bean.size()==0){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(List bean) {
        if (bean==null||bean.size()==0){
            return false;
        }
        return true;
    }
    public static String toString(List bean,String separator) {
        if (bean==null){
            return null;
        }
        if (bean.size()==0){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <bean.size() ; i++) {
            sb.append("'");
            sb.append(bean.get(i));
            sb.append("'");
            if (i<(bean.size()-1)){
                sb.append(separator);
            }
        }
        return sb.toString();
    }
    public static String toString(List bean) {
        return toString(  bean,  ",");
    }

}
