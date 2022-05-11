package com.ydt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydt.bean.SLog;
import com.ydt.config.SysLog;
import com.ydt.controller.vo.SlogVo;
import com.ydt.service.SLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("system")
public class LogController extends BaseController{

    @Autowired
    private SLogService sLogService;

    @RequestMapping("no_power")
    public String noPage(){
        return "/404";
    }

    @RequestMapping("slogList")
    @SysLog(name = "操作日志")
    public String slogList(){
        return "/slog/slog_list";
    }

    @RequestMapping("/slogData")
    @ResponseBody
    public String slogData(SlogVo vo) {
        Page<SLog> page = sLogService.getLogList(vo);
        return getPageData(page);
    }

    @RequestMapping("/slogExcel")
    @ResponseBody
    @SysLog(name = "操作日志导出")
    protected void slogExcel(HttpServletResponse response, SlogVo vo) {
        sLogService.slogExcel(response, slogData(vo));
    }

}
