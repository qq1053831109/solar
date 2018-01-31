package org.solar.generator;

import org.solar.util.DateUtil;
import org.solar.util.StringUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.solar.generator.Generator.packagePrefix;

/**
 * Created by xianchuanwu on 2017/9/25.
 */
public class DataBase {
    private  String dataBaseName;
    private Connection connection ;
    //2. 下面就是获取表的信息。
    private DatabaseMetaData metaData ;
    public DataBase(String jdbcUrl) {
        this( jdbcUrl,"com.mysql.jdbc.Driver");
    }
    public DataBase(String jdbcUrl, String driverName) {
        int beginIndex=jdbcUrl.lastIndexOf("/");
        String dataBaseName=jdbcUrl.substring(beginIndex+1);
        if (dataBaseName!=null&&dataBaseName.contains("?")){
            this.dataBaseName=dataBaseName.split("\\?")[0];
        }
        try {
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(jdbcUrl);
            //2. 下面就是获取表的信息。
            metaData = connection.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getTableNames()  {
        ResultSet tableRet = null;
        try {
            tableRet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});

            List list=new ArrayList();
            while (tableRet.next()) {
                list.add(tableRet.getString("TABLE_NAME"));
            }
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public List<Table> getTables()  {
        List<Table> tableList=new ArrayList();
        List<String> li=getTableNames();
        for (int i = 0; i <li.size(); i++) {
            String tableName=li.get(i);
            Table table=getTable(tableName);
            tableList.add(table);
        }

        return tableList;
    }
    public Table getTable(String tableName)   {
        Table  table=new Table();
        table.setName(tableName);
        //获取表注释
        Statement statement= null;
        try {
            statement = connection.createStatement();

        String sql="Select * from INFORMATION_SCHEMA.TABLES Where " +
                "table_schema = '"+dataBaseName+"' And  table_name = '"+tableName+"'";
        ResultSet tableResultSet=statement.executeQuery(sql);
        if (tableResultSet.next()){
            table.setComment(tableResultSet.getString("TABLE_COMMENT"));
        }
        List<TableField> list=new ArrayList();
        //4. 提取表内的字段的名字和类型
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        while (colRet.next()) {
            TableField tableField=new TableField();
            String name = colRet.getString("COLUMN_NAME");
            String type = colRet.getString("TYPE_NAME");
            String comment = colRet.getString("REMARKS");
            int size= colRet.getInt("COLUMN_SIZE");
            if ("id".equals(name)){
                continue;
            }
            tableField.setName(name);
            tableField.setType(type);
            tableField.setComment(comment);
            tableField.setSize(size);
            list.add(tableField);
        }
        table.setTableFields(list);
         return  table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
