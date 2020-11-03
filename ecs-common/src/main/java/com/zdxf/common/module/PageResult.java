package com.zdxf.common.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Admin
 * 分页结果DO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {
    /**
     * 当前页
     */
    private Long current;
    /**
     * 当前分页总页数
     */
    private Long size;
    /**
     * 当前满足条件总行数
     */
    private Long total;
    /**
     * 当前分页总页数
     */
    private Long pages;
    /**
     * 分页记录列表
     */
    private T data;
}
