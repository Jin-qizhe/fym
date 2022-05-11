package com.ydt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydt.config.SysConfig;
import com.ydt.config.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysConfig sysConfig;

    /**
     * 获取登录用户
     *
     * @return
     */
    protected SysUser getUser() {
        return (SysUser) request.getAttribute(sysConfig.SYSUSER);
    }

    /**
     * 转换layui表格
     *
     * @param p
     * @return
     */
    protected String getPageData(Page<?> p) {
        String list = JSON.toJSONString(p.getRecords());
        JSONArray data = JSONArray.parseArray(list);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "");
        json.put("count", p.getTotal());
        json.put("data", data);
        json.put("pages", p.getPages());
        return json.toJSONString();
    }

    /**
     * 获取request
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
