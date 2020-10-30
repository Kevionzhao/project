package com.zdxf.system.utils;

import com.zdxf.system.entity.UserDetail;
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