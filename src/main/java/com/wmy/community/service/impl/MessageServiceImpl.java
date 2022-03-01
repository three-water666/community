package com.wmy.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmy.community.dao.MessageMapper;
import com.wmy.community.entity.Message;
import com.wmy.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/23 18:57
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public PageInfo<Message> findConversations(int userId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Message> list = messageMapper.selectConversations(userId);
        PageInfo<Message> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int addMessage(Message message) {

        return messageMapper.insertMessage(message);
    }

    @Override
    public PageInfo<Message> findSystemMessage(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Message> list = messageMapper.selectSystemMessage(userId);
        PageInfo<Message> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
