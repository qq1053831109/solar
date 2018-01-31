package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.PropertiesUtil;
import org.solar.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class DataBaseFieldDictGenerator {
    private List<Table> tableList;
    private String  dataBaseFieldDictPath;
    public DataBaseFieldDictGenerator(List<Table> tableList,String  dataBaseFieldDictPath) {
        this.tableList = tableList;
        this.dataBaseFieldDictPath = dataBaseFieldDictPath;
    }


    public void generatorPropertiesFile() throws Exception {
        File file=new File(dataBaseFieldDictPath);
        if (!file.exists()){
            file.createNewFile();
        }
        Properties properties= PropertiesUtil.getProperty("file:"+dataBaseFieldDictPath);
        FileOutputStream fos = new FileOutputStream(file);
        for (Table table : tableList) {
            List<TableField> tableFieldList = table.getTableFields();
            for (TableField tableField:tableFieldList){
                properties.put(tableField.getName(),tableField.getCommentInOneLine());
            }
        }
        List keySet=new ArrayList();
        keySet.addAll(properties.keySet());
        Collections.sort(keySet);
        StringBuffer fileContent=new StringBuffer();
        for (Object key:keySet){
            fileContent.append(key);
            fileContent.append("=");
            String comment=(String)properties.get(key);
            if (comment==null){
                comment="";
            }else {
                comment=comment.replace("\r","");
                comment=comment.replace("\n","");
            }
            fileContent.append(comment);
            fileContent.append("\r\n");
        }
        fos.write(fileContent.toString().getBytes("utf-8"));
        fos.flush();
        fos.close();
        System.out.println("生成 " + file.getName() + " 成功!");
    }




}
