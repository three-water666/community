package com.wmy.community.service.impl;

import com.sun.mail.imap.Rights;
import com.wmy.community.dao.LoginTicketMapper;
import com.wmy.community.dao.UserMapper;
import com.wmy.community.entity.LoginTicket;
import com.wmy.community.entity.User;
import com.wmy.community.enums.UserStatusEnum;
import com.wmy.community.exception.DomainException;
import com.wmy.community.service.UserService;
import com.wmy.community.util.EmailClient;
import com.wmy.community.util.RedisKeyUtil;
import com.wmy.community.util.RegistryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    //@Autowired
    //private LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User findUserById(int id) {
        //return userMapper.selectById(id);
        User user = getUserFromRedis(id);
        if(user==null){
            user=getUserFromDBAndInitRedis(id);
        }
        return user;
    }

    //1. 优先从缓存中获取用户信息
    private User getUserFromRedis(int userId){
        String userKey = RedisKeyUtil.getUserKey(userId);
        return (User)redisTemplate.opsForValue().get(userKey);
    }
    //2. 如果缓存中没有，从数据库获取用户信息，并初始化缓存
    private User getUserFromDBAndInitRedis(int userId){
        User user = userMapper.selectById(userId);
        String userKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(userKey,user,3600, TimeUnit.SECONDS);
        return user;
    }
    //3. 数据变更时清除缓存
    private void clearUserInRedis(int userId){
        String userKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(userKey);
    }


    @Override
    public void register(String username, String password, String email) {
        //空值处理
        if(username==null){
            throw new DomainException("用户名不能为空");
        }
        if(password==null){
            throw new DomainException("密码不能为空");
        }
        if(email==null){
            throw new DomainException("邮箱不能为空");
        }

        //判断账号是否占用
        User u = userMapper.selectByName(username);
        if(u!=null){
            //注册失败，该账号已存在
            throw new DomainException("注册失败，该账号已存在");
        }
        //判断邮箱是否占用
        u = userMapper.selectByEmail(email);
        if(u!=null){
            //注册失败，该邮箱已被占用
            throw new DomainException("注册失败，该邮箱已被占用");
        }

        //注册用户，保存用户信息
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setSalt(RegistryUtil.generateUUID().substring(0,5));
        user.setPassword(RegistryUtil.md5(password+user.getSalt()));
        user.setActivationCode(RegistryUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送激活邮件
        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        emailClient.sendMail(email,"账号激活",url);
    }

    @Override
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new DomainException("激活失败，用户不存在");
        } else if (user.getStatus() == UserStatusEnum.ACTIVATED.getStatus()) {
            throw new DomainException("无效操作，账号已激活");
        } else if (user.getActivationCode().equals(code)) {
            clearUserInRedis(userId);
            return userMapper.updateStatus(userId, 1);
        } else {
            throw new DomainException("激活失败，激活码错误");
        }
    }

    @Override
    public LoginTicket login(String username, String password, int expiredSeconds) {
        //空值处理
        if(username==null){
            throw new DomainException("账号为空");
        }
        if(password==null){
            throw new DomainException("密码为空");
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if(user==null){
            throw new DomainException("用户不存在");
        }
        //验证状态
        if(user.getStatus()==0){
            throw new DomainException("账号未激活");
        }
        //验证密码
        password=RegistryUtil.md5(password+user.getSalt());
        if(!user.getPassword().equals(password)){
            throw new DomainException("密码错误");
        }
        //分发登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(RegistryUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));
        //loginTicketMapper.insertLoginTicket(loginTicket);
        //登录模块优化，将登录凭证存到redis中
        String ticketKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(ticketKey,loginTicket);

        return loginTicket;
    }

    @Override
    public void logout(String ticket) {
        //loginTicketMapper.updateStatus(ticket,1);
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(ticketKey,loginTicket);
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket)redisTemplate.opsForValue().get(ticketKey);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        User user = this.findUserById(userId);

        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch(user.getType()){
                    case 1:return "admin";
                    case 2:return "moderator";
                    default:return "user";
                }
            }
        });
        return list;
    }
}
