package org.solar.util;


import org.solar.exception.SolarRuntimeException;

import java.util.Date;

/**
 * Created by xianchuanwu on 2017/9/19.
 */
public class Assert {
    public static void notEmpty(String ...strs) {
        for (String str:strs) {
            if ("".equals(str)||null==str){
                throw new SolarRuntimeException("参数不能为空!");
            }
        }

    }
    public static void notEmpty(Object obj,String name) {
            if (obj==null||"".equals(obj)){
                throw new SolarRuntimeException(name+"不能为空!");
            }

    }

    public static void notNull(Object obj, String info) {
        if (obj==null){
            throw new SolarRuntimeException(info);
        }
    }
}
