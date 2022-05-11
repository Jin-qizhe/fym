package com.ydt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydt.bean.SPower;
import com.ydt.bean.SUser;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SUserService extends IService<SUser> {
    List<SPower> getPowerByUserId(Integer userId);
}
