package org.solar.generator;
import org.solar.util.ELUtil;
import org.solar.util.StringUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.solar.generator.Generator.packagePrefix;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class MapperXMLGenerator {
    public   boolean fullTextSearchContainDate=true;
    public   boolean whereContainWherein=true;

    private final String template;
    private String packagePrefix;
    private List<Table> tableList;
    public MapperXMLGenerator(List<Table> tableList, String template, String packagePrefix ) {
        this.tableList = tableList;
        this.template = template;
        this.packagePrefix = packagePrefix;
    }
    public  String toString(Table table){
        Map replaceMap=new  LinkedHashMap();
        List<TableField> tableFieldList=table.getTableFields();
        replaceMap.put("BeanName",table.getCamelName(false));
        replaceMap.put("BaseResultMap",getBaseResultMap(tableFieldList));
        replaceMap.put("Base_Column_List",getBase_Column_List(tableFieldList));
        replaceMap.put("Base_Column_Value_List",getBase_Column_Value_List(tableFieldList));
        replaceMap.put("tableName",table.getName());
        replaceMap.put("where",getWhere(tableFieldList));
        replaceMap.put("insertTrimList",getInsertTrimList(tableFieldList));
        replaceMap.put("insertTrimValueList",getInsertTrimValueList(tableFieldList));
        replaceMap.put("updateSetList",getUpdateSetList(tableFieldList));
        replaceMap.put("updateNameValueList",getUpdateNameValueList(tableFieldList));
        replaceMap.put("packagePrefix",packagePrefix);
        String mapperXMLContent= ELUtil.replace(template,replaceMap);
        return mapperXMLContent;

    }




    public  String getBase_Column_List(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        int maxLineLen=100;
        for (TableField tableField: tableFieldList){
            sb.append(" , "+tableField.getSqlEscapeName());
            if (sb.length()>maxLineLen){
                sb.append("\n");
                maxLineLen=maxLineLen+100;
            }
        }
        return sb.toString();
    }
    public  String getUpdateNameValueList(List<TableField> tableFieldList){
        String[] keys=new String[tableFieldList.size()];
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < tableFieldList.size(); i++) {
            TableField tableField=tableFieldList.get(i);
            String  column=tableField.getSqlEscapeName();
            sb.append("      "+column+" = #{"+tableField.getCamelName()+"}");
            if (i<(keys.length-1)){
                sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public  String getBase_Column_Value_List(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        int maxLineLen=100;
        for (TableField tableField: tableFieldList){
            sb.append(" , #{"+tableField.getCamelName()+"}");
            if (sb.length()>maxLineLen){
                sb.append("\n");
                maxLineLen=maxLineLen+100;
            }
        }
        return sb.toString();
    }
    public  String getBaseResultMap(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        for (TableField tableField: tableFieldList){
            String  jdbcType=tableField.getJdbcType();
            sb.append("    ");//前面空格
            sb.append("<result column=\""+tableField.getName()+"\" property=\""+tableField.getCamelName()+"\" jdbcType=\""+jdbcType+"\" />\n");
        }
        return sb.toString();
    }
    public  String getInsertTrimList(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        for (TableField tableField: tableFieldList){
            sb.append("      <if test=\""+tableField.getCamelName()+" != null\" >\n");
            sb.append("        "+tableField.getSqlEscapeName()+",\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getInsertTrimValueList(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        for (TableField tableField: tableFieldList){
            sb.append("      <if test=\""+tableField.getCamelName()+" != null\" >\n");
            sb.append("        #{"+tableField.getCamelName()+"},\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getUpdateSetList(List<TableField> tableFieldList){
        StringBuilder sb=new StringBuilder();
        for (TableField tableField: tableFieldList){
            sb.append("      <if test=\""+tableField.getCamelName()+" != null\" >\n");
            sb.append("        "+tableField.getSqlEscapeName()+" = #{"+tableField.getCamelName()+"},\n");
            sb.append("      </if>\n");
        }
        return sb.toString();
    }
    public  String getWhere(List<TableField> tableFieldList){
        StringBuilder where=new StringBuilder();
        StringBuilder fullTextSearch=new StringBuilder();
        fullTextSearch.append("       <if test=\"fullTextSearchValue != null\" >\n");
        fullTextSearch.append("           AND (\n");

        boolean isFirst=true;
        for (TableField tableField: tableFieldList){
            String  column=tableField.getSqlEscapeName();
            String  key=tableField.getCamelName();
            where.append("      <if test=\""+key+" != null\" >\n");
            where.append("        AND "+column+" = #{"+key+"}\n");
            where.append("      </if>\n");
            String javaType=tableField.getJAVAType();
            //&lt;=<  &gt;=>
            if ("Date".equals(javaType)||"Timestamp".equals(javaType)){
                where.append("      <if test=\"" + key + "Start != null and "+key+"Start != ''\" >\n");
                where.append("        AND " + column + " &gt;= #{" + key + "Start}\n");
                where.append("      </if>\n");
                //end
                where.append("      <if test=\"" + key + "End != null and "+key+"End != ''\" >\n");
                where.append("        AND " + column + " &lt; #{" + key + "End}\n");
                where.append("      </if>\n");
                if (!fullTextSearchContainDate){
                    continue;
                }
            }
            // fullTextSearch
            if ("content".equals(key) || "dr".equals(key)|| "html".equals(key)) {
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
        if (whereContainWherein){
            where.append("      <if test=\"idList != null\" >\n");
            where.append("        AND id in\n");
            where.append("        <foreach collection=\"idList\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n");
            where.append("            #{item}\n");
            where.append("        </foreach>\n");
            where.append("      </if>\n");
            where.append(getWhereInColumnListKeyList(tableFieldList));
        }
        where.append(fullTextSearch);
        return where.toString();
    }
    public  String getWhereInColumnListKeyList(List<TableField> tableFieldList){
        StringBuilder where=new StringBuilder();

        for (TableField tableField: tableFieldList){
            String  column=tableField.getSqlEscapeName();
            String  key=tableField.getCamelName();
            where.append("      <if test=\""+key+"List != null\" >\n");
            where.append("        AND "+column+" in\n");
            where.append("        <foreach collection=\""+key+"List\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n");
            where.append("            #{item}\n");
            where.append("        </foreach>\n");
            where.append("      </if>\n");


        }
        return where.toString();
    }

}
