package com.wmy.community.dao;

import com.wmy.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 15:09
 */
@Mapper
public interface CommentMapper {

    List<Comment> selectCommentByEntity(int entityType,int entityId);

    int selectCountByEntity(int entityType,int entityId);
}
