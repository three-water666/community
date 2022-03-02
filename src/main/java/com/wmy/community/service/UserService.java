package com.wmy.community.service;

import com.wmy.community.entity.LoginTicket;
import com.wmy.community.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/24 0:47
 */
public interface UserService {
    public User findUserById(int id);

    public void register(String username,String password,String email);

    public int activation(int userId,String code);

    public LoginTicket login(String username, String password, int expiredSeconds);

    public void logout(String ticket);

    public LoginTicket findLoginTicket(String ticket);

    //获得用户权限
    public Collection<? extends GrantedAuthority> getAuthorities(int userId);
}
