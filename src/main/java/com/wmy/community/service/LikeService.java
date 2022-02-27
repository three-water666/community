package com.wmy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/24 14:52
 */
public interface LikeService {

    //点赞
    public void like(int userId,int entityType,int entityId,int entityUserId);

    //查询某实体的获得的点赞数
    public long findEntityLikeCount(int entityType,int entityId);
}
