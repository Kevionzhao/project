package com.zdxf.sysmanage.query;

import com.zdxf.common.base.BaseQuery;
import lombok.Data;

/**
 * 操作日志查询条件
 */
@Data
public class OperLogQuery extends BaseQuery {

    /**
     * 日志标题
     */
    private String title;

}
