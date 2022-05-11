package com.ydt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydt.bean.SPower;
import com.ydt.bean.SUser;
import com.ydt.dao.SUserDao;
import com.ydt.service.SPowerService;
import com.ydt.service.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserDao, SUser> implements SUserService {
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SPowerService sPowerService;

    @Override
    public List<SPower> getPowerByUserId(Integer userId) {
        List<SPower> list = null;
        if (userId == 1) {//admin
            QueryWrapper<SPower> qw = new QueryWrapper<>();
            qw.eq("status", 0).orderByAsc("sort");
            list = sPowerService.list(qw);
        } else {
            list = sUserDao.getPowerByUserId(userId);
        }
        return list;
    }
}
