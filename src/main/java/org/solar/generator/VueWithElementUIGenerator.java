package org.solar.generator;

import org.solar.util.ELUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class VueWithElementUIGenerator {
    private final String template;
    private List<Table> tableList;

    public VueWithElementUIGenerator(List<Table> tableList, String template) {
        this.tableList = tableList;
        this.template = template;
    }

    //分页列表的限制字段名
    private static boolean isPageDispalyLimit(String fieldName) {
        if (fieldName.endsWith("Id") || fieldName.endsWith("Url")
                || fieldName.endsWith("Info")
                ) {
            return true;
        }
        if ("dr".equals(fieldName) || "updator".equals(fieldName)
                || "fromUrl".equals(fieldName)
                ) {
            return true;
        }


        return false;
    }

    public String getFieldListHtml(List<TableField> tableFieldList) {
        StringBuilder sb = new StringBuilder();
        for (TableField tableField : tableFieldList) {
            String jdbcType = tableField.getType();
            Integer size = tableField.getSize();
            String fieldName = tableField.getCamelName();
            String comment = tableField.getComment();
            if (comment == null) {
                comment = fieldName;
            }
            if ("dr".equals(fieldName)) {
                continue;
            }
            sb.append("                    <el-form-item label=\""+comment+"\" prop=\""+fieldName+"\">\n");

            if ("TEXT".equals(jdbcType) || size >= 1000) {
                sb.append("                        <el-input type=\"textarea\" :rows=\"5\" v-model=\"bean."+fieldName+"\"></el-input>\n");
            } else if (jdbcType.startsWith("DATE") || jdbcType.startsWith("TIMESTAMP")) {
                sb.append("                        <el-date-picker placeholder=\"选择日期\" type=\"date\"\n");
                sb.append("                                        v-model=\"bean."+fieldName+"\">\n");
                sb.append("                        </el-date-picker>\n");
            } else {
                sb.append("                        <el-input v-model=\"bean."+fieldName+"\"></el-input>\n");
            }
            sb.append("                    </el-form-item>");
        }
        return sb.toString();
    }

    /*
                        <el-table-column
                            sortable="custom"
                            prop="loginName"
                            label="登录名">
                    </el-table-column>
     */
    public String getPageFieldList(List<TableField> tableFieldList) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (TableField tableField : tableFieldList) {
            String jdbcType = tableField.getType();
            String javaType = tableField.getJAVAType();
            Integer size = tableField.getSize();
            String fieldName = tableField.getCamelName();
            if (isPageDispalyLimit(fieldName)) {
                continue;
            }
            if (fieldName.equals("name")) {
            } else if ("MEDIUMTEXT".equals(jdbcType) || "TEXT".equals(jdbcType) || size > 100) {
                continue;
            }
            String comment = tableField.getComment();
            if (comment == null) {
                comment = fieldName;
            }
            if ("Date".equals(javaType)) {
                sb.append("                    <el-table-column sortable=\"custom\" min-width=\"70px\" :formatter=\"dateFormat\"\n");
                sb.append("                        label=\""+comment+"\"\n");
                sb.append("                        prop=\""+fieldName+"\">\n");
                sb.append("                    </el-table-column>\n");
            } else {
                sb.append("                    <el-table-column sortable=\"custom\" min-width=\"150px\"\n");
                sb.append("                        label=\""+comment+"\"\n");
                sb.append("                        prop=\""+fieldName+"\">\n");
                sb.append("                    </el-table-column>\n");
            }
        }
        return sb.toString();
    }


    public String toString(Table table) {
        String beanName = table.getCamelName();

        String fieldList = getFieldListHtml(table.getTableFields());
        String pageFieldList = getPageFieldList(table.getTableFields());
        Map map = new HashMap();
        map.put("fieldList", fieldList);
        map.put("pageFieldList", pageFieldList);
        map.put("beanName", beanName);
        map.put("beanComment", table.getCommentInOneLine());
        return ELUtil.replace(template, map);
    }


}
