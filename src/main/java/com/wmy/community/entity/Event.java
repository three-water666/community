package com.wmy.community.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 事件实体类
 * @Author: 三水
 * @Date: 2022/3/1 12:54
 */
public class Event {
    //点赞 评论 关注
    private String topic;
    private int userId;
    private int entityType;
    private int entityId;
    private int entityUserId;
    //可能的其他数据
    private Map<String,Object> data=new HashMap<>();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public void setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String key,Object value) {
        this.data.put(key,value);
    }
}
