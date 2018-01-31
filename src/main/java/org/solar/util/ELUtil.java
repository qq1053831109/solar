package org.solar.util;

import java.util.Map;

public class ELUtil {
    public static String replace(String elString,Map<Object,Object> map) {
        for (Map.Entry<Object,Object> entry:map.entrySet()){
            elString=elString.replace("${"+String.valueOf(entry.getKey())+"}",String.valueOf(entry.getValue()));
        }
        return elString;
    }
}
