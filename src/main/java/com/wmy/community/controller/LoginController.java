package com.wmy.community.controller;

import com.google.code.kaptcha.Producer;
import com.wmy.community.entity.LoginTicket;
import com.wmy.community.entity.User;
import com.wmy.community.enums.TokenValidTimeEnum;
import com.wmy.community.exception.DomainException;
import com.wmy.community.interceptor.LoginInterceptor;
import com.wmy.community.model.dto.LoginInfo;
import com.wmy.community.model.dto.RegisterInfo;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.UserService;
import com.wmy.community.util.CookieUtil;
import com.wmy.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 注册 登录功能
 * @Author: 三水
 * @Date: 2022/2/11 21:22
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/registry")
    public Result register(@RequestBody RegisterInfo regInfo){
        userService.register(regInfo.getUsername(),regInfo.getPassword(),regInfo.getEmail());
        return Result.ok("注册成功，已经向您邮箱发送激活链接，请尽快激活");
    }

    @GetMapping("/activation/{userId}/{code}")
    public Result activation(@PathVariable("userId") int userId,@PathVariable("code") String code){
        userService.activation(userId,code);
        return Result.ok("激活成功，请登录");
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入session
        session.setAttribute("kaptcha",text);

        //将验证码图片输出给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginInfo loginInfo, HttpSession session){
        //验证验证码 不区分大小写
        String kaptcha =(String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(kaptcha)||StringUtils.isBlank(loginInfo.getKaptcha())||!kaptcha.equalsIgnoreCase(loginInfo.getKaptcha())){
            throw new DomainException("验证码错误");
        }
        //
        LoginTicket token = userService.login(loginInfo.getUsername(), loginInfo.getPassword(), TokenValidTimeEnum.ONE_DAY.getSeconds());
        HashMap<String, LoginTicket> map = new HashMap<>();
        map.put("token",token);
        return Result.ok("登录成功", map);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        String ticket = CookieUtil.getValueByName(request,"ticket");
        userService.logout(ticket);
        SecurityContextHolder.clearContext();
        return Result.ok("登出成功");
    }

    @GetMapping("/userData")
    public Result userData(HttpServletRequest request){
        String ticket = CookieUtil.getValueByName(request,"ticket");
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        int userId = loginTicket.getUserId();
        User user = userService.findUserById(userId);
        Map<String,Object> map=new HashMap<>();
        map.put("info",user);
        return Result.ok("请求用户数据成功",map);
    }

}
