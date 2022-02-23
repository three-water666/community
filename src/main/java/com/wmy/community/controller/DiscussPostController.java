package com.wmy.community.controller;

import com.wmy.community.entity.DiscussPost;
import com.wmy.community.entity.User;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.DiscussPostService;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 帖子详情
 * @Author: 三水
 * @Date: 2022/2/23 14:12
 */
@RestController
@RequestMapping("/discussPost")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @GetMapping("/{postId}")
    public Result getDiscussPost(@PathVariable("postId") int postId){
        Map<String,Object> map=new HashMap<>();
        DiscussPost discussPost = discussPostService.findDiscussPostById(postId);
        map.put("discussPost",discussPost);
        User user = userService.findUserById(discussPost.getUserId());
        map.put("user",user);
        return Result.ok("请求帖子详情成功",map);
    }


}
