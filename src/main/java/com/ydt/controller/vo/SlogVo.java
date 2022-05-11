package com.ydt.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SlogVo extends BaseVo {

    private Integer id;

    /**
     * 功能名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 操作用户ID
     */
    private Integer userId;

    /**
     * 操作用户名称
     */
    private String userName;

    /**
     * 请求来源地址
     */
    private String host;

    /**
     * 日志打印时间
     */
    private Date logTime;
    private String logTimeQj;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态：0有效，-1无效
     */
    private Integer status;

}

