package com.wmy.community.exception;

/**
 * @Description: 服务端业务异常
 * @Author: 三水
 * @Date: 2022/2/11 21:43
 */
public class DomainException extends RuntimeException{
    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
