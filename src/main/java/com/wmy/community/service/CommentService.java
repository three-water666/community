package com.wmy.community.service;

import com.github.pagehelper.PageInfo;
import com.wmy.community.entity.Comment;

/**
 * @Description: 评论业务层
 * @Author: 三水
 * @Date: 2022/2/23 15:32
 */
public interface CommentService {

    public PageInfo<Comment> findCommentByEntity(int entityType,int entityId,int index,int size);

}
