package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.solar.generator.Generator.packagePrefix;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class BeanGenerator {
    public String jdbcUrl;
    public static String driverName;
    public static String tableName="%";
    public Connection connection ;
    //2. 下面就是获取表的信息。
    public DatabaseMetaData metaData ;
    public BeanGenerator(String jdbcUrl) {

          this( jdbcUrl,"com.mysql.jdbc.Driver");
    }
    public BeanGenerator(String jdbcUrl,String driverName) {
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

    public String getBeanFileContent(String template, List<Map> tableFields, String BeanName) throws Exception {
        String beanFields=getBeanFields(tableFields);
        String beanFieldsMethods=getBeanFieldsMethods(tableFields);
        template=template.replace("${fields}",beanFields);
        template=template.replace("${fieldsMethods}",beanFieldsMethods);
        template=template.replace("${BeanName}",BeanName);
        template=template.replace("${packagePrefix}",packagePrefix);
        String date = DateUtil.format(new Date(), "yyyy年MM月dd日 HH:MM");
        template=template.replace("${Date}",date);
        return  template;
    }

    /**
     * wxc
     */
    public static String conver(String str){
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r|\\r)");
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll("");
        }
        return str;
    }
    public String getBeanFields(List<Map> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(Map  tableField:tableFields){
            sb.append("\n");
            String javaType=tableField.get("type").toString();
            javaType= JdbcTypeMap.getJavaTypeByJdbcType(javaType);
            String fieldName=tableField.get("name").toString().toLowerCase();
            fieldName=StringUtil.underline2Camel(fieldName,true);
            Object obj=tableField.get("comment");
            if (obj!=null){
                String comment=obj.toString();
                comment=conver(comment);
                sb.append("    //"+comment+"\n");
            }
            sb.append("    private "+javaType+" "+fieldName+";\n");
        }
        return sb.toString();
    }
    public String getBeanFieldsMethods(List<Map> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(Map  tableField:tableFields){
            sb.append("\n");
            String javaType=tableField.get("type").toString();
            javaType= JdbcTypeMap.getJavaTypeByJdbcType(javaType);
            String fieldName=tableField.get("name").toString().toLowerCase();
            fieldName=StringUtil.underline2Camel(fieldName,true);
            String FieldName=StringUtil.toUpperCaseFirstOne(fieldName);

            sb.append("    public "+javaType+" get"+FieldName+"() {\n");
            sb.append("        return "+fieldName+";\n");
            sb.append("    }\n");
            sb.append("\n");
            sb.append("    public void set"+FieldName+"("+javaType+" "+fieldName+") {\n");
            sb.append("        this."+fieldName+" = "+fieldName+";\n");
            sb.append("    }\n");
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
    public List<Map> getTableFields(String tableName) throws Exception{
        List list=new ArrayList();
        //4. 提取表内的字段的名字和类型
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        while (colRet.next()) {
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            byte[] commentBytes = colRet.getBytes("REMARKS");
            String comment = colRet.getString("REMARKS");
            int size= colRet.getInt("COLUMN_SIZE");
            if ("id".equals(name)){
                continue;
            }
            Map map =new  LinkedHashMap();
            map.put("name",name);
            map.put("type",type);
//            System.out.println(comment);
            if (comment!=null){
                map.put("comment",comment);
            }
            map.put("size",size);
            list.add(map);
        }
        return  list;
    }

}
