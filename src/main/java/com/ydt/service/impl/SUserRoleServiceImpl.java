package com.ydt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydt.bean.SUserRole;
import com.ydt.dao.SUserRoleDao;
import com.ydt.service.SUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色关联表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SUserRoleServiceImpl extends ServiceImpl<SUserRoleDao, SUserRole> implements SUserRoleService {

}
