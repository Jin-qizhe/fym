package com.ydt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ydt.bean.SPower;
import com.ydt.bean.SUser;
import com.ydt.config.SysLog;
import com.ydt.config.SysLogin;
import com.ydt.config.SysUser;
import com.ydt.config.UnAuthority;
import com.ydt.controller.res.MyRes;
import com.ydt.service.SUserService;
import com.ydt.util.DESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
    @Autowired
    SysLogin sysLogin;
    @Autowired
    private SUserService sUserService;

    @UnAuthority
    @RequestMapping("/login")
    @SysLog(name = "系统登录")
    public String login() {
        return "/login";
    }

    @UnAuthority
    @RequestMapping("/dologin")
    @ResponseBody
    public MyRes dologin(String username, String password) throws Exception {
        MyRes res = new MyRes();
        if (StringUtils.isBlank(username)) {
            res.setFailed(1001, "缺少用户名");
            return res;
        }
        if (StringUtils.isBlank(password)) {
            res.setFailed(1001, "缺少密码");
            return res;
        }
        QueryWrapper<SUser> qw = new QueryWrapper<>();
        qw.eq("login_name", username).eq("status", 0);
        SUser user = sUserService.getOne(qw);
        if (user != null && user.getLoginName().equals(username)) {
            if (user.getPassword().equals(DESUtils.encrypt(password))) {
                //登录成功
//                List<SPower> list = sUserService.getPowerByUserId(user.getId());
//                List<String> urls = new ArrayList<>();
//                List<String> btns = new ArrayList<>();
//                if (list != null && list.size() > 0) {
//                    list.stream().forEach(v -> {
//                        if (v.getType().equals(99)) {
//                            btns.add(v.getBtn());
//                        } else {
//                            String url = v.getUrl();
//                            if (StringUtils.isNotBlank(url)) {
//                                urls.add(url);
//                            }
//                        }
//                    });
//                }
                //sysLogin.login(username, user.getName(), user.getId(), user.getDeptId(), user.getDeptName(), urls, btns, user.getType());
                sysLogin.login1(username, user.getName(), user.getId(), user.getDeptId(), user.getDeptName());
                res.setSuccess("登录成功");
            } else {
                res.setFailed(-1, "密码错误");
            }
        } else {
            res.setFailed(1001, "用户不存在");
        }
        return res;
    }

    @RequestMapping("/loginout")
    @SysLog(name = "退出登录")
    @UnAuthority
    public String loginout() {
        sysLogin.loginOut();
        return "/login";
    }

    @RequestMapping("/")
    @UnAuthority
    public String index(ModelMap modelMap) {
        //SysUser user = getUser();
        //modelMap.put("userName", user.getName());
//        List<SPower> list = sUserService.getPowerByUserId(user.getId());
//        modelMap.put("list", list);
        return "/login";
    }

    @RequestMapping("/homepage")
    @SysLog(name = "系统主页")
    public String homepage(ModelMap modelMap) {
        SysUser user = getUser();
        modelMap.put("userName", user.getName());
        List<SPower> powerList = sUserService.getPowerByUserId(user.getId());
        modelMap.put("powerList", powerList);
        return "/console";
    }

    //修改密码
    @RequestMapping("/change")
    public String change(ModelMap modelMap) {
        return "/changePassword";
    }


    //修改密码
    @RequestMapping("/doChange")
    @SysLog(name = "修改密码")
    @ResponseBody
    public MyRes doChange(String oldpass, String newpass) throws Exception {
        MyRes res = new MyRes();
        String username = getUser().getLoginName();
        if (StringUtils.isBlank(oldpass)) {
            res.setFailed(-1, "缺少旧密码");
            return res;
        }
        if (StringUtils.isBlank(newpass)) {
            res.setFailed(-1, "缺少新密码");
            return res;
        }
        QueryWrapper<SUser> qw = new QueryWrapper<>();
        qw.eq("login_name", username).eq("status", 0);
        SUser user = sUserService.getOne(qw);
        if (user != null ) {
            if (user.getPassword().equals(DESUtils.encrypt(oldpass))) {
                //校验旧密码
                user.setPassword(DESUtils.encrypt(newpass));
                Boolean u = sUserService.updateById(user);
                if (u){
                    res.setSuccess("修改密码成功");
                }else{
                    res.setFailed(-1, "修改密码失败");
                }
            }else {
                res.setFailed(-1, "当前密码错误");
            }
        } else {
            res.setFailed(-1, "用户不存在");
        }
        return res;
    }

}
