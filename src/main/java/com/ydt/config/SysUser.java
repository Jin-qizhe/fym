package com.ydt.config;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;

/**
 * 登录用户信息和权限信息
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 6123866133028499223L;
    private String loginName;
    private String name;
    private String deptId;
    private String deptName;
    private Integer id;
    private Integer type;
    private HashSet<String> urls;
    private HashSet<String> btns;

    public boolean hasUrL(String url) {
        if (url.equals("/")) return true;
        if (url != null && url.length() > 0 && urls != null && urls.size() > 0 && urls.contains(url)) {
            return true;
        }
        return false;
    }

    public boolean hasBtn(String btn) {
        if (btn != null && btn.length() > 0 && btns != null && btns.size() > 0 && btns.contains(btn)) {
            return true;
        }
        return false;
    }
}
