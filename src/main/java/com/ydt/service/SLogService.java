package com.ydt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydt.bean.SLog;
import com.ydt.controller.vo.SlogVo;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统操作日志 服务类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
public interface SLogService extends IService<SLog> {

    Page<SLog> getLogList(SlogVo vo);

    void slogExcel(HttpServletResponse response, String slogData);
}
