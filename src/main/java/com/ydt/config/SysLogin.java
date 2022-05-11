package com.ydt.config;

import com.ydt.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

@Service
public class SysLogin {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    RedisManager redisManager;
    @Autowired
    private SysConfig sysConfig;

    public boolean login(String loginName, String name, Integer id, String deptid, String deptName, List<String> urls, List<String> btns, Integer type) {
        if (loginName == null || name == null || id == null) return false;
        SysUser user = new SysUser();
        user.setLoginName(loginName);
        user.setName(name);
        user.setDeptId(deptid);
        user.setDeptName(deptName);
        user.setId(id);
        user.setType(type);
        HashSet<String> urlSet = new HashSet<>();
        if (urls != null && urls.size() > 0) {
            urls.stream().forEach(v -> {
                urlSet.add(v);
            });
        }
        user.setUrls(urlSet);
        HashSet<String> btnSet = new HashSet<>();
        if (btns != null && btns.size() > 0) {
            btns.stream().forEach(v -> {
                btnSet.add(v);
            });
        }
        user.setBtns(btnSet);
        String token = IdGenerator.getUUID().toUpperCase();
        redisManager.set(token, user, sysConfig.LOGINTIMEOUT);
        response.addCookie(new Cookie(sysConfig.TOKEN, token));
        return true;
    }

    public boolean loginOut() {
        String token = getCookieToken();
       // redisManager.del(token);
        response.addCookie(new Cookie(sysConfig.TOKEN, null));
        return true;
    }

    public String getCookieToken() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c != null && c.getName().equals(sysConfig.TOKEN)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public boolean login1(String loginName, String name, Integer id, String deptid, String deptName) {
        if (loginName == null || name == null || id == null) return false;
        SysUser user = new SysUser();
        user.setLoginName(loginName);
        user.setName(name);
        user.setDeptId(deptid);
        user.setDeptName(deptName);
        user.setId(id);
        String token = IdGenerator.getUUID().toUpperCase();
        //redisManager.set(token, user, sysConfig.LOGINTIMEOUT);
        response.addCookie(new Cookie(sysConfig.TOKEN, token));
        return true;
    }
}
