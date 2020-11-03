package com.zdxf.common.base;

import lombok.Data;

/**
 * 查询对象基类
 * @author Admin
 */
@Data
public class BaseQuery {
    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 每页数
     */
    private Integer pageSize;
}
