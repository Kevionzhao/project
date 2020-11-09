package com.zdxf.common.module;

import com.zdxf.common.enums.CodeType;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 返回数据
 * @author Admin
 */
@Data
@ToString
public class ResultJson<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code;

    /**
     *  状态消息
     */
    private String msg;

    /**
     * 返回对象
     */
    private Object data;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    /**
     * 构造函数
     */
    public ResultJson() {
    }

    public ResultJson(String msg) {
        this.msg = msg;
    }

    public ResultJson(Object data) {
        this.data = data;
    }

    public ResultJson(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultJson(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultJson success() {
        return new ResultJson();
    }

    public static ResultJson success(String msg) {
        return new ResultJson(msg);
    }

    public static ResultJson success(Object data) {
        return new ResultJson(data);
    }

    public static ResultJson success(String msg, Object data) {
        return new ResultJson(HttpStatus.OK.value(), msg, data);
    }

    public static ResultJson error() {
        return new ResultJson(-1, "操作失败");
    }

    public static ResultJson error(String msg) {
        return new ResultJson(-1, msg);
    }

    public static ResultJson error(Integer code, String msg) {
        return new ResultJson(code, msg);
    }

    public static ResultJson error(Integer code, String msg, Object data) {
        return new ResultJson(code, msg, data);
    }

    public static ResultJson error(CodeType errorCode) {
        return new ResultJson(errorCode.getCode(), errorCode.getMsg());
    }

    public static ResultJson error(HttpStatus httpStatus, String msg, Object data) {
        return new ResultJson(httpStatus.value(), msg, data);
    }

    public Object error(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
        return this;
    }

//    private T data;
//
//    private Integer code;
//
//    private String msg;
//
//
//    /**
//     * 自定义返回 不带数据
//     * @param code
//     * @param msg
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> result(Integer code, String msg) {
//        return succeedWith(null, code, msg);
//    }
//
//    /**
//     * 自定义返回 ,带数据
//     * @param code
//     * @param msg
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> result(Integer code, String msg, T data) {
//        return succeedWith(data, code, msg);
//    }
//
//    /**
//     * 成功，code=0，msg=ok
//     * @return
//     */
//    public static <T> Result<T> succeed() {
//        return succeedWith(null, CodeType.SUCCESS.getCode(),CodeType.SUCCESS.getMsg());
//    }
//
//    /**
//     * 成功 code=0 msg=自定义
//     * @param msg
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> succeedOfMsg(String msg) {
//        return succeedWith(null, CodeType.SUCCESS.getCode(), msg);
//    }
//
//    /**
//     * 成功 code=0 msg=ok
//     * @param model
//     * @param msg
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> succeed(T model, String msg) {
//        return succeedWith(model, CodeType.SUCCESS.getCode(), msg);
//    }
//
//    /**
//     * 成功
//     * @param model
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> succeed(T model) {
//        return succeedWith(model, CodeType.SUCCESS.getCode(), "ok");
//    }
//
//    public static <T> Result<T> succeedWith(T data, Integer code, String msg) {
//        return new Result<>(data, code, msg);
//    }
//
//    /**
//     * 失败
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> failure(T data) {
//        return failureWith(data, CodeType.EXCEPTION.getCode(), "fail");
//    }
//
//    /**
//     *
//     * @param model
//     * @param msg
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> failure(T model, String msg) {
//        return failureWith(model, CodeType.EXCEPTION.getCode(), msg);
//    }
//
//    /**
//     *
//     * @param data
//     * @param code
//     * @param msg
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> failureWith(T data, Integer code, String msg) {
//        return new Result<>(data, code, msg);
//    }
//
//    /**
//     * 没有登录，code=0，msg=ok
//     * @return
//     */
//    public static <T> Result<T> notLogin() {
//        return succeedWith(null, CodeType.NOTLOGIN.getCode(), "未登录，请登录");
//    }
//
//    /**
//     * 指定api未携带token
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> noToken(){
//        return succeedWith(null, CodeType.NOTLOGIN.getCode(), "未知的token");
//    }
//
//    /**
//     * 没有登录，code=6，msg=参数错误
//     * @return
//     */
//    public static <T> Result<T> parameterError() {
//        return succeedWith(null, CodeType.PARA_TYPE_ERROR.getCode(), "参数错误");
//    }
//
//    /**
//     * 没有登录，code=0，msg=ok
//     * @return
//     */
//    public static <T> Result<T> permissionDenied() {
//        return succeedWith(null, CodeType.PERMISSION_DENIED.getCode(), "没有权限，请先申请权限");
//    }
//
//    /**
//     * 没有登录，code=0，msg=ok
//     * @return
//     */
//    public static <T> Result<T> customMsg(String msg) {
//        return succeedWith(null, CodeType.NOTLOGIN.getCode(), msg);
//    }
//
//    /**
//     * 服务不通
//     * @param <T>
//     * @return
//     */
//    public static <T> Result<T> serviceError(String msg) {
//        return succeedWith(null, CodeType.SERVER_ERROR.getCode(), msg);
//    }
//
//    /**
//     * 是否成功结果
//     * @param result
//     * @return
//     */
//    public static Boolean isSuccess(Result result){
//
//        return null != result && null != result.getCode() && 0 == result.getCode();
//    }
}
