package com.zdxf.sysmanage.query;

import com.zdxf.common.base.BaseQuery;
import lombok.Data;

/**
 * 人员查询条件
 * @author Admin
 */
@Data
public class UserQuery extends BaseQuery {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 所属单位
     */
    private String companyId;
}
