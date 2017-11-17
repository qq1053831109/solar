package org.solar.generator;

public class JdbcTypeMap {
    public static   String getJdbcTypeByJavaType(String javaType){
        if ("String".equals(javaType)){
            return "VARCHAR";
        }
        if ("Date".equals(javaType)){
            return "TIMESTAMP";
        }
        if ("Timestamp".equals(javaType)){
            return "TIMESTAMP";
        }
        if ("Integer".equals(javaType)){
            return "INTEGER";
        }
        if ("Long".equals(javaType)){
            return "BIGINT";
        }
        return "VARCHAR";
    }
    public static   String getJavaTypeByJdbcType(String type){
        if ("VARCHAR".equals(type)){
            return "String";
        }
        if ("CHAR".equals(type)){
            return "String";
        }
        if ("MEDIUMTEXT".equals(type)){
            return "String";
        }
        if ("TEXT".equals(type)){
            return "String";
        }
        if ("Date".equals(type)){
            return "Date";
        }
        if ("DATETIME".equals(type)){
            return "Date";
        }
        if ("Timestamp".equals(type)){
            return "Date";
        }
        if ("INT".equals(type)){
            return "Integer";
        }
        if ("BIGINT".equals(type)){
            return "Long";
        }
        return "String";
    }
}
