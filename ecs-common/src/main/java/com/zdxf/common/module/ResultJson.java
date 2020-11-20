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

    public ResultJson(Integer code, Object data) {
        this.code = code;
        this.data = data;
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
        return new ResultJson(HttpStatus.OK.value(),data);
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
}
