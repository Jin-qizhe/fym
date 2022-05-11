package com.ydt.service;

import com.ydt.bean.BYqycFyBase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydt.controller.res.ScanRes;
import com.ydt.controller.res.InstanceRes;
import com.ydt.controller.res.UserRes;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 防疫基础数据表 服务类
 * </p>
 *
 * @author ydt
 * @since 2022-04-19
 */
public interface BYqycFyBaseService extends IService<BYqycFyBase> {

    UserRes getUser(String zjhm, String sjhm);

    ScanRes getFyxx(String certNo);

    ScanRes getScanxx(String userId);

    InstanceRes getInstancexx(String instanceCode);

    String getSsdq(String addrId);

    void deleteAllfy();

    List<ScanRes> getScanList(String ysxh, Date date);

    UserRes getUserById(String userId);
}
