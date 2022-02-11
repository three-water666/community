package com.wmy.community.controller;

import com.wmy.community.model.vo.Result;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result register(String username,String password,String email){
        userService.register(username,password,email);
        return Result.ok("注册成功");
    }

}
