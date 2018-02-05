package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.ELUtil;
import org.solar.util.StringUtil;
import java.util.*;


/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class BeanGenerator {
    private final String template;
    private String packagePrefix;
    public BeanGenerator( String template,String packagePrefix) {
        this.template = template;
        this.packagePrefix = packagePrefix;
    }
    public String toString(Table table) throws Exception {
        String BeanName = StringUtil.underline2Camel(table.getName().toLowerCase(), false);
        Map map =new HashMap();
        String beanFields=getBeanFields(table.getTableFields());
        String beanFieldsMethods=getBeanFieldsMethods(table.getTableFields());
        map.put("fields",beanFields);
        map.put("fieldsMethods",beanFieldsMethods);
        map.put("BeanName",BeanName);
        map.put("packagePrefix",packagePrefix);
        String date = DateUtil.format(new Date(), "yyyy年MM月dd日 HH:mm");
        map.put("Date",date);
        return ELUtil.replace(template,map);
    }



    public String getBeanFields(List<TableField> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(TableField  tableField:tableFields){
            sb.append("\n");
            String javaType=tableField.getJAVAType();
            String fieldName=tableField.getCamelName();
            Object obj=tableField.getCommentInOneLine();
            if (obj!=null){
                String comment=obj.toString();
                sb.append("    //"+comment+"\n");
            }
            sb.append("    private "+javaType+" "+fieldName+";\n");
        }
        return sb.toString();
    }
    public String getBeanFieldsMethods(List<TableField> tableFields) throws Exception {
        StringBuilder sb=new StringBuilder();
        for(TableField  tableField:tableFields){
            sb.append("\n");
            String javaType=tableField.getJAVAType();
            String fieldName=tableField.getCamelName();
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
