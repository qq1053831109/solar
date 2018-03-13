package org.solar.util;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    public static List<List> parseExcel(File file) {
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(file.getName().endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(file.getName().endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = null;
        List list=new ArrayList();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
            sheet = workbook.getSheetAt(i);
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
                Row row = sheet.getRow(j);
                if (row != null) {
                    List rowList=new ArrayList();
                    for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                        String cellValue = getCellValue(row.getCell(k));
                        rowList.add(cellValue);
                    }
                    list.add(rowList);
                }

            }
        }
        return list;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
        }
        return cellValue;
    }
}