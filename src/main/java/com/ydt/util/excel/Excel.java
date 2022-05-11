package com.ydt.util.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具类
 * 吕建清
 * 2018-07-05
 */
public class Excel {
    private int total;//数据总条数
    private int pages;//数据总页数
    private List<ExcelColumn> columns;//数据列
    private JSONArray arr;

    /**
     * @param data    /list/data 方法返回的数据结果集
     * @param columns 需要显示的列(按照添加顺序显示)
     */
    public Excel(String data, List<ExcelColumn> columns) {
        if (StringUtils.isNotBlank(data)) {
            JSONObject json = JSONObject.parseObject(data);
            int count = json.getIntValue("count");
            int pages = json.getIntValue("pages");
            JSONArray arr = json.getJSONArray("data");
            this.pages = pages;
            this.total = count;
            if (arr == null) {
                this.arr = new JSONArray();
            } else {
                this.arr = arr;
            }
            if (columns == null) {
                this.columns = new ArrayList<>();
            } else {
                this.columns = columns;
            }
        }
    }

    /**
     * @param arr     /list/data 方法返回的数据结果集
     * @param columns 需要显示的列(按照添加顺序显示)
     */
    public Excel(JSONArray arr, List<ExcelColumn> columns) {
        if (arr == null) {
            this.arr = new JSONArray();
        } else {
            this.arr = arr;
        }
        if (columns == null) {
            this.columns = new ArrayList<>();
        } else {
            this.columns = columns;
        }
    }

    /**
     * 生成excel工作簿
     *
     * @return
     */
    public Workbook getWorkBook() {
        // 创建工作簿
        Workbook wb = new SXSSFWorkbook();
        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet("Sheet1");
        // 申明标题行
        int rowIndex = 0;
        Row title = sheet.createRow(rowIndex);
        Cell cell;
        XSSFCellStyle titleStyle = getTitleStyle(wb);
        int columnIndex = 0;
        for (ExcelColumn column : columns) {
            // 写入标题
            cell = title.createCell(columnIndex);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(column.getTitle());
            sheet.setColumnWidth(columnIndex, 10 * 512);
            columnIndex++;
        }
        rowIndex++;
        Row row;
        CellStyle cs = wb.createCellStyle();
        if (arr != null && arr.size() > 0) {
            for (int i = 0; i < arr.size(); i++) {
                row = sheet.createRow(rowIndex);
                columnIndex = 0;
                JSONObject jo = arr.getJSONObject(i);
                for (ExcelColumn column : columns) {
                    cell = row.createCell(columnIndex);
                    cell.setCellStyle(cs);
                    cell.setCellValue(column.getValue(jo));
                    columnIndex++;
                }
                rowIndex++;
            }
        }
        return wb;
    }

    /**
     * 写到页面
     *
     * @param response
     * @param fileName 文件名
     */
    public void writeExcel(HttpServletResponse response, String fileName) {
        try {
            Workbook xlsx = getWorkBook();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xlsx");
            response.setContentType("application/msexcel");// 定义输出类型
            xlsx.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XSSFCellStyle getTitleStyle(Workbook wb) {
        XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
        // 设置背景色
//        titleStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.white));
//        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //Font font = wb.createFont();
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置字体
//        titleStyle.setFont(font);
        return titleStyle;
    }
}
