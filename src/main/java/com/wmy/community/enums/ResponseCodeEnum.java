package com.wmy.community.enums;

/**
 * @Description:返回值枚举类型
 * @Author: 三水
 * @Date: 2022/2/11 20:42
 */
public enum ResponseCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS("00000"),
    /**
     * 客户端错误
     */
    CLIENT_ERROR("A0001"),
    /**
     * 服务端错误
     */
    SERVER_ERROR("B0001"),
    /**
     * 第三方错误
     */
    THIRD_REEOR("C0001");

    private String code;

    ResponseCodeEnum(String code) {
        this.code=code;
    }
    public String getCode(){
        return code;
    }
}
