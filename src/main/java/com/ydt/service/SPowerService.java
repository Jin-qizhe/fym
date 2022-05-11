package com.ydt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydt.bean.SPower;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SPowerService extends IService<SPower> {
    boolean isSystemUrl(String url);
}
