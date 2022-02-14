package com.wmy.community.enums;

/**
 * @Description:用户激活状态
 * @Author: 三水
 * @Date: 2022/2/14 17:29
 */
public enum UserStatusEnum {
    /**
     * 已激活
     */
    ACTIVATED(1),
    /**
     * 未激活
     */
    INACTIVATED(0);

    private int status;

    UserStatusEnum(int status){
        this.status=status;
    }

    public int getStatus(){
        return status;
    }
}
