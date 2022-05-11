package com.ydt.service.impl;

import com.ydt.bean.BFyxx;
import com.ydt.bean.BYqycFyBase;
import com.ydt.controller.res.ScanRes;
import com.ydt.controller.res.InstanceRes;
import com.ydt.controller.res.UserRes;
import com.ydt.dao.BYqycFyBaseDao;
import com.ydt.service.BFyxxService;
import com.ydt.service.BYqycFyBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 防疫基础数据表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2022-04-19
 */
@Service
public class BYqycFyBaseServiceImpl extends ServiceImpl<BYqycFyBaseDao, BYqycFyBase> implements BYqycFyBaseService {

    @Autowired
    private BYqycFyBaseDao bYqycFyBaseDao;
    @Autowired
    private BFyxxService bFyxxService;

    @Override
    public UserRes getUser(String zjhm, String sjhm) {
        return bYqycFyBaseDao.getUser(zjhm, sjhm);
    }

    @Override
    public ScanRes getFyxx(String certNo) {
        return bYqycFyBaseDao.getFyxx(certNo);
    }

    @Override
    public ScanRes getScanxx(String userId) {
        return bYqycFyBaseDao.getScanxx(userId);
    }

    @Override
    public InstanceRes getInstancexx(String instanceCode) {
        return bYqycFyBaseDao.getInstancexx(instanceCode);
    }

    @Override
    public String getSsdq(String addrId) {
        return bYqycFyBaseDao.getSsdq(addrId);
    }

    @Override
    public void deleteAllfy() {
        List<BYqycFyBase> bYqycFyBases = list();
        if (bYqycFyBases.size() > 0) {
            bYqycFyBases.stream().forEach(l -> {
                removeById(l);
            });
        }
        //List<String> ids = bYqycFyBases.stream().map(BYqycFyBase::getId).collect(Collectors.toList());
        //removeByIds(ids);

        List<BFyxx> bFyxxes = bFyxxService.list();
        if (bFyxxes.size() > 0) {
            bFyxxes.stream().forEach(l -> {
                bFyxxService.removeById(l);
            });
        }
//        List<String> ids1 = bFyxxes.stream().map(BFyxx::getId).collect(Collectors.toList());
//        bFyxxService.removeByIds(ids1);
    }

    @Override
    public List<ScanRes> getScanList(String ysxh, Date date) {
        return bYqycFyBaseDao.getScanList(ysxh, date);
    }

    @Override
    public UserRes getUserById(String userId) {
        return bYqycFyBaseDao.getUserById(userId);
    }


}
