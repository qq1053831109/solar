package org.solar.generator;

import org.solar.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableField {
    private String name;
    private String comment;
    private String type;
    private Integer size;

    public String getName() {
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
