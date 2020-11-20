package com.zdxf.common.enums;

/**
 * 操作类型
 *
 * @author Admin
 */
public enum OperatorType {

    /**
     * 其它
     */
    OTHER,

    /**
     * 后台用户
     */
    MANAGE,

    /**
     * 手机端用户
     */
    MOBILE,
    /**
     * 新增
     */
    ADD(1, "新增"),
    /**
     * 编辑
     */
    EDIT(2, "编辑"),
    /**
     * 删除
     */
    DEL(3, "删除");
    private int code;
    private String msg;

    OperatorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    OperatorType() {

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
