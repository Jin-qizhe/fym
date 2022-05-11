package com.ydt.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ydt.service.SDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by lvjianqing on 2017/8/8.
 */
@Component
public class Dict {
    @Autowired
    private SDictionaryService sDictionaryService;

    public List<String> getDicNames() {
        Set<String> set = sDictionaryService.getDicts().keySet();
        return new ArrayList<>(set);
    }

    /**
     * 根据dic_name生产select下拉列表
     *
     * @param dic_name
     * @param default_value
     * @return
     */
    public String getOptions(String dic_name, String default_value) {
        if (StringUtils.isBlank(dic_name) || sDictionaryService.getDicts() == null || !sDictionaryService.getDicts().containsKey(dic_name)) {
            return "<option></option>";
        }
        Collection<String[]> vals = sDictionaryService.getDicts().get(dic_name);
        StringBuffer sb = new StringBuffer();
        for (String[] strs : vals) {
            String dic_val = strs[0];
            String dic_memo = strs[1];
            if (StringUtils.isNotBlank(default_value) && dic_val.equals(default_value)) {
                sb.append("<option selected value=\"").append(dic_val).append("\">").append(dic_memo).append("</option>");
            } else {
                sb.append("<option value=\"").append(dic_val).append("\">").append(dic_memo).append("</option>");
            }
        }
        return sb.toString();
    }

    /**
     * 根据dic_val获取dic_memo
     *
     * @param dic_name
     * @param dic_val
     * @return
     */
    public String getDicMemo(String dic_name, String dic_val) {
        if (StringUtils.isBlank(dic_name) || sDictionaryService.getDicts() == null || !sDictionaryService.getDicts().containsKey(dic_name)) {
            return "";
        }
        Collection<String[]> vals = sDictionaryService.getDicts().get(dic_name);
        for (String[] strs : vals) {
            if (strs[0].equals(dic_val)) {
                return strs[1];
            }
        }
        return "";
    }

    /**
     * 获取指定类型的HashMap
     *
     * @param
     * @return
     * @date 2018/5/4 15:25
     */
    public HashMap getDicMap(String type) {
        HashMap map = new HashMap();
        Collection<String[]> vals = sDictionaryService.getDicts().get(type);
        for (String[] strs : vals) {
            map.put(strs[0], strs[1]);
        }
        return map;
    }

    /**
     * 根据dic_memo获取dic_val
     *
     * @param dic_name
     * @param dic_memo
     * @return
     */
    public String getDicVal(String dic_name, String dic_memo) {
        if (StringUtils.isBlank(dic_name) || sDictionaryService.getDicts() == null || !sDictionaryService.getDicts().containsKey(dic_name)) {
            return "";
        }
        Collection<String[]> vals = sDictionaryService.getDicts().get(dic_name);
        for (String[] strs : vals) {
            if (strs[1].equals(dic_memo)) {
                return strs[0];
            }
        }
        return "";
    }

    /**
     * @param list
     * @param default_value
     * @return
     */
    public static String getOptions(List<String[]> list, String default_value) {
        if (list == null) {
            return "<option></option>";
        }
        StringBuffer sb = new StringBuffer();
        for (String[] strs : list) {
            String k = strs[0];
            String v = strs[1];
            if (k.equals(default_value)) {
                sb.append("<option selected value=\"").append(k).append("\">").append(v).append("</option>");
            } else {
                sb.append("<option value=\"").append(k).append("\">").append(v).append("</option>");
            }
        }
        return sb.toString();
    }


    public static String getCheckbox(List<String[]> list, String name, List<String> values) {
        if (list == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String[] strs : list) {
            String k = strs[0];
            String v = strs[1];
            if (values != null && values.contains(k)) {
                sb.append("<input  type='checkbox' lay-skin='primary' name='" + name + "'  value='" + k + "'   title='" + v + "'   checked >");
            } else {
                sb.append("<input  type='checkbox' lay-skin='primary'  name='" + name + "'  value='" + k + "'   title='" + v + "' >");
            }
        }
        return sb.toString();
    }
}
