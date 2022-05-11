package com.ydt.controller.res;

import lombok.Data;

/**
 * 用户信息表
 */
@Data
public class UserRes {

    //用户id
    private String userId;

    //姓名
    private String xm;

    //证件号码
    private String zjhm;

    //手机号码
    private String sjhm;

    //健康码状态
    private String jkmzt;

    //接种次数
    private String jxcs;


}
