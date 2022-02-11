package com.wmy.community.exception;

import com.wmy.community.model.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常处理
 * @Author: 三水
 * @Date: 2022/2/11 21:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public Result domainExceptionHandler(DomainException e){
        return Result.error(e.getMessage());
    }
}
