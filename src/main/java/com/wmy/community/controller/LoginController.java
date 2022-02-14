package com.wmy.community.controller;

import com.wmy.community.model.dto.RegInfo;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 注册 登录功能
 * @Author: 三水
 * @Date: 2022/2/11 21:22
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

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

}
