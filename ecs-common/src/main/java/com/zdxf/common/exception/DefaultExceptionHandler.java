package com.zdxf.common.exception;

import com.zdxf.common.enums.CodeType;
import com.zdxf.common.module.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  * 异常处理类
 *  * controller层异常无法捕获处理，需要自己处理
 *
 * @author Admin
 * */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {
    /**
     * 处理所有自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResultJson handleCustomException(CustomException e){
        log.error(e.getResultJson().getMsg());
        return e.getResultJson();
    }
    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.error(CodeType.BAD_REQUEST.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
