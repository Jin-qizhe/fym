package com.ydt.util.excel;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * 标题列
 * 吕建清
 * 2018-07-05
 */
public class ExcelColumn {
    private String title;
    private String column;
    private String type;
    private HashMap<String, String> dict;

    /**
     * @param title  标题
     * @param column 列名称
     * @param dict   字典
     */
    public ExcelColumn(String column, String title, HashMap<String, String> dict) {
        this.title = title;
        this.column = column;
        this.type = null;
        this.dict = dict;
    }

    /**
     * @param title  标题
     * @param column 列名称
     * @param type   字段类型：date
     */
    public ExcelColumn(String column, String title, String type) {
        this.title = title;
        this.column = column;
        this.type = type;
        this.dict = null;
    }

    /**
     * @param title  标题
     * @param column 列名称
     */
    public ExcelColumn(String column, String title) {
        this.title = title;
        this.column = column;
        this.type = null;
        this.dict = null;
    }

    public String getValue(JSONObject jo) {
        try {
            if (jo != null && jo.containsKey(column)) {
                String key = jo.getString(column);
                if (StringUtils.isNotEmpty(type)) {
                    if (type.equalsIgnoreCase("date")) {//时间类型
                        return new DateTime(jo.getTimestamp(column)).toString("yyyy-MM-dd HH:mm:ss");
                    }
                    if(type.equalsIgnoreCase("array")){
                        return jo.getString(column).replace("[\"","").replace("\"]","").replace("\"","");
                    }
                    if(type.equalsIgnoreCase("sex")){
                        if(jo.getString(column).equals("1")){
                            return "男";
                        }else{
                            return "女";
                        }
                    }
                } else if (dict != null && StringUtils.isNotEmpty(key)) {
                    return dict.get(key);
                } else {
                    return key;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getDict() {
        return dict;
    }

    public void setDict(HashMap<String, String> dict) {
        this.dict = dict;
    }
}