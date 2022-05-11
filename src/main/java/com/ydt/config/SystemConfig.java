package com.ydt.config;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lvjianqing on 2017/8/1.
 * 系统常量
 */
public class SystemConfig {
    static Logger log = Logger.getLogger(SystemConfig.class);
    public static String SYSTEMID = "BA4B020D28264BF89F30C8F31024A237";
    public static String ManagerSessionName = "manager";//在session中存放登录用户信息的key
    public static String ManagerDwSessionName = "manager_dw"; //在session中存放登录用户单位的key
    public static String ManagerYwSessionName = "manager_yw";//在session中存放登录用户业务列表
    public static String user_menu_list = "list";//用户菜单
    public static String Dictionary = "dict";
    public static String DW_UTIL = "dw_util";

    public static String getMapVal(Map map, String key){
        String val = "";
        try {
            Object obj = map.get(key);
            if(obj==null){
                obj = map.get(key.toUpperCase());
            }
            if(obj==null){
                return "";
            }
            if(obj instanceof String[]){
                String []val_arr = (String[]) obj;
                String dh = "";
                for(int i=0;i<val_arr.length;i++){
                    val+=dh+val_arr[i];
                    dh = ",";
                }

                return val;
            }else if(obj instanceof String){
                return (String)obj;
            }else if(obj instanceof Long){
                return obj.toString();
            }else if(obj instanceof BigDecimal){
                BigDecimal l = (BigDecimal)obj;
                return l.toString();
            }else if(obj instanceof Character){
                Character l = (Character)obj;
                return l.toString();
            }
        }catch (Exception e) {
            val = "";
        }
        return val;
    }
}
