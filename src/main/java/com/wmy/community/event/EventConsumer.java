package com.wmy.community.event;

import com.alibaba.fastjson.JSONObject;
import com.wmy.community.entity.Event;
import com.wmy.community.entity.Message;
import com.wmy.community.service.MessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 事件消费者 不用调用，自动执行
 * @Author: 三水
 * @Date: 2022/3/1 13:04
 */
@Component
public class EventConsumer {

    @Autowired
    private MessageService messageService;

    @KafkaListener(topics = {"comment","like","follow"})
    public void handleCommentMessage(ConsumerRecord record){
        if(record==null||record.value()==null){
            return;
        }
        //将接受的数据转化为event对象
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if(event==null){
            return;
        }

        //通知持久化
        Message message = new Message();
        message.setFromId(1);//系统通知
        message.setToId(event.getEntityUserId());//被点赞的用户id
        message.setConversationId(event.getTopic());//主题
        message.setCreateTime(new Date());

        //消息内容 某用户给你的某评论/帖子/关注 点赞/评论
        HashMap<String, Object> content = new HashMap<>();
        content.put("userId",event.getUserId());
        content.put("entityType",event.getEntityType());
        content.put("entityId",event.getEntityId());

        //还有其他数据
        if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object> entry:event.getData().entrySet()){
                content.put(entry.getKey(),entry.getValue());
            }
        }

        message.setContent(JSONObject.toJSONString(content));
        //持久化消息
        messageService.addMessage(message);
    }
}
