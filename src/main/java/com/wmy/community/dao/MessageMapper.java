package com.wmy.community.dao;

import com.wmy.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 18:42
 */
@Mapper
public interface MessageMapper {

    //查询当前用户会话列表，每个会话只返回最新私信
    List<Message> selectConversations(int userId);

    //查询当前用户系统通知最新私信
    List<Message> selectSystemMessage(int userId);

    //查询当前用户会话数量
    int selectConversationCount(int userId);

    //查询某个会话私信列表
    List<Message> selectLetters(String conversationId);

    //查询某个会话包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信数量
    int selectLetterUnreadCount(int userId,String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 修改消息的状态
    int updateStatus(List<Integer> ids, int status);
}
