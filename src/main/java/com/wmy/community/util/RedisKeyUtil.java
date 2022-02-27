package com.wmy.community.util;

/**
 * @Description:redis的key生成工具类
 * @Author: 三水
 * @Date: 2022/2/22 19:18
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_FOLLOWER = "follower";
    //验证码
    private static final String PREFIX_KAPTCHA = "kaptcha";
    //登录凭证
    private static final String PREFIX_TICKET = "ticket";
    //用户信息
    private static final String PREFIX_USER = "user";
    private static final String PREFIX_UV = "uv";
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";

    //登录的凭证
    public static String getTicketKey(String ticket){
        return PREFIX_TICKET+SPLIT+ticket;
    }

    //缓存用户，userId作为key
    public static String getUserKey(int userId){
        return PREFIX_USER+SPLIT+userId;
    }

    //某个实体的赞，实体可以是帖子，评论等
    //like:entity:entityType:entityId->set(userId)
    public static String getEntityLikeKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+entityId;
    }

    //某个用户获得的赞的个数
    //like:user:userId->int
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }

}
