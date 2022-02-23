package com.wmy.community.controller;

import com.github.pagehelper.PageInfo;
import com.wmy.community.entity.Comment;
import com.wmy.community.entity.User;
import com.wmy.community.model.vo.PageResult;
import com.wmy.community.model.vo.Result;
import com.wmy.community.service.CommentService;
import com.wmy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 15:42
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/{postId}/{index}/{size}")
    public Result getCommentList(@PathVariable("postId") int postId,
                                 @PathVariable("index") int index,
                                 @PathVariable("size") int size){
        PageInfo<Comment> pageInfo = commentService.findCommentByEntity(1, postId, index, size);
        List<Map<String,Object>> list=new ArrayList<>();
        for(Comment comment:pageInfo.getList()){
            Map<String,Object> map=new HashMap<>();
            map.put("comment",comment);
            User poster = userService.findUserById(comment.getUserId());
            map.put("poster",poster);
            list.add(map);
        }
        return Result.ok("请求评论成功",new PageResult<Map<String,Object>>((int)pageInfo.getTotal(), list));


    }
}
