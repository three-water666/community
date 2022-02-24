package com.wmy.community.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Description:私信实体类
 * @Author: 三水
 * @Date: 2022/2/23 18:40
 */
@Getter
@Setter
@ToString
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;
}
