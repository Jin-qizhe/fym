package com.ydt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydt.bean.SPower;
import com.ydt.bean.SRole;
import com.ydt.bean.SRolePower;
import com.ydt.bean.SUserRole;
import com.ydt.config.SysLog;
import com.ydt.controller.vo.SRoleVo;
import com.ydt.service.SPowerService;
import com.ydt.service.SRolePowerService;
import com.ydt.service.SRoleService;
import com.ydt.service.SUserRoleService;
import com.ydt.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 系统角色
 */
@Controller
@RequestMapping("srole")
public class SRoleController extends BaseController {
    @Autowired
    private SRoleService sRoleService;
    @Autowired
    private SUserRoleService sUserRoleService;
    @Autowired
    private SRolePowerService sRolePowerService;
    @Autowired
    private SPowerService sPowerService;

    @RequestMapping("list")
    @SysLog(name = "角色管理")
    protected String list() {
        return "/srole/srole_list";
    }

    @RequestMapping("data")
    @ResponseBody
    protected String data(SRoleVo vo) {
        Page<SRole> page = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper<SRole> ew = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(vo.getRoleName())) {
            ew.like("role_name", vo.getRoleName());
        }
        page = sRoleService.page(page, ew);
        return getPageData(page);
    }

    @RequestMapping("/edit")
    @SysLog(name = "角色编辑")
    protected String edit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SRoleVo vo) {
        Integer id = vo.getId();
        modelMap.put("role", new SRole());
        if (id != null && id != -1) {
            SRole role = sRoleService.getById(id);
            if (role != null) {
                modelMap.put("role", role);
            }
        }
        return "/srole/srole_edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    protected String save(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SRoleVo vo) {
        SRole role = new SRole();
        if (vo.getId() != null) {
            role.setId(vo.getId());
        }
        role.setRoleName(vo.getRoleName());
        boolean r = sRoleService.saveOrUpdate(role);
        if (r) {
            return "角色编辑成功";
        } else {
            return "角色编辑失败";
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    @SysLog(name = "角色删除")
    protected Boolean delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, SRoleVo vo) {
        Integer id = vo.getId();
        if (id != null) {
            boolean r = sRoleService.removeById(id);
            if (r) {
                QueryWrapper<SRolePower> ew = new QueryWrapper<>();
                ew.eq("role_id", id);
                sRolePowerService.remove(ew);
                QueryWrapper<SUserRole> ew1 = new QueryWrapper<>();
                ew1.eq("role_id", id);
                sUserRoleService.remove(ew1);
            }
            return r;
        } else {
            return false;
        }
    }

    /**
     * 角色权限编辑
     *
     * @param id
     * @return
     */
    @RequestMapping("permission/edit")
    @SysLog(name = "角色权限编辑")
    public String permissionEdit(Long id, ModelMap modelMap) {
        if (id != null) {
            QueryWrapper<SPower> sx = new QueryWrapper<>();
            sx.eq("status", 0);
            List<SPower> all = sPowerService.list(sx);
            modelMap.put("all", all);
            QueryWrapper<SRolePower> ew = new QueryWrapper<>();
            ew.eq("role_id", id);
            List<SRolePower> self = sRolePowerService.list(ew);
            modelMap.put("self", self);
            modelMap.put("id", id);
        }
        return "/srole/power_edit";
    }

    /**
     * 角色权限保存
     *
     * @param id
     * @param permissions
     * @return
     */
    @RequestMapping("permission/save")
    @ResponseBody
    public String permissionSave(Integer id, Integer[] permissions) {
        if (id != null && permissions != null) {
            QueryWrapper<SRolePower> ew = new QueryWrapper<>();
            ew.eq("role_id", id);
            sRolePowerService.remove(ew);
            HashSet<Integer> pids = new HashSet<>();
            List<SRolePower> list = new ArrayList<>();
            for (Integer p : permissions) {
                SPower per = sPowerService.getById(p);
                if (per != null && per.getType()!=99) {
                    pids.add(per.getPid());
                }
                pids.add(p);
            }
            for (Integer pid : pids) {
                SRolePower rp = new SRolePower();
                rp.setRoleId(id);
                rp.setPowerId(pid);
                list.add(rp);
            }
            sRolePowerService.saveBatch(list);
            return "角色权限编辑成功";
        }
        return "角色权限编辑失败";
    }
}
