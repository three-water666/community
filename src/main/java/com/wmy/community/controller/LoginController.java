package com.wmy.community.controller;

import com.google.code.kaptcha.Producer;
import com.wmy.community.config.KaptchaConfig;
import com.wmy.community.model.dto.RegInfo;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    @PostMapping("/registry")
    public Result register(@RequestBody RegInfo regInfo){
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

}
