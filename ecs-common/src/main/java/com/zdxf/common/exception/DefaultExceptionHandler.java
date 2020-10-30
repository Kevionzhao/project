package com.zdxf.common.exception;

import com.zdxf.common.enums.CodeType;
import com.zdxf.common.module.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  * 异常处理类
 *  * controller层异常无法捕获处理，需要自己处理
 *  * Created at 2018/8/27.
 *
 * @author Admin*/
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {
    /**
     * 处理所有自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result handleCustomException(CustomException e){
        log.error(e.getResult().getMsg());
        return e.getResult();
    }
    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return Result.failure(CodeType.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
