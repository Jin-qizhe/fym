package com.ydt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Multimap;
import com.ydt.bean.SDictionary;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SDictionaryService extends IService<SDictionary> {
    List<SDictionary> getDictList();

    Multimap<String, String[]> getDicts();
}
