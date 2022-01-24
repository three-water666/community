package com.wmy.community.service;

import com.wmy.community.entity.DiscussPost;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/1/23 0:58
 */
public interface DiscussPostService {
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit);

    public int findDiscussPostRows(int userId);
}
