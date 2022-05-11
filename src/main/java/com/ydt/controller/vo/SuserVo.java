package com.ydt.controller.vo;

import lombok.Data;

@Data
public class SuserVo extends BaseVo {
    private String loginName;
    private String idCard;
    private String phone;
    private String name;
    private String deptId;
    private String deptName;
    private String password;
}
