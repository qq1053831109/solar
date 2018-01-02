package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.PropertiesUtil;
import org.solar.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
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
    public String jdbcUrl;
    public static String driverName;
    public static String tableName = "%";
    public Connection connection;
    //2. 下面就是获取表的信息。
    public DatabaseMetaData metaData;
    public String dataBaseFieldDictPath;

    public DataBaseFieldDictGenerator(String jdbcUrl) {
        this(jdbcUrl, "com.mysql.jdbc.Driver");
    }
    public DataBaseFieldDictGenerator(String jdbcUrl, String driverName) {
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


    public void generatorDataBaseFieldDict() throws Exception {
        File file=new File(dataBaseFieldDictPath);
        if (!file.exists()){
            file.createNewFile();
        }
        Properties properties=PropertiesUtil.getProperty("file:"+dataBaseFieldDictPath);
        FileOutputStream fos = new FileOutputStream(file);
        List<String> tableNames = getTableNames();
        for (String tableName : tableNames) {
                List<Map> fieldList = getTableFields(tableName);
                for (Map fieldInfoMap:fieldList){
                    String comment=(String)fieldInfoMap.get("comment");
                    String name=(String)fieldInfoMap.get("name");
                    properties.put(name,comment);
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


    public List getTableNames() throws Exception {
        ResultSet tableRet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        List list = new ArrayList();
        while (tableRet.next()) {
            list.add(tableRet.getString("TABLE_NAME"));
        }
        return list;
    }

    public List<Map> getTableFields(String tableName) throws Exception {
        List list = new ArrayList();
        //4. 提取表内的字段的名字和类型
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        while (colRet.next()) {
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            String comment = colRet.getString("REMARKS");
            int size = colRet.getInt("COLUMN_SIZE");
            if ("id".equals(name)) {
                continue;
            }
            Map map = new LinkedHashMap();
            map.put("name", name);
            map.put("type", type);
            if (comment != null) {
                map.put("comment", comment);
            }
            map.put("size", size);
            list.add(map);
        }
        return list;
    }

}
