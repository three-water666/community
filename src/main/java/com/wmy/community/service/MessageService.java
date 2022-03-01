package com.wmy.community.service;

import com.github.pagehelper.PageInfo;
import com.wmy.community.entity.Message;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 18:56
 */
public interface MessageService {
    //查找会话列表
    public PageInfo<Message> findConversations(int userId,int pageNum,int pageSize);

    //发送私信
    public int addMessage(Message message);

    public PageInfo<Message> findSystemMessage(int userId,int pageNum,int pageSize);
}
