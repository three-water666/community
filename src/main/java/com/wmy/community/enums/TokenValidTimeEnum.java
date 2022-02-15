package com.wmy.community.enums;

/**
 * @Description:
 * @Author: 三水
 * @Date: 2022/2/15 20:12
 */
public enum TokenValidTimeEnum {
    /**
     * 一天
     */
    ONE_DAY(60 * 60 * 24),
    /**
     * 一周
     */
    ONE_WEEK(60 * 60 * 24 * 7);

    private int seconds;

    TokenValidTimeEnum(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
