package com.wmy.community.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 帖子列表
 * @Author: 三水
 * @Date: 2022/1/23 0:31
 */
@Getter
@Setter
@ToString
public class DiscussPost {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private int commentCount;
    private double score;
}

