package com.wmy.community.config;

import com.alibaba.fastjson.JSONObject;
import com.wmy.community.model.vo.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: springSecurity 配置类
 * @Author: 三水
 * @Date: 2022/3/2 14:24
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests()
                .antMatchers(
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like/**",
                        "/follow",
                        "/unfollow"
                ).hasAnyAuthority(
                        "user",
                            "admin",
                            "moderator"
                ).anyRequest().permitAll()
                .and().csrf().disable();

        //权限不够的处理
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    //没有登录
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        Result result = Result.create(403, "请登录");
                        out.write(JSONObject.toJSONString(result));
                        out.flush();;
                        out.close();
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    //权限不足
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        Result result = Result.create(403, "权限不足");
                        out.write(JSONObject.toJSONString(result));
                        out.flush();;
                        out.close();
                    }
                });

        //Security底层默认拦截.logout请求，进行退出处理
        //我们用错误路径覆盖它拦截路径，善意欺骗，才能执行我们自己的退出代码
        http.logout().logoutUrl("/securityogout");
    }
}
