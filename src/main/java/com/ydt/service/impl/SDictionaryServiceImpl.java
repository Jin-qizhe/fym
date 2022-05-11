package com.ydt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ydt.bean.SDictionary;
import com.ydt.config.RedisManager;
import com.ydt.dao.SDictionaryDao;
import com.ydt.service.SDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SDictionaryServiceImpl extends ServiceImpl<SDictionaryDao, SDictionary> implements SDictionaryService {
    @Autowired
    private RedisManager redisManager;

    @Override
    public List<SDictionary> getDictList() {
        Object dict = redisManager.get("jfyjs_dict");
        List<SDictionary> list = null;
        if (dict != null) {
            list = (List<SDictionary>) dict;
        } else {
            QueryWrapper<SDictionary> qw = new QueryWrapper<>();
            qw.eq("status", 0);
            list = list(qw);
            if (list != null && list.size() > 0) redisManager.set("jfyjs_dict", list, 3600);//缓存保留一个小时
        }
        return list;
    }

    @Override
    public Multimap<String, String[]> getDicts() {
        List<SDictionary> list = getDictList();
        Multimap<String, String[]> dictMap = ArrayListMultimap.create();
        for (SDictionary d : list) {
            dictMap.put(d.getDicName(), new String[]{d.getDicVal(), d.getDicMemo()});
        }
        return dictMap;
    }
}
