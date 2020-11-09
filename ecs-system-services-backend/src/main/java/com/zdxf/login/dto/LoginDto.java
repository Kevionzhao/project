package com.zdxf.login.dto;

import com.zdxf.common.base.BaseQuery;
import lombok.Data;

/**
 * 人员查询条件
 * @author Admin
 */
@Data
public class LoginDto extends BaseQuery {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码KEY
     */
    private String key;
}
