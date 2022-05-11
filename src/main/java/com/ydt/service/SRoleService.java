package com.ydt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydt.bean.SRole;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SRoleService extends IService<SRole> {
    List<SRole> findUserRole(Integer id);
}
