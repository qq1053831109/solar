package org.solar.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static double divide(String var1,String var2,int scale) {
        return new BigDecimal(var1).divide(new BigDecimal(var2),scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double divide(String var1,String var2) {
        return divide(var1,var2,4);
    }

}
