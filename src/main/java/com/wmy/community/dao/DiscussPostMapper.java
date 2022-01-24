package com.wmy.community.dao;

import com.wmy.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 列表Mapper
 * @Author: 三水
 * @Date: 2022/1/23 0:37
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

    //获得总行数
    //@Param注解用于给参数起别名
    //如果只有一个参数，并且在<if>里使用，必须起别名
    int selectDiscussPostRows(@Param("userId") int userId);
}
