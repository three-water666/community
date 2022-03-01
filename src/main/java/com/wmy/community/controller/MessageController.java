package com.wmy.community.controller;

import com.github.pagehelper.PageInfo;
import com.wmy.community.entity.LoginTicket;
import com.wmy.community.entity.Message;
import com.wmy.community.model.vo.PageResult;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.MessageService;
import com.wmy.community.service.UserService;
import com.wmy.community.util.CookieUtil;
import com.wmy.community.util.RedisKeyUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/24 13:19
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/conversationList")
    public Result getConversationList(HttpServletRequest request,int pageNum,int pageSize,String messageType){
        String ticket = CookieUtil.getValueByName(request,"ticket");
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        int userId = loginTicket.getUserId();

        PageInfo<Message> conversations=null;
        //根据messageType决定查询私信还是通知
        if(messageType.equals("message")){
            conversations = messageService.findConversations(userId, pageNum, pageSize);
        }else{
            conversations = messageService.findSystemMessage(userId, pageNum, pageSize);
        }

        List<Map<String,Object>> conversationList=new ArrayList<>();
        for(Message message: conversations.getList()){
            Map<String,Object> map=new HashMap<>();
            map.put("lastMessage",message);
            map.put("from",userService.findUserById(message.getFromId()));
            conversationList.add(map);
        }

        return Result.ok("请求私信/系统通知成功",new PageResult<Map<String,Object>>((int)conversations.getTotal(),conversationList));
    }


}
