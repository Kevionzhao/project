package com.zdxf.utils;

import com.zdxf.sysmanage.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Admin
 */
@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;
}