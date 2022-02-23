package com.wmy.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmy.community.dao.CommentMapper;
import com.wmy.community.entity.Comment;
import com.wmy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 15:32
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<Comment> findCommentByEntity(int entityType, int entityId, int index, int size) {
        PageHelper.startPage(index,size);
        List<Comment> list = commentMapper.selectCommentByEntity(entityType, entityId);
        PageInfo<Comment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
