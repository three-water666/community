package com.wmy.community.controller;

import com.wmy.community.entity.DiscussPost;
import com.wmy.community.entity.Event;
import com.wmy.community.entity.LoginTicket;
import com.wmy.community.event.EventProducer;
import com.wmy.community.exception.DomainException;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.DiscussPostService;
import com.wmy.community.service.LikeService;
import com.wmy.community.util.CookieUtil;
import com.wmy.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/24 16:29
 */
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private EventProducer eventProducer;

    @PutMapping("like/{postId}")
    public Result like(HttpServletRequest request,@PathVariable("postId")int postId){
        String ticket = CookieUtil.getValueByName(request,"ticket");

        if(ticket==null){
            throw new DomainException("点赞失败，请登录");
        }

        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        int userId = loginTicket.getUserId();

        DiscussPost discussPost = discussPostService.findDiscussPostById(postId);

        likeService.like(userId,1,postId,discussPost.getUserId());

        //点赞后使用生产者发送通知
        Event event = new Event();
        event.setTopic("like");
        event.setUserId(userId);
        event.setEntityType(1);//帖子点赞
        event.setEntityId(postId);
        event.setEntityUserId(discussPost.getUserId());

        eventProducer.fireEvent(event);

        return Result.ok("点赞成功");
    }
}
