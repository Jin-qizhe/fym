package com.ydt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydt.bean.SPower;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SPowerDao extends BaseMapper<SPower> {

    List<Map<String, Object>> getPowerList(int id);

}
