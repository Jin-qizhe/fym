package com.ydt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydt.bean.SRole;
import com.ydt.bean.SUser;
import com.ydt.bean.SUserRole;
import com.ydt.config.SysLog;
import com.ydt.controller.vo.SRoleVo;
import com.ydt.controller.vo.SuserVo;
import com.ydt.service.SRoleService;
import com.ydt.service.SUserRoleService;
import com.ydt.service.SUserService;
import com.ydt.util.DESUtils;
import com.ydt.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 */
@Controller
@RequestMapping("suser")
public class SUserController extends BaseController {
    @Autowired
    private SUserService sUserService;
    @Autowired
    private SRoleService sRoleService;
    @Autowired
    private SUserRoleService sUserRoleService;

    @RequestMapping("/list")
    @SysLog(name = "用户管理")
    public String yhManage() {
        return "/suser/suser_list";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String data(SuserVo vo) {
        Page<SUser> page = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper<SUser> ew = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(vo.getName())) {
            ew.like("name", vo.getName());
        }
        if (StringUtil.isNotEmpty(vo.getPhone())) {
            ew.eq("phone", vo.getPhone());
        }
        if (StringUtil.isNotEmpty(vo.getDeptId())) {
            String[] dws = vo.getDeptId().split(",");
            ew.likeRight("dept_id", dws[dws.length - 1]);
        }
        if (StringUtil.isNotEmpty(vo.getLoginName())) {
            ew.eq("login_name", vo.getLoginName());
        }
        if (StringUtil.isNotEmpty(vo.getIdCard())) {
            ew.eq("id_card", vo.getIdCard());
        }
        ew.eq("status", 0).ne("id", 1);
        page = sUserService.page(page, ew);
        return getPageData(page);
    }

    @RequestMapping("/edit")
    @SysLog(name = "编辑用户")
    protected String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SuserVo vo) throws Exception {
        Integer id = vo.getId();
        modelMap.put("user", new SUser());
        if (id != null && id != -1) {
            SUser user = sUserService.getById(id);
            if (user != null) {
                user.setPassword(DESUtils.decrypt(user.getPassword()));
                modelMap.put("user", user);
            }
        }
        return "/suser/suser_edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    protected String save(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SuserVo vo) throws Exception {
        SUser user = new SUser();
        if (vo.getId() != null) {
            user.setId(vo.getId());
            user.setUpdateTime(new Date());
        }
        user.setLoginName(vo.getLoginName());
        user.setPassword(DESUtils.encrypt(vo.getPassword()));
        user.setIdCard(vo.getIdCard());
        user.setPhone(vo.getPhone());
        user.setName(vo.getName());
        user.setDeptId(vo.getDeptId());
        user.setDeptName(vo.getDeptName());
        user.setInsertTime(new Date());
        user.setType(1);
        boolean r = sUserService.saveOrUpdate(user);
        if (r) {
            return "编辑成功";
        } else {
            return "编辑失败";
        }
    }

    @RequestMapping("checkLoginName")
    @ResponseBody
    public Boolean checkLoginName(String name) {
        if (StringUtil.isEmpty(name)) {
            return true;
        }
        QueryWrapper<SUser> ew = new QueryWrapper<>();
        ew.eq("login_name", name).eq("status", 0);
        List<SUser> list = sUserService.list(ew);
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    @SysLog(name = "删除用户")
    protected Boolean delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SRoleVo vo) {
        Integer id = vo.getId();
        if (id != null) {
            SUser user = sUserService.getById(id);
            if (user != null && !user.getId().equals(1)) {
                user.setStatus(-1);
                user.setUpdateTime(new Date());
                return sUserService.updateById(user);
            }
        }
        return false;
    }

    /**
     * 用户角色编辑
     *
     * @param id
     * @return
     */
    @RequestMapping("/role/edit")
    @SysLog(name = "用户角色编辑")
    public String roleEdit(Integer id, ModelMap modelMap) {
        if (id != null) {
            QueryWrapper<SRole> ew = new QueryWrapper<>();
            ew.eq("status", 0);
            List<SRole> all = sRoleService.list(ew);
            modelMap.put("all", all);
            List<SRole> self = sRoleService.findUserRole(id);
            modelMap.put("self", self);
            modelMap.put("id", id);
        }
        return "/suser/suser_role_edit";
    }

    /**
     * 用户角色保存
     *
     * @param id
     * @param roles
     * @return
     */
    @RequestMapping("role/save")
    @ResponseBody
    public String roleSave(Integer id, Integer[] roles) {
        if (id != null) {
            QueryWrapper<SUserRole> ew = new QueryWrapper<>();
            ew.eq("user_id", id);
            sUserRoleService.remove(ew);
            if (roles != null && roles.length > 0) {
                for (Integer r : roles) {
                    SUserRole ur = new SUserRole();
                    ur.setRoleId(r);
                    ur.setUserId(id);
                    sUserRoleService.save(ur);
                }
            }
            return "用户角色编辑成功";
        }
        return "用户角色编辑失败";
    }
}
