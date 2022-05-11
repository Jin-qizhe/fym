package com.ydt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydt.bean.SRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SRoleDao extends BaseMapper<SRole> {
    List<SRole> findUserRole(@Param("id") Integer id);
}
