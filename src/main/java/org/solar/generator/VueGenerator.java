package org.solar.generator;

import org.solar.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class VueGenerator {
    public String jdbcUrl;
    public static String driverName;
    public static String tableName="%";
    public Connection connection ;
    //2. 下面就是获取表的信息。
    public DatabaseMetaData metaData ;
    public VueGenerator(String jdbcUrl) {

        this( jdbcUrl,"com.mysql.jdbc.Driver");
    }
    public VueGenerator(String jdbcUrl,String driverName) {
        this.jdbcUrl = jdbcUrl;
        this.driverName = driverName;
        //1. JDBC连接MYSQL的代码很标准。
        try {
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(jdbcUrl);
            //2. 下面就是获取表的信息。
            metaData = connection.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //分页列表的限制字段名
    private static boolean isPageDispalyLimit(String fieldName){
        if (fieldName.endsWith("Id")||fieldName.endsWith("Url")
            ||fieldName.endsWith("Info")
                ){
            return true;
        }
        if ("dr".equals(fieldName)||"updator".equals(fieldName)
            ||"fromUrl".equals(fieldName)
            ){
            return true;
        }


        return false;
    }

    public String getFieldListHtml(List<Map> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(Map  tableField:tableFields){
            String jdbcType=tableField.get("type").toString();
            Integer size= (Integer) tableField.get("size");
            //System.out.println(jdbcType+"========"+size);
            String fieldName=tableField.get("name").toString().toLowerCase();
            fieldName=StringUtil.underline2Camel(fieldName,true);
            Object obj=tableField.get("comment");
            String comment=fieldName;
            if (obj!=null){
                comment=obj.toString();
            }
            if ("dr".equals(fieldName)){
                continue;
            }
            sb.append("\n");
            sb.append("                    <li>\n");
            sb.append("                        <div>"+comment+"</div>\n");
            sb.append("                    </li>\n");
            sb.append("                    <li>\n");
            if ("TEXT".equals(jdbcType)||size>=1000){
                sb.append("                        <div><textarea rows=\"10\" style=\"width: 800px\" v-model=\"bean."+fieldName+"\"/></div>");
            }else {
                sb.append("                        <div><input v-model=\"bean."+fieldName+"\"></div>\n");
            }


            sb.append("                    </li>\n");
        }
        return sb.toString();
    }
    public String getFieldNameHtmlList(List<Map> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(Map  tableField:tableFields){
            String jdbcType=tableField.get("type").toString();
            Integer size= (Integer) tableField.get("size");
            String column_name=tableField.get("name").toString().toLowerCase();
            String fieldName=StringUtil.underline2Camel(column_name,true);
            if (isPageDispalyLimit(fieldName)){
                continue;
            }
            if (column_name.equals("name")) {

            } else if ("MEDIUMTEXT".equals(jdbcType) || "TEXT".equals(jdbcType) || size > 100) {
                continue;
            }
            Object obj=tableField.get("comment");
            String comment=fieldName;
            if (obj!=null){
                comment=obj.toString();
            }
            sb.append("\n");
            sb.append("                    <td @click=\"changeOrder('" + column_name + "')\" style=\"cursor: pointer\">\n");
            sb.append("                        <div style=\"width: 100px\">"+comment+"</div>\n");
            sb.append("                    </td>\n");
        }
        return sb.toString();
    }
    public String getFieldValueHtmlList(List<Map> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        boolean isFirst=true;
        for(Map  tableField:tableFields){
            String jdbcType=tableField.get("type").toString();
            String javaType= JdbcTypeMap.getJavaTypeByJdbcType(jdbcType);
            Integer size= (Integer) tableField.get("size");
            String column_name=tableField.get("name").toString().toLowerCase();
            String fieldName=StringUtil.underline2Camel(column_name,true);
            if (isPageDispalyLimit(fieldName)){
                continue;
            }
            if (column_name.equals("name")) {

            } else if ("MEDIUMTEXT".equals(jdbcType) || "TEXT".equals(jdbcType) || size > 100) {
                continue;
            }
            Object obj=tableField.get("comment");
            String comment=fieldName;
            if (obj!=null){
                comment=obj.toString();
            }
            sb.append("\n");
            sb.append("                    <td>");
            if (isFirst){
                sb.append("<a href=\"javascript:void(0)\" @click=\"goBeanPage(bean.id)\">{{bean."+fieldName+"}}</a>");
                isFirst=false;
            }else {
                if ("Date".equals(javaType)){
                    sb.append("{{bean."+fieldName+" | time }}");
                }else {

                sb.append("{{bean."+fieldName+"}}");

                }
            }
            sb.append("</td>");
        }
        return sb.toString();
    }

    public List getTableNames() throws Exception{
        ResultSet tableRet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        List list=new ArrayList();
        while (tableRet.next()) {
            list.add(tableRet.getString("TABLE_NAME"));
        }
        return  list;
    }
    public String getVueHtmlCode(String template,String tableName) throws Exception{
        String beanName=StringUtil.underline2Camel(tableName.toLowerCase(),true);
        List list=new ArrayList();
        //4. 提取表内的字段的名字和类型
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        while (colRet.next()) {
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            String comment = colRet.getString("REMARKS");
            int size= colRet.getInt("COLUMN_SIZE");
            if ("id".equals(name)){
                continue;
            }
            Map map =new  LinkedHashMap();
            map.put("name",name);
            map.put("type",type);
            if (comment!=null){
                map.put("comment",comment);
            }
            map.put("size",size);
            list.add(map);
        }
        String fieldList=getFieldListHtml(list);
        String fieldNameHtmlList=getFieldNameHtmlList(list);
        String fieldValueHtmlList=getFieldValueHtmlList(list);
        template=template.replace("${fieldList}",fieldList);
        template=template.replace("${fieldNameHtmlList}",fieldNameHtmlList);
        template=template.replace("${fieldValueHtmlList}",fieldValueHtmlList);
        template=template.replace("${beanName}",beanName);
        return  template;
    }
    public String getVuePageHtmlCode(String template,String tableName) throws Exception{
        String beanName=StringUtil.underline2Camel(tableName.toLowerCase(),true);
        List list=new ArrayList();
        //4. 提取表内的字段的名字和类型
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        while (colRet.next()) {
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            String comment = colRet.getString("REMARKS");
            int size= colRet.getInt("COLUMN_SIZE");
            if ("id".equals(name)){
                continue;
            }
            Map map =new  LinkedHashMap();
            map.put("name",name);
            map.put("type",type);
            if (comment!=null){
                map.put("comment",comment);
            }
            map.put("size",size);
            list.add(map);
        }
        String fieldNameHtmlList=getFieldNameHtmlList(list);
        String fieldValueHtmlList=getFieldValueHtmlList(list);
        template=template.replace("${fieldNameHtmlList}",fieldNameHtmlList);
        template=template.replace("${fieldValueHtmlList}",fieldValueHtmlList);
        template=template.replace("${beanName}",beanName);
        return  template;
    }

}
