package com.ydt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydt.bean.SPower;
import com.ydt.config.RedisManager;
import com.ydt.dao.SPowerDao;
import com.ydt.service.SPowerService;
import com.ydt.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SPowerServiceImpl extends ServiceImpl<SPowerDao, SPower> implements SPowerService {
    @Autowired
    private RedisManager redisManager;

    @Override
    public boolean isSystemUrl(String url) {
        if (StringUtils.isBlank(url)) return false;
        Object o = redisManager.get("system_url_" + url);
        if (o == null) {
            QueryWrapper<SPower> qw = new QueryWrapper<>();
            qw.eq("status", 0).in("type", 0, 1, 2).isNotNull("url");
            List<SPower> list = list(qw);
            boolean flag = false;
            if (list != null && list.size() > 0) {
                for (SPower v : list) {
                    if (StringUtil.isNotEmpty(v.getUrl()) && !v.getUrl().equals("null")) {
                        redisManager.set("system_url_" + url, v.getUrl(), 3600);
                    }
                    if (url.equals(v.getUrl())) flag = true;
                }
            }
            if (!flag) {
                redisManager.set("system_url_" + url, "no", 3600);
            }
            return flag;
        } else {
            String u = (String) o;
            if (u.equals("no") || u.equals("null")) {
                return false;
            } else {
                return true;
            }
        }
    }
}
