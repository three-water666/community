package com.wmy.community.service.impl;

import com.sun.mail.imap.Rights;
import com.wmy.community.dao.UserMapper;
import com.wmy.community.entity.User;
import com.wmy.community.service.UserService;
import com.wmy.community.util.EmailClient;
import com.wmy.community.util.RegistryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/24 0:49
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailClient emailClient;

    @Override
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void register(String username, String password, String email) {
        //判断账号是否占用
        User u = userMapper.selectByName(username);
        if(u!=null){
            //注册失败，该账号已存在
        }
        //判断邮箱是否占用
        u = userMapper.selectByEmail(email);
        if(u!=null){
            //注册失败，该邮箱已被占用
        }

        //注册用户，保存用户信息
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setSalt(RegistryUtil.generateUUID().substring(0,5));
        user.setPassword(RegistryUtil.md5(password+user.getSalt()));
        user.setActivationCode(RegistryUtil.generateUUID());
        user.setHeaderUrl(String.format("http://imaegs.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送激活邮件
        String url="";
        emailClient.sendMail(email,"账号激活",url);
    }
}
