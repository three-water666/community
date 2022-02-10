package com.wmy.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Description:注册功能工具类
 * @Author: 三水
 * @Date: 2022/2/10 22:05
 */
public class RegistryUtil {
    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String genera(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * MD5 加密
     *
     * @param key
     * @return
     */
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
