package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.ELUtil;
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
public class ApiHtmlDoc {
    private String template;
    private String host;
    private List<Table> tableList;
    public ApiHtmlDoc(List<Table> tableList, String template ) {
        this.tableList = tableList;
        this.template = template;
    }
    public ApiHtmlDoc(List<Table> tableList, String template, String host) {
        this.tableList = tableList;
        this.template = template;
        this.host = host;
    }


    public String toString() {
        Map map=new HashMap();
        map.put("date",DateUtil.format(new Date(), "yyyy年MM月dd日 HH:MM"));
        map.put("host",host);
        try {
            map.put("apiList",getApiListHtml());
            map.put("apiNavList",getApiNavListHtml());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ELUtil.replace(template,map);
    }
    public String getApiListHtml() throws Exception {
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < tableList.size(); i++) {
            String api=getApiHtml(tableList.get(i));
            sb.append(api);
        }
        return sb.toString();
    }
    public String getApiNavListHtml() throws Exception {
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < tableList.size(); i++) {
            Table table=tableList.get(i);
            sb.append("                        <li>\n");
            sb.append("                            <a  href=\"#"+table.getCamelName()+"\">"+(i+1)+"、 "+table.getCommentInOneLine()+"</a>\n");
            sb.append("                        </li>\n");
            sb.append("");
            sb.append("");
        }
        return sb.toString();
    }
    public String getApiHtml(Table table) throws Exception {
        List<TableField> tableFieldList=table.getTableFields();
        StringBuilder sb=new StringBuilder();
        String tableCamelName=table.getCamelName();
        sb.append("        <div class=\"api\" id=\""+tableCamelName+"\">\n");
        sb.append("             <h3>"+table.getCommentInOneLine()+"</h3>");
        sb.append("             <h6><a  href=\"\" onclick=\"openUri('/"+tableCamelName+"/select?pageNum=1');\" >查询接口</a> host+\"/"+tableCamelName+"/select\"</h6>\n");
        sb.append("             <h6>删除接口 host+\"/"+tableCamelName+"/delete\"</h3>\n");
        sb.append("             <h6>保存或修改接口 host+\"/"+tableCamelName+"/saveOrUpdate\"</h3>\n");
        sb.append("             <table>\n");
        sb.append("                 <tr>\n");
        sb.append("                     <td>参数名</td>\n");
        sb.append("                     <td>类型</td>\n");
        sb.append("                     <td>备注</td>\n");
        sb.append("                 </tr>\n");
        sb.append("                 <tr>\n");
        sb.append("                     <td>id</td>\n");
        sb.append("                     <td>String</td>\n");
        sb.append("                     <td>数据唯一id</td>\n");
        sb.append("                 </tr>\n");
        for (int i = 0; i <tableFieldList.size() ; i++) {
            TableField tableField=tableFieldList.get(i);
        sb.append("                 <tr>\n");
        sb.append("                     <td>"+tableField.getCamelName()+"</td>\n");
        sb.append("                     <td>"+tableField.getJAVAType()+"</td>\n");
        sb.append("                     <td>"+tableField.getCommentInOneLine()+"</td>\n");
        sb.append("                 </tr>\n");
        }
        sb.append("             </table>\n");
        sb.append("        </div>\n");
        return sb.toString();
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


}
