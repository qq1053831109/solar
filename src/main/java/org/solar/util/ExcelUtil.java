package org.solar.util;

import java.io.*;
import java.util.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**

 */
public class ExcelUtil {


    public static byte[] toExcel(List<List> rowList) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 生成一个表格
        SXSSFSheet sheet = workbook.createSheet("1");
        // 产生表格标题行
        for (int i = 0; i <rowList.size() ; i++) {
            SXSSFRow row = sheet.createRow(i) ;
            List cellList=rowList.get(i);
            for (int j = 0; j <cellList.size() ; j++) {
                sheet.setColumnWidth(j,3800);
                Object val=cellList.get(j);
                if (val==null){
                    continue;
                }
                SXSSFCell cell=row.createCell(j);
                if (val instanceof String){
                    cell.setCellValue((String) val);
                }else if(val instanceof Integer){
                    cell.setCellValue((Integer) val);
                }else if(val instanceof Long){
                    cell.setCellValue((Long) val);
                }else if(val instanceof Double){
                    cell.setCellValue((Double) val);
                }else if(val instanceof Date){
                    cell.setCellValue((Date) val);
                }else {
                    cell.setCellValue(String.valueOf(val));
                }
            }
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static void main(String[] args) throws IOException {

    }
}