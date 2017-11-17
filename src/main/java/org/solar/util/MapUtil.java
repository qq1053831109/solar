package org.solar.util;

import java.util.Map;
import java.util.Set;

/**
 * Created by xianchuanwu on 2017/9/23.
 */
public class MapUtil {
    public static void printMap(Map map) {
        if (map==null){
            System.err.println("MapUtil.printMap():map=null!");
        }
          Set<Map.Entry> entrySet=map.entrySet();
          for (Map.Entry entry:entrySet){
                System.out.println(entry.getKey()+"="+entry.getValue());
           }
    }
}
