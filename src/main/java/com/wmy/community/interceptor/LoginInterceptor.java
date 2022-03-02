package com.wmy.community.interceptor;

import com.wmy.community.annotation.LoginRequired;
import com.wmy.community.entity.LoginTicket;
import com.wmy.community.entity.User;
import com.wmy.community.service.UserService;
import com.wmy.community.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description: 登录拦截器，记得配置
 * @Author: 三水
 * @Date: 2022/2/22 18:34
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否有需要登陆注解 已经弃用 使用security代替
        //if(handler instanceof HandlerMethod){
        //    HandlerMethod handlerMethod = (HandlerMethod) handler;
        //    Method method = handlerMethod.getMethod();
        //    LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        //    if(loginRequired==null){
        //        return true;
        //    }
        //}
        //
        //return false;
        String ticket = CookieUtil.getValueByName(request, "ticket");

        if(ticket!=null){
            //根据凭证获得完整凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //判断凭证是否有效
            if(loginTicket!=null&&loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){
                //得到凭证所属用户
                User user = userService.findUserById(loginTicket.getUserId());
                //构建用户认证结果，存入securityContext,便于security进行授权
                Authentication authentication= new UsernamePasswordAuthenticationToken(
                        user,user.getPassword(),userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }

        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.clearContext();
    }
}
