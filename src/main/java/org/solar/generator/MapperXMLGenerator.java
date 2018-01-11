package org.solar.generator;
import org.solar.util.StringUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.solar.generator.Generator.packagePrefix;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class MapperXMLGenerator {


    public static void main(String[] args) throws  Exception {
        new MapperXMLGenerator().start();
    }
    public  void start() throws  Exception {


    }
    public  String getMapperXMLContent(String template,String beanName,Map beanNameMap) throws  Exception {
        Map replaceMap=new  LinkedHashMap();
        replaceMap.put("BeanName",beanName);
        replaceMap.put("BaseResultMap",getBaseResultMap(beanNameMap));
        replaceMap.put("Base_Column_List",getBase_Column_List(beanNameMap));
        replaceMap.put("Base_Column_Value_List",getBase_Column_Value_List(beanNameMap));
        String tableName=StringUtil.camel2Underline(StringUtil.toLowerCaseFirstOne(beanName));
        replaceMap.put("tableName",tableName);
        replaceMap.put("where",getWhere(beanNameMap));
        replaceMap.put("insertTrimList",getInsertTrimList(beanNameMap));
        replaceMap.put("insertTrimValueList",getInsertTrimValueList(beanNameMap));
        replaceMap.put("updateSetList",getUpdateSetList(beanNameMap));
        replaceMap.put("updateNameValueList",getUpdateNameValueList(beanNameMap));
        replaceMap.put("packagePrefix",packagePrefix);
        String mapperXMLContent=replaceTemplateEL(template,replaceMap);
        return mapperXMLContent;

    }
    private String replaceTemplateEL(String template, Map<String, String> map) {
        template= template.replace("${BeanName}", map.get("BeanName"));
        template= template.replace("${BaseResultMap}", map.get("BaseResultMap"));
        template= template.replace("${Base_Column_List}", map.get("Base_Column_List"));
        template= template.replace("${tableName}", map.get("tableName"));
        template= template.replace("${where}", map.get("where"));
        template= template.replace("${insertTrimList}", map.get("insertTrimList"));
        template= template.replace("${insertTrimValueList}", map.get("insertTrimValueList"));
        template= template.replace("${updateSetList}", map.get("updateSetList"));
        template= template.replace("${updateNameValueList}", map.get("updateNameValueList"));
        template= template.replace("${packagePrefix}", map.get("packagePrefix"));
        return template;
    }

    public  Map<String,String> getBeanNameMap(String beanPackage,String beanName) throws  Exception{
        Class beanClass=Class.forName(beanPackage+"."+beanName);
        Field[] fieleArray = beanClass.getDeclaredFields();
        Map map=new  LinkedHashMap();
        for (Field field:fieleArray){
            String fieldName=field.getName();
            String fieldType=field.getType().getSimpleName();
            map.put(fieldName,fieldType);
        }
        map.remove("id");
        return map;
    }
    public  String getBase_Column_List(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        int maxLineLen=100;
        for (String key: keys){
            sb.append(" , "+StringUtil.camel2Underline(key));
            if (sb.length()>maxLineLen){
                sb.append("\n");
                maxLineLen=maxLineLen+100;
            }
        }
        return sb.toString();
    }
    public  String getUpdateNameValueList(Map<String,String> beanNameMap){
        String[] keys=new String[beanNameMap.keySet().size()];
        keys=beanNameMap.keySet().toArray(keys);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            String key=keys[i];
            String  column=StringUtil.camel2Underline(key);
            sb.append("      "+column+" = #{"+key+"}");
            if (i<(keys.length-1)){
                sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public  String getBase_Column_Value_List(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        int maxLineLen=100;
        for (String key: keys){
            sb.append(" , #{"+key+"}");
            if (sb.length()>maxLineLen){
                sb.append("\n");
                maxLineLen=maxLineLen+100;
            }
        }
        return sb.toString();
    }
    public  String getBaseResultMap(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        for (String key: keys){
            String  column=StringUtil.camel2Underline(key);
            String  jdbcType=JdbcTypeMap.getJdbcTypeByJavaType(beanNameMap.get(key));
            sb.append("    ");//前面空格
            sb.append("<result column=\""+column+"\" property=\""+key+"\" jdbcType=\""+jdbcType+"\" />\n");
        }
        return sb.toString();
    }
    public  String getInsertTrimList(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        for (String key: keys){
            String  column=StringUtil.camel2Underline(key);
            sb.append("      <if test=\""+key+" != null\" >\n");
            sb.append("        "+column+",\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getInsertTrimValueList(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        for (String key: keys){
            sb.append("      <if test=\""+key+" != null\" >\n");
            sb.append("        #{"+key+"},\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getUpdateSetList(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder sb=new StringBuilder();
        for (String key: keys){
            String  column=StringUtil.camel2Underline(key);
            sb.append("      <if test=\""+key+" != null\" >\n");
            sb.append("        "+column+" = #{"+key+"},\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getWhere(Map<String,String> beanNameMap){
        Set<String> keys=beanNameMap.keySet();
        StringBuilder where=new StringBuilder();
        StringBuilder fullTextSearch=new StringBuilder();
        fullTextSearch.append("       <if test=\"fullTextSearchValue != null\" >\n");
        fullTextSearch.append("           AND (\n");

        boolean isFirst=true;
        for (String key: keys){
            String  column=StringUtil.camel2Underline(key);
            where.append("      <if test=\""+key+" != null\" >\n");
            where.append("        AND "+column+" = #{"+key+"}\n");
            where.append("      </if>\n");
            String javaType=beanNameMap.get(key);
            //&lt;=<  &gt;=>
            if ("Date".equals(javaType)||"Timestamp".equals(javaType)){
                where.append("      <if test=\"" + key + "Start != null\" >\n");
                where.append("        AND " + column + " &gt;= #{" + key + "Start}\n");
                where.append("      </if>\n");
                //end
                where.append("      <if test=\"" + key + "End != null\" >\n");
                where.append("        AND " + column + " &lt; #{" + key + "End}\n");
                where.append("      </if>\n");
            }
            // fullTextSearch
            if ("content".equals(key) || "dr".equals(key)) {
                continue;//某些字段不加入全文搜索
            }
            if (isFirst){
                fullTextSearch.append("              ");
                isFirst=false;
            }else {
                fullTextSearch.append("           or ");
            }
            fullTextSearch.append(column+" like #{fullTextSearchValue}\n");
        }
        fullTextSearch.append("           )\n");
        fullTextSearch.append("      </if>\n");
        where.append(fullTextSearch);
        return where.toString();
    }

}
