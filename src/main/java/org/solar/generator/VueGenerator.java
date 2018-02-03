package org.solar.generator;

import org.solar.util.ELUtil;
import org.solar.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class VueGenerator {
    private final String template;
    private List<Table> tableList;
    public VueGenerator(List<Table> tableList, String template ) {
        this.tableList = tableList;
        this.template = template;
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
//                    <tr>
//                    <td>
//                        <div>名称</div>
//                    </td>
//                    <td>
//                        <div><input v-model="bean.name"></div>
//                    </td>
//                </tr>
    public String getFieldListHtml(List<TableField> tableFieldList)  {
        StringBuilder sb=new StringBuilder();
        for(TableField  tableField:tableFieldList){
            String jdbcType=tableField.getType();
            Integer size= tableField.getSize();
            //System.out.println(jdbcType+"========"+size);
            String fieldName=tableField.getCamelName();
            String comment=tableField.getComment();
            if (comment==null){
                comment=fieldName;
            }
            if ("dr".equals(fieldName)){
                continue;
            }
            sb.append("                <tr>\n");
            sb.append("                    <td>\n");
            sb.append("                        <div>"+comment+"</div>\n");
            sb.append("                    </td>\n");
            sb.append("                    <td>\n");
            if ("TEXT".equals(jdbcType)||size>=1000){
                sb.append("                        <div><textarea rows=\"10\" style=\"width: 500px\" v-model=\"bean."+fieldName+"\"/></div>");
            }else if(jdbcType.startsWith("DATE")||jdbcType.startsWith("TIMESTAMP")) {
                sb.append("                        <div><input v-model=\"bean."+fieldName+"\" type=\"date\"/></div>\n");
            }else{
                sb.append("                        <div><input v-model=\"bean."+fieldName+"\"/></div>\n");
            }
            sb.append("                    </td>\n");
            sb.append("                </tr>\n");
        }
        return sb.toString();
    }
    public String getFieldNameHtmlList(List<TableField> tableFieldList)  {
        StringBuilder sb=new StringBuilder();
        for(TableField  tableField:tableFieldList){
            String jdbcType=tableField.getType();
            Integer size= tableField.getSize();
//            String column_name=tableField.getName().toLowerCase();
            String fieldName=tableField.getCamelName();
            if (isPageDispalyLimit(fieldName)){
                continue;
            }
            if (fieldName.equals("name")) {

            } else if ("MEDIUMTEXT".equals(jdbcType) || "TEXT".equals(jdbcType) || size > 100) {
                continue;
            }
            String comment=tableField.getComment();
            if (comment==null){
                comment=fieldName;
            }
            sb.append("\n");
            sb.append("                    <td @click=\"changeOrder('" + fieldName + "')\" style=\"cursor: pointer\">\n");
            sb.append("                        <div style=\"width: 100px\">"+comment+"</div>\n");
            sb.append("                    </td>\n");
        }
        return sb.toString();
    }
    public String getFieldValueHtmlList(List<TableField> tableFieldList)  {
        StringBuilder sb=new StringBuilder();
        boolean isFirst=true;
        for(TableField  tableField:tableFieldList){
            String jdbcType=tableField.getType();
            String javaType= tableField.getJAVAType();
            Integer size= tableField.getSize();
//            String column_name=tableField.getName().toLowerCase();
            String fieldName=tableField.getCamelName();
            if (isPageDispalyLimit(fieldName)){
                continue;
            }
            if (fieldName.equals("name")) {

            } else if ("MEDIUMTEXT".equals(jdbcType) || "TEXT".equals(jdbcType) || size > 100) {
                continue;
            }
            String comment=tableField.getComment();
            if (comment==null){
                comment=fieldName;
            }
            sb.append("\n");
            sb.append("                    <td>");
            if (isFirst){
                if ("Date".equals(javaType)){
                    sb.append("<a href=\"javascript:void(0)\" @click=\"goBeanPage(bean.id)\">{{bean."+fieldName+" | time }}</a>");
                }else {
                    sb.append("<a href=\"javascript:void(0)\" @click=\"goBeanPage(bean.id)\">{{bean."+fieldName+"}}</a>");
                }
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


    public String toString( Table table)  {
        String beanName=table.getCamelName();

        String fieldList=getFieldListHtml(table.getTableFields());
        String fieldNameHtmlList=getFieldNameHtmlList(table.getTableFields());
        String fieldValueHtmlList=getFieldValueHtmlList(table.getTableFields());
        Map map =new HashMap();
        map.put("fieldList",fieldList);
        map.put("fieldNameHtmlList",fieldNameHtmlList);
        map.put("fieldValueHtmlList",fieldValueHtmlList);
        map.put("beanName",beanName);
        map.put("beanComment",table.getCommentInOneLine());
        return ELUtil.replace(template,map);
    }


}
