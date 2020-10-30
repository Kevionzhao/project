package com.zdxf.system.result;

import lombok.Data;

import java.io.Serializable;

/**
 * RESTful API 返回类型
 * @author Admin
 */
@Data
public class ResultJson<T> implements Serializable {
    private static final long serialVersionUID = 0L;
    private int code;
    private String msg;
    private T data;

    public static ResultJson success() {
        return new ResultJson(ResultCode.SUCCESS);
    }

    public static ResultJson ok(Object o) {
        return new ResultJson(ResultCode.SUCCESS, o);
    }

    public static ResultJson failure(ResultCode code) {
        return failure(code, "");
    }

    public static ResultJson failure(ResultCode code, Object o) {
        return new ResultJson(code, o);
    }

    public ResultJson(ResultCode resultCode) {
        setResultCode(resultCode);
    }
    public ResultJson() {

    }

    public ResultJson(ResultCode resultCode, T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":\"" + data + '\"'+
                '}';
    }
}
