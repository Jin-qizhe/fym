package com.ydt.util.excel;

import jxl.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*******************************************************************************
 * <p>
 * File Name: XlsUtil.java
 * </p>
 * <p>
 * Title: excel的操作的公用方法
 * </p>
 * <p>
 * Description: 从excel文件流获取单元格数据
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author 徐建协
 * @version 1.0 2006/07/06
 ******************************************************************************/
public class XlsUtil {

    /**
     * 从EXCEL文件中获取数据
     *
     * @param path String 文件的路径
     * @return 二维数组
     */
    public static String[][] GetArrays(String path) {
        try {
            InputStream is = new FileInputStream(path);
            Workbook wb = Workbook.getWorkbook(is);// 从输入流创建Workbook
            Sheet rs = wb.getSheet(0);// 获取第一张Sheet表
            String[][] tmp = new String[rs.getRows()][rs.getColumns()];
            for (int i = 0; i < rs.getRows(); i++) {
                for (int j = 0; j < rs.getColumns(); j++) {
                    tmp[i][j] = rs.getCell(j, i).getContents().trim();
                }
            }
            rs = null;
            wb.close();
            is.close();
            wb = null;
            is = null;
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从EXCEL文件中获取数据
     *
     * @param file File 文件
     * @return 二维数组
     */
    public static String[][] GetArrays(File file) {
        try {
            InputStream is = new FileInputStream(file);
            Workbook wb = Workbook.getWorkbook(is);// 从输入流创建Workbook
            Sheet rs = wb.getSheet(0);// 获取第一张Sheet表
            String[][] tmp = new String[rs.getRows()][rs.getColumns()];
            for (int i = 0; i < rs.getRows(); i++) {
                for (int j = 0; j < rs.getColumns(); j++) {
                    tmp[i][j] = rs.getCell(j, i).getContents().trim();
                }
            }
            rs = null;
            wb.close();
            is.close();
            wb = null;
            is = null;
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从EXCEL文件中获取数据,指定某个sheet
     *
     * @param file File 文件
     * @return 二维数组
     */
    public static String[][] GetArrays(File file, int sheetnum) throws Exception {
        InputStream is = new FileInputStream(file);
        Workbook wb = Workbook.getWorkbook(file);// 从输入流创建Workbook
        Sheet rs = wb.getSheet(sheetnum);// 获取第一张Sheet表
        String[][] tmp = new String[rs.getRows()][rs.getColumns()];
        for (int i = 0; i < rs.getRows(); i++) {
            for (int j = 0; j < rs.getColumns(); j++) {
                //判断日期格式
                Cell cell = rs.getCell(j, i);
                if (cell.getType() == CellType.DATE) {
                    DateCell dc = (DateCell) cell;
                    Date date = dc.getDate();
                    TimeZone zone = TimeZone.getTimeZone("GMT");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(zone);
                    String sDate = sdf.format(date);
                    tmp[i][j] = sDate;
                }else{
                    tmp[i][j] = rs.getCell(j, i).getContents().trim();
                }
            }
        }
        rs = null;
        wb.close();
        is.close();
        wb = null;
        is = null;
        return tmp;
    }

    /**
     * 从EXCEL文件中获取数据
     *
     * @param is InputStream 输入流
     * @return 二维数组
     */
    public static String[][] GetArrays(InputStream is) {
        try {
            Workbook wb = Workbook.getWorkbook(is);// 从输入流创建Workbook
            Sheet rs = wb.getSheet(0);// 获取第一张Sheet表
            String[][] tmp = new String[rs.getRows()][rs.getColumns()];
            for (int i = 0; i < rs.getRows(); i++) {
                for (int j = 0; j < rs.getColumns(); j++) {
                    tmp[i][j] = rs.getCell(j, i).getContents().trim();
                }
            }
            rs = null;
            wb.close();
            is.close();
            wb = null;
            is = null;
            return tmp;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /***************************************************************************
     * 从EXCEL字节数组中获取数据
     *
     * @param data
     *            byte[]
     * @return String[][]
     */
    public static String[][] GetArrays(byte[] data) {
        try {
            InputStream is = new ByteArrayInputStream(data);
            Workbook wb = Workbook.getWorkbook(is);// 从输入流创建Workbook
            Sheet rs = wb.getSheet(0);// 获取第一张Sheet表
            String[][] tmp = new String[rs.getRows()][rs.getColumns()];
            for (int i = 0; i < rs.getRows(); i++) {
                for (int j = 0; j < rs.getColumns(); j++) {
                    tmp[i][j] = rs.getCell(j, i).getContents().trim();
                }
            }
            rs = null;
            wb.close();
            is.close();
            data = null;
            wb = null;
            is = null;
            return tmp;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String[][] data = XlsUtil.GetArrays("11.xls");
        for (int i = 0; i < data.length; i++) {
            System.out.print("第" + (i + 1) + "行 ");
            for (int j = 0; j < data[0].length; j++) {
                System.out.print(data[i][j] + ",");
            }
            System.out.println("");
        }

    }

}
