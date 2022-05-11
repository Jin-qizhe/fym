package com.ydt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydt.bean.SLog;
import com.ydt.config.RedisManager;
import com.ydt.config.SysConfig;
import com.ydt.controller.vo.SlogVo;
import com.ydt.dao.SLogDao;
import com.ydt.service.SLogService;
import com.ydt.util.StringUtil;
import com.ydt.util.excel.Excel;
import com.ydt.util.excel.ExcelColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统操作日志 服务实现类
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Service
public class SLogServiceImpl extends ServiceImpl<SLogDao, SLog> implements SLogService {

    @Autowired
    private RedisManager redisManager;
    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private SLogService sLogService;

    @Override
    public Page<SLog> getLogList(SlogVo vo) {
        Page<SLog> page = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper<SLog> ew = new QueryWrapper<>();

        if (StringUtil.isNotEmpty(vo.getName())) {
            ew.like("name", vo.getName());
        }
        if (StringUtil.isNotEmpty(vo.getUrl())) {
            ew.like("url", vo.getUrl());
        }
        if (vo.getUserId() != null) {
            ew.eq("user_id", vo.getUserId());
        }
        if (StringUtil.isNotEmpty(vo.getUserName())) {
            ew.like("user_name", vo.getUserName());
        }
        if (StringUtil.isNotEmpty(vo.getLogTimeQj())) {
            String qj = vo.getLogTimeQj();
            ew.ge("log_time", qj.split("~")[0]);
            ew.le("log_time", qj.split("~")[1]);
        }
        ew.eq("status", 0);
        page = sLogService.page(page, ew);

        return page;
    }

    @Override
    public void slogExcel(HttpServletResponse response, String slogData) {
        List<ExcelColumn> columns = new ArrayList<>();
        columns.add(new ExcelColumn("name", "功能名称"));
        columns.add(new ExcelColumn("url", "目标地址URL"));
        columns.add(new ExcelColumn("userName", "操作用户名称"));
        columns.add(new ExcelColumn("host", "来源地址"));
        columns.add(new ExcelColumn("logTime", "操作时间", "date"));
        Excel excel = new Excel(slogData, columns);
        excel.writeExcel(response, "操作日志表");
    }

}
