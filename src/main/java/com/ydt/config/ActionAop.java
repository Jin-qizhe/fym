package com.ydt.config;

import com.ydt.service.SLogService;
import com.ydt.service.SPowerService;
import com.ydt.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class ActionAop {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    RedisManager redisManager;
    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private SysLogin sysLogin;
    @Autowired
    private SPowerService sPowerService;
    @Autowired
    private SLogService sLogService;

    /**
     * 拦截所有的控制层请求
     */
    @Pointcut(value = "execution(* com.ydt.controller..*.*(..))")
    private void action() {
    }

    /**
     * 操作日志打印注解
     * 再需要打印日志的controller层上添加该注解即可打印操作日志
     */
    @Pointcut("@annotation(com.ydt.config.SysLog)")
    public void sysLog() {
    }

    long time = 0;

    @Before(value = "sysLog()")
    private void before() {
        time = System.currentTimeMillis();
    }

    @After(value = "sysLog()")
    private void after(JoinPoint joinPoint) throws ClassNotFoundException {
        String host = request.getRemoteHost(), url = request.getRequestURI();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    SysLog sysLog = method.getAnnotation(SysLog.class);
                    String name = sysLog.name();
                    SysUser user = (SysUser) request.getAttribute(sysConfig.SYSUSER);
                    Integer userId = null;
                    String userName = null;
                    if (user != null) {
                        userId = user.getId();
                        userName = user.getName();
                    }
                    log.error("sysLog:|userId:" + userId + "|userName:" + userName + "|name:" + name + "|host:" + host + "|url:" + url + "|ms:" + (System.currentTimeMillis() - time));
                }
            }
        }
    }

    @Around(value = "action()")
    public Object introcepter(ProceedingJoinPoint pjp) {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            UnAuthority unAuthority = signature.getMethod().getAnnotation(UnAuthority.class);
            if (unAuthority != null) {
                return pjp.proceed();
            }
            String token = request.getParameter(sysConfig.TOKEN);//从url取token
            if (token == null || token.length() == 0) {
                token = sysLogin.getCookieToken();//从cookie取token
            } else {
                response.addCookie(new Cookie(sysConfig.TOKEN, token));
            }
            if (token == null || token.length() == 0) {
                response.sendRedirect(request.getContextPath() + sysConfig.LOGINURL);
                return request.getContextPath() + sysConfig.LOGINURL;
            }
            //Object rm = request.getAttribute(token);
            if (StringUtil.isNotEmpty(token)){
                Object pro = pjp.proceed();
                return pro;
            }else{
                response.sendRedirect(request.getContextPath() + sysConfig.LOGINURL);
                return request.getContextPath() + sysConfig.LOGINURL;
            }
//            if (rm != null) {
//                SysUser user = (SysUser) rm;
//                request.setAttribute(sysConfig.SYSUSER, user);
//                String uri = request.getRequestURI().substring(request.getContextPath().length());
//                if (uri.equals("/") || user.hasUrL(uri) || !sPowerService.isSystemUrl(uri)) {
//                    Object pro = pjp.proceed();
//                    request.removeAttribute(sysConfig.SYSUSER);
//                    request.removeAttribute(sysConfig.TOKEN);
//                    return pro;
//                } else {
//                    response.sendRedirect(request.getContextPath() + sysConfig.NOPOWERURL);
//                    return request.getContextPath() + sysConfig.NOPOWERURL;
//                }
//            } else {
//                response.sendRedirect(request.getContextPath() + sysConfig.LOGINURL);
//                return request.getContextPath() + sysConfig.LOGINURL;
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
