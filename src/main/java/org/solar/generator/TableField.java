package org.solar.generator;

import org.solar.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableField {
    private static List<String> sqlKeyWord=new ArrayList();
    static {
        sqlKeyWord.add("key");
        sqlKeyWord.add("index");
        sqlKeyWord.add("distinct");
        sqlKeyWord.add("form");
        sqlKeyWord.add("table");
        sqlKeyWord.add("between");
        sqlKeyWord.add("except");
        sqlKeyWord.add("null");
        sqlKeyWord.add("union");
        sqlKeyWord.add("intersect");
        sqlKeyWord.add("sum");
        sqlKeyWord.add("count");
        sqlKeyWord.add("max");
        sqlKeyWord.add("min");
        sqlKeyWord.add("group");
        sqlKeyWord.add("limit");
        sqlKeyWord.add("avg");
        sqlKeyWord.add("having");
        sqlKeyWord.add("order");
        sqlKeyWord.add("by");
        sqlKeyWord.add("all");
        sqlKeyWord.add("as");
        sqlKeyWord.add("primary");
        sqlKeyWord.add("database");
        sqlKeyWord.add("where");
        sqlKeyWord.add("value");
        sqlKeyWord.add("values");
        sqlKeyWord.add("foreign");
        sqlKeyWord.add("references");
    }
    private String name;
    private String comment;
    private String type;
    private Integer size;

    public String getName() {
        return name;
    }

    public String getSqlEscapeName() {
        if (sqlKeyWord.contains(name)){
            return "'"+name+"'";
        }
        return name;
    }

    public String getCamelName() {
        return StringUtil.underline2Camel(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentInOneLine() {
        String str=comment;
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r|\\r)");
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll("");
        }
        return str;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public String getJAVAType() {
        return JdbcTypeMap.getJavaTypeByJdbcType(type);
    }

    public String getJdbcType() {
        return JdbcTypeMap.getJdbcTypeByJavaType(getJAVAType());
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
