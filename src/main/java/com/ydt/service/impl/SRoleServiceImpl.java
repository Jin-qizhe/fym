package com.ydt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydt.bean.SRole;
import com.ydt.dao.SRoleDao;
import com.ydt.service.SRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SRoleServiceImpl extends ServiceImpl<SRoleDao, SRole> implements SRoleService {
    @Autowired
    private SRoleDao sRoleDao;

    @Override
    public List<SRole> findUserRole(Integer id) {
        return sRoleDao.findUserRole(id);
    }
}
