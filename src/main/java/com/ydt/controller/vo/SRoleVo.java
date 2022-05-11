package com.ydt.controller.vo;

import lombok.Data;

@Data
public class SRoleVo extends BaseVo {
    private String roleName;
    private String roleId;
    private String[] permissions;
}
