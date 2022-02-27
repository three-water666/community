package com.wmy.community.controller;

import com.wmy.community.entity.DiscussPost;
import com.wmy.community.entity.User;
import com.wmy.community.model.vo.PageResult;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.DiscussPostService;
import com.wmy.community.service.LikeService;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/23 1:07
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/postList")
    public Result getIndexPage(
            @RequestParam(required = false,defaultValue = "10") int pageSize,
            @RequestParam(required = false,defaultValue = "1") int pageNum){
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0,(pageNum-1)*pageSize,pageSize);
        for(DiscussPost post :discussPostList){
            Map<String, Object> map = new HashMap<>();
            //post指的是帖子
            map.put("post", post);
            User user  = userService.findUserById(post.getUserId());
            map.put("user", user);
            long likeCount = likeService.findEntityLikeCount(1, post.getId());
            map.put("likeCount",likeCount);
            discussPosts.add(map);
        }
        PageResult<Map<String, Object>> pageResult = new PageResult<>();
        pageResult.setList(discussPosts);
        pageResult.setTotalPage(discussPostService.findDiscussPostRows(0));
        return Result.ok("请求成功",pageResult);
    }
}
