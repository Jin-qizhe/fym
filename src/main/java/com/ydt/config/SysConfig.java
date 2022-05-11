package com.ydt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 */
@Component
public class SysConfig {
    /**
     * 登录授权token
     */
    @Value("${token:token}")
    public String TOKEN;
    /**
     * 系统用户
     */
    @Value("${sysuser:sysuser}")
    public String SYSUSER;
    /**
     * 系统用户
     */
    @Value("${logintimeout:86400}")
    public Long LOGINTIMEOUT;
    /**
     * 登录url
     */
    @Value("${loginurl:/login}")
    public String LOGINURL;
    /**
     * 无权限提示页面
     */
    @Value("${nopowerurl:/system/no_power}")
    public String NOPOWERURL;
}
