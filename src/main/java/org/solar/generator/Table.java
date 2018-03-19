package org.solar.generator;

import org.solar.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {
    private String name;
    private String comment;
    private List<TableField> tableFields;

    public String getName() {
        return name;
    }

    public String getCamelName(boolean bool) {
        return StringUtil.underline2Camel(name,bool);
    }

    public String getCamelName() {
        return StringUtil.underline2Camel(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        if(StringUtil.isEmpty(comment)){
            return getCamelName();
        }
        return comment;
    }
    public String getCommentInOneLine() {
        String str=comment;
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r|\\r)");
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll("");
        }
        if(StringUtil.isEmpty(str)){
            return getCamelName();
        }
        return str;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<TableField> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<TableField> tableFields) {
        this.tableFields = tableFields;
    }
}
