package com.ydt.dao;

import com.ydt.bean.BYqycFyBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydt.controller.res.ScanRes;
import com.ydt.controller.res.InstanceRes;
import com.ydt.controller.res.UserRes;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 防疫基础数据表 Mapper 接口
 * </p>
 *
 * @author ydt
 * @since 2022-04-19
 */
public interface BYqycFyBaseDao extends BaseMapper<BYqycFyBase> {

    UserRes getUser(@Param("zjhm") String zjhm,@Param("sjhm") String sjhm);

    ScanRes getFyxx(String certNo);

    ScanRes getScanxx(String userId);

    InstanceRes getInstancexx(String instanceCode);

    String getSsdq(String addrId);

    List<ScanRes> getScanList(@Param("ysxh") String ysxh,@Param("date") Date date);

    UserRes getUserById(String userId);
}
