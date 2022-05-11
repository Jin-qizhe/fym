package com.ydt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydt.bean.SPower;
import com.ydt.bean.SUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Repository
public interface SUserDao extends BaseMapper<SUser> {
    List<SPower> getPowerByUserId(Integer userId);
}
