package com.zdxf.system.result;

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
    private int page;
    private int rows;
    private int total;
    private T data;
}
