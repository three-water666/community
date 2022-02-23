package com.wmy.community.service.impl;

import com.wmy.community.dao.DiscussPostMapper;
import com.wmy.community.entity.DiscussPost;
import com.wmy.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/23 0:59
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    @Override
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public DiscussPost findDiscussPostById(int postId) {
        return discussPostMapper.selectDiscussPostById(postId);
    }
}
